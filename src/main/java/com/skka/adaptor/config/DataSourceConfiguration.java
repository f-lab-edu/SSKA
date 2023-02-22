package com.skka.adaptor.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;

@Configuration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement
public class DataSourceConfiguration {

    @Bean(name = "writeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource writeDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "readOnlyDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource readOnlyDataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "routingDataSource")
    public DataSource routingDataSource(
        @Qualifier("writeDataSource") DataSource writeDataSource,
        @Qualifier("readOnlyDataSource") DataSource readOnlyDataSource) {
        return new WriteOrReadOnlyRoutingDataSource(writeDataSource, readOnlyDataSource);
    }

    @Primary
    @Bean(name = "dataSource")
    public LazyConnectionDataSourceProxy dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
