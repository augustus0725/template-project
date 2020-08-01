package com.sabo.providers;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author canbin.zhang
 * date: 2020/1/30
 */
public class RdbProvider {
    public static DruidDataSource createDataSource(String template, Properties propertiesOverride) throws IOException {
        Properties properties = new Properties();

        InputStream configStream = null;
        try {
            configStream = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(template + ".properties");
            properties.load(configStream);
        } finally {
            JdbcUtils.close(configStream);
        }
        if (null != propertiesOverride) {
            properties.putAll(propertiesOverride);
        }
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.configFromPropety(properties);
        return dataSource;
    }
}
