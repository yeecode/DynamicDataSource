package com.github.yeecode.dynamicdatasource.datasource;

import com.github.yeecode.dynamicdatasource.DynamicDataSource;
import com.github.yeecode.dynamicdatasource.model.DataSourceInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DynamicDataSourceConfig {

    @Value("${dynamicDataSource.default.url}")
    private String defaultUrl;
    @Value("${dynamicDataSource.default.driverClassName}")
    private String driverClassName;
    @Value("${dynamicDataSource.default.username}")
    private String defaultUsername;
    @Value("${dynamicDataSource.default.password}")
    private String defaultPassword;

    @Bean("defaultDataSource")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().url(defaultUrl)
                .driverClassName(driverClassName)
                .username(defaultUsername)
                .password(defaultPassword).build();
    }

    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource(DataSource defaultDataSource) {
        return new DynamicDataSource(defaultDataSource);
    }

    public static DataSource createDataSource(DataSourceInfo datasourceInfo) {
        return DataSourceBuilder.create().url(datasourceInfo.getUrl())
                .driverClassName(datasourceInfo.getDriverClassName())
                .username(datasourceInfo.getUserName())
                .password(datasourceInfo.getPassword()).build();
    }

}
