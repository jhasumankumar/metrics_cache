package com.sj.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.beans.PropertyVetoException;

@Component
public class DatabaseClientConfig {

    @Autowired
    private ConnectionPoolConfiguration connectionPoolConfiguration;

    @Bean
    public ComboPooledDataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setMaxPoolSize(connectionPoolConfiguration.getMaxSize());
        dataSource.setMinPoolSize(connectionPoolConfiguration.getMinSize());
        dataSource.setIdleConnectionTestPeriod(connectionPoolConfiguration.getIdleTestPeriod());
        dataSource.setMaxStatements(connectionPoolConfiguration.getMaxStatements());
        dataSource.setPreferredTestQuery(connectionPoolConfiguration.getPreferredTestQuery());
        dataSource.setJdbcUrl(connectionPoolConfiguration.getUrl());
        dataSource.setPassword(connectionPoolConfiguration.getPassword());
        dataSource.setUser(connectionPoolConfiguration.getUsername());
        dataSource.setDriverClass(connectionPoolConfiguration.getDriverClassName());
        return dataSource;
    }
}
