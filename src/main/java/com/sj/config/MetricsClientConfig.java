package com.sj.config;

import com.codahale.metrics.JmxAttributeGauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricSet;
import com.codahale.metrics.jvm.FileDescriptorRatioGauge;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.ExportMetricReader;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.boot.actuate.metrics.opentsdb.DefaultOpenTsdbNamingStrategy;
import org.springframework.boot.actuate.metrics.opentsdb.OpenTsdbGaugeWriter;
import org.springframework.boot.actuate.metrics.opentsdb.OpenTsdbNamingStrategy;
import org.springframework.boot.actuate.metrics.reader.MetricReader;
import org.springframework.boot.actuate.metrics.reader.MetricRegistryMetricReader;
import org.springframework.boot.actuate.metrics.writer.GaugeWriter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Component;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.codahale.metrics.MetricRegistry.name;

@Component
public class MetricsClientConfig {

    private static final Logger LOG = LoggerFactory.getLogger(MetricsClientConfig.class);

    @Autowired
    private ApplicationConfiguration applicationConfiguration;
    @Autowired
    private MetricRegistry metricRegistry;
    @Autowired
    private GaugeService gaugeService;

    private MBeanServerConnection mbsc;
    private JMXConnector connection;

    @Bean
    @ExportMetricWriter
    @ConfigurationProperties("metrics.export")
    public GaugeWriter openTsdbMetricWriter() {
        OpenTsdbGaugeWriter writer = new OpenTsdbGaugeWriter();
        writer.setUrl(applicationConfiguration.getTsdbUrl());
        writer.setNamingStrategy(namingStrategy());
        return writer;
    }

    @Bean
    @ConfigurationProperties("metrics.names")
    public OpenTsdbNamingStrategy namingStrategy() {
        return new DefaultOpenTsdbNamingStrategy();
    }

    @Bean
    @ExportMetricReader
    @ConfigurationProperties("metrics.export")
    public MetricReader metricReader() {
        MetricSet jvmMetrics = new MetricSet() {
            @Override
            public Map<String, Metric> getMetrics() {

                Map<String, Metric> metrics = new HashMap<>();
                metrics.put("gc", new GarbageCollectorMetricSet());
                metrics.put("file-descriptors", new FileDescriptorRatioGauge());
                metrics.put("memory-usage", new MemoryUsageGaugeSet());
                metrics.put("threads", new ThreadStatesGaugeSet());
                metrics = registerMbeans(metrics);
                return metrics;
            }
        };

        metricRegistry.registerAll(jvmMetrics);
        return new MetricRegistryMetricReader(metricRegistry);
    }

    public JMXServiceURL getJmxUrl() throws MalformedURLException {
        return new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:7799/jmxrmi");
    }

    private void connect() {
        try {
            JMXServiceURL jmxUrl = getJmxUrl();
            connection = JMXConnectorFactory.connect(jmxUrl);
            mbsc = connection.getMBeanServerConnection();

        } catch (Exception e) {
        }
    }

    private Map<String, Metric> registerMbeans(Map<String, Metric> metrics) {
        connect();
        try {
            Set<ObjectInstance> objectInstances = mbsc.queryMBeans(null, null);
            for (ObjectInstance objectInstance : objectInstances) {
             //   LOG.info("JMX Objects-"+objectInstance.getObjectName().getCanonicalName());
                for (String objRef : applicationConfiguration.getMetrics()) {
                    if (objectInstance.getObjectName().getCanonicalName().contains(objRef)) {
                        MBeanInfo beanInfo = mbsc.getMBeanInfo(objectInstance.getObjectName());
                        for (MBeanAttributeInfo attr : beanInfo.getAttributes()) {
                            JmxAttributeGauge jmxAttributeGauge = new JmxAttributeGauge(objectInstance.getObjectName(), attr.getName());
                            metrics.put(name(objRef, attr.getName()),
                                    jmxAttributeGauge);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Wrong metrics");
        }
        return metrics;
    }
}