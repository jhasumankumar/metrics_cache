package com.sj.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application")
public class ApplicationConfiguration {

    @Value("${application.tsdb.metrics.url}")
    private String tsdbUrl;
    @Value("${application.metrics}")
    private List<String> metrics;
    @Value("${application.cache.config}")
    private String coherenceConfigFile;

    public String getTsdbUrl() {
        return tsdbUrl;
    }

    public void setTsdbUrl(String tsdbUrl) {
        this.tsdbUrl = tsdbUrl;
    }

    public List<String> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<String> metrics) {
        this.metrics = metrics;
    }

    public String getCoherenceConfigFile() {
        return coherenceConfigFile;
    }

    public void setCoherenceConfigFile(String coherenceConfigFile) {
        this.coherenceConfigFile = coherenceConfigFile;
    }
}
