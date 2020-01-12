package com.github.yeecode.dynamicdatasource;

import com.github.yeecode.dynamicdatasource.datasource.DynamicDataSourceConfig;
import com.github.yeecode.dynamicdatasource.model.DataSourceInfo;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicDataSource extends AbstractRoutingDataSource {
    // current datasource of this thread
    private static final ThreadLocal<String> CURRENT_DATASOURCE_NAME = new ThreadLocal<String>();
    private ConcurrentHashMap<Object, Object> dataSourcesMap = new ConcurrentHashMap<Object, Object>();
    private DataSource defaultDataSource;

    public DynamicDataSource(DataSource defaultDataSource) {
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(dataSourcesMap);
    }

    @Override
    public Object determineCurrentLookupKey() {
        return CURRENT_DATASOURCE_NAME.get();
    }

    /**
     * add new datasource
     * @param dataSourceInfo datasource info to create new database
     * @return operation succeeded or failed
     */
    public synchronized boolean addDataSource(DataSourceInfo dataSourceInfo) {
        //The new data source does not overwrite the old data source with the same name
        if (dataSourcesMap.containsKey(dataSourceInfo.getName())) {
            return false;
        } else {
            DataSource dataSource = DynamicDataSourceConfig.createDataSource(dataSourceInfo);
            dataSourcesMap.put(dataSourceInfo.getName(), dataSource);
            super.afterPropertiesSet();
            return true;
        }
    }

    /**
     * del one datasource by name
     * @param dataSourceName the name of datasource to be deleted
     */
    public synchronized void delDataSource(String dataSourceName) {
        if (dataSourcesMap.containsKey(dataSourceName)) {
            dataSourcesMap.remove(dataSourceName);
        }
    }

    /**
     * switch to a datasource
     * @param dataSourceName  the name of the data source to be switched to
     * @return operation succeeded or failed
     */
    public boolean switchDataSource(String dataSourceName) {
        if (!dataSourcesMap.containsKey(dataSourceName)) {
            return false;
        }
        CURRENT_DATASOURCE_NAME.set(dataSourceName);
        return true;
    }

    /**
     * set default dataSource
     * @param dataSourceInfo default datasource info
     */
    public void setDefaultDataSource(DataSourceInfo dataSourceInfo) {
        addDataSource(dataSourceInfo);
        defaultDataSource = (DataSource) dataSourcesMap.get(dataSourceInfo.getName());
        super.setDefaultTargetDataSource(defaultDataSource);
    }

    /**
     * get default datasource
     * @return default datasource
     */
    public DataSource getDefaultDataSource() {
        return defaultDataSource;
    }

}
