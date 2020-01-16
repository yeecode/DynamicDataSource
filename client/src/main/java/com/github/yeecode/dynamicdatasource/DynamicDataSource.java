package com.github.yeecode.dynamicdatasource;

import com.github.yeecode.dynamicdatasource.datasource.DynamicDataSourceConfig;
import com.github.yeecode.dynamicdatasource.model.DataSourceInfo;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private static final ThreadLocal<String> CURRENT_DATASOURCE_NAME = new ThreadLocal<String>();
    private ConcurrentHashMap<Object, Object> dataSourcesMap = new ConcurrentHashMap<Object, Object>();
    private DataSource defaultDataSource;

    public DynamicDataSource(DataSource defaultDataSource) {
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(dataSourcesMap);
        this.defaultDataSource = defaultDataSource;
    }

    @Override
    public Object determineCurrentLookupKey() {
        return CURRENT_DATASOURCE_NAME.get();
    }

    /**
     * Add a new datasource
     *
     * @param dataSourceInfo Datasource info to create new datasource
     * @param overwrite      Whether to allow overwriting if a datasource with the same name already exists
     * @return Whether the new datasource has been added
     */
    public synchronized boolean addDataSource(DataSourceInfo dataSourceInfo, Boolean overwrite) {
        if (dataSourcesMap.containsKey(dataSourceInfo.getName()) && !overwrite) {
            return false;
        } else {
            DataSource dataSource = DynamicDataSourceConfig.createDataSource(dataSourceInfo);
            dataSourcesMap.put(dataSourceInfo.getName(), dataSource);
            super.afterPropertiesSet();
            return true;
        }
    }

    /**
     * Add a new datasource and switch to it
     *
     * @param dataSourceInfo Datasource info to create new database
     * @param overwrite      If a datasource with the same name already exists, whether to allow overwriting before switching.
     * @return Whether the new datasource is added and enabled
     */
    public synchronized boolean addAndSwitchDataSource(DataSourceInfo dataSourceInfo, Boolean overwrite) {
        if (dataSourcesMap.containsKey(dataSourceInfo.getName()) && !overwrite) {
            CURRENT_DATASOURCE_NAME.set(dataSourceInfo.getName());
            return false;
        } else {
            DataSource dataSource = DynamicDataSourceConfig.createDataSource(dataSourceInfo);
            dataSourcesMap.put(dataSourceInfo.getName(), dataSource);
            super.afterPropertiesSet();
            CURRENT_DATASOURCE_NAME.set(dataSourceInfo.getName());
            return true;
        }
    }

    /**
     * Switch to a datasource
     *
     * @param dataSourceName The name of the data source to be switched to
     * @return Whether to switch to the specified datasource
     */
    public synchronized boolean switchDataSource(String dataSourceName) {
        if (!dataSourcesMap.containsKey(dataSourceName)) {
            return false;
        }
        CURRENT_DATASOURCE_NAME.set(dataSourceName);
        return true;
    }

    /**
     * Del a datasource by name. If the specified data source is in use, deletion is not allowed.
     *
     * @param dataSourceName The name of datasource to be deleted
     * @return Whether the data source was successfully deleted
     */
    public synchronized boolean delDataSource(String dataSourceName) {
        if (CURRENT_DATASOURCE_NAME.get().equals(dataSourceName)) {
            return false;
        } else {
            dataSourcesMap.remove(dataSourceName);
            return true;
        }
    }

    /**
     * Get default datasource
     *
     * @return default datasource
     */
    public DataSource getDefaultDataSource() {
        return defaultDataSource;
    }

}
