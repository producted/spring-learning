package com.zhangpk.config;

import com.zhangpk.util.DataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * AbstractRoutingDataSource类源码分析，通过resolvedDataSources中以map形式保存着多个数据源。
 * 通过重写的determineCurrentLookupKey()来动态的获得当前线程需要的数据源名字。
 *
 * @Auther: Zhang Peike
 * @Date: 2019/7/23 10:35
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
}
