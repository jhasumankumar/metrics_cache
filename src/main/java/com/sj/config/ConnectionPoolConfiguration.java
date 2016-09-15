package com.sj.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "dataSource")
public class ConnectionPoolConfiguration {

    @Value("${dataSource.c3p0.max_size}")
    private int maxSize;

    @Value("${dataSource.c3p0.min_size}")
    private int minSize;

    @Value("${dataSource.c3p0.idle_test_period}")
    private int idleTestPeriod;

    @Value("${dataSource.c3p0.max_statements}")
    private int maxStatements;

    @Value("${dataSource.c3p0.url}")
    private String url;

    @Value("${dataSource.c3p0.username}")
    private String username;

    @Value("${dataSource.c3p0.password}")
    private String password;

    @Value("${dataSource.c3p0.driverClassName}")
    private String driverClassName;

    @Value("${dataSource.c3p0.preferredTestQuery}")
    private String preferredTestQuery;

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getIdleTestPeriod() {
        return idleTestPeriod;
    }

    public void setIdleTestPeriod(int idleTestPeriod) {
        this.idleTestPeriod = idleTestPeriod;
    }

    public int getMaxStatements() {
        return maxStatements;
    }

    public void setMaxStatements(int maxStatements) {
        this.maxStatements = maxStatements;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getPreferredTestQuery() {
        return preferredTestQuery;
    }

    public void setPreferredTestQuery(String preferredTestQuery) {
        this.preferredTestQuery = preferredTestQuery;
    }

}