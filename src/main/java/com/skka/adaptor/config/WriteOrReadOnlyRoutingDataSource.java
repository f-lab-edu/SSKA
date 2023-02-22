package com.skka.adaptor.config;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class WriteOrReadOnlyRoutingDataSource extends AbstractRoutingDataSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(WriteOrReadOnlyRoutingDataSource.class);

    private final Map<Object, Object> targetDataSources;

    public WriteOrReadOnlyRoutingDataSource(DataSource writeDataSource, DataSource readOnlyDataSource) {
        Objects.requireNonNull(writeDataSource, "writeDataSource cannot be null");
        Objects.requireNonNull(readOnlyDataSource, "readOnlyDataSource cannot be null");

        this.targetDataSources = new HashMap<>();
        this.targetDataSources.put(RoutingType.WRITE, writeDataSource);
        this.targetDataSources.put(RoutingType.READ_ONLY, readOnlyDataSource);
        super.setTargetDataSources(this.targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        RoutingType routingType = TransactionSynchronizationManager.isCurrentTransactionReadOnly() ?
            RoutingType.READ_ONLY : RoutingType.WRITE;
        LOGGER.info("DataSource is routed to {}", routingType);
        return routingType;
    }
}
