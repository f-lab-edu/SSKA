package com.skka.adaptor.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories({"com.skka.domain"})
@EnableTransactionManagement
public class DatabaseConfiguration {

    @Value("${spring.datasource.driver-class-name}")
    private String DRIVER_CLASS_NAME;

    @Value("${spring.datasource.url.read-write}")
    private String READ_WRITE_URL;

    @Value("${spring.datasource.url.read-only}")
    private String READ_ONLY_URL;

    @Value("${spring.datasource.username}")
    private String USERNAME;

    @Value("${spring.datasource.password}")
    private String PASSWORD;

    @Value("${spring.datasource.hikari.maximum-pool-size}")
    private int MAXIMUM_POOL_SIZE;

    @Value("${spring.datasource.hikari.auto-commit}")
    private boolean AUTO_COMMIT;

    @Value("${spring.datasource.hikari.connection-init-sql}")
    private String CONNECTION_INIT_SQL;

    @Bean(name = "readWriteDataSource")
    public DataSource readWriteDataSource() {

        return this.buildDataSource(
            DRIVER_CLASS_NAME,
            READ_WRITE_URL,
            USERNAME,
            PASSWORD,
            "read-write",
            MAXIMUM_POOL_SIZE,
            AUTO_COMMIT,
            CONNECTION_INIT_SQL
        );
    }

    @Bean(name = "readOnlyDataSource")
    public DataSource readDataSource() {

        return this.buildDataSource(
            DRIVER_CLASS_NAME,
            READ_ONLY_URL,
            USERNAME,
            PASSWORD,
            "read-only",
            MAXIMUM_POOL_SIZE,
            AUTO_COMMIT,
            CONNECTION_INIT_SQL
        );
    }

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource(
        @Qualifier("readWriteDataSource") DataSource readWriteDataSource,
        @Qualifier("readOnlyDataSource") DataSource readOnlyDataSource
    ) {
        return new LazyReplicationConnectionDataSourceProxy(readWriteDataSource, readOnlyDataSource);
    }

    private DataSource buildDataSource(
        String driverClassName,
        String jdbcUrl,
        String username,
        String password,
        String poolName,
        int maximumPoolSize,
        boolean autoCommit,
        String connectionInitSql
    ) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setPoolName(poolName);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setAutoCommit(autoCommit);
        config.setConnectionInitSql(connectionInitSql);

        return new HikariDataSource(config);
    }
}
