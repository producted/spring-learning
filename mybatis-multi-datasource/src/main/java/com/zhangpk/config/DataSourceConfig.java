package com.zhangpk.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 *  手动创建数据源，有几个注入几个就ok
 *
 *  这里很方便的是，DataSourceBuilder会自动根据属性进行自动注入 无需写一大堆注入的代码
 *
 * @Author: zhangpk
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "dbOne")
    @ConfigurationProperties(prefix = "spring.datasource.db-one")
    public DataSource dsOne(){
        // 此方法是不适用druid连接池的数据源构建
        /*return DataSourceBuilder.create().build();*/
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "dbTwo")
    @ConfigurationProperties(prefix = "spring.datasource.db-two")
    public DataSource dsTwo(){
        return DruidDataSourceBuilder.create().build();
    }
}
