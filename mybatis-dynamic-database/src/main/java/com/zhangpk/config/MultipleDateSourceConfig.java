package com.zhangpk.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: Zhang Peike
 * @Date: 2019/7/23 10:37
 */
@Configuration
public class MultipleDateSourceConfig {

    /**
     *
     * @return
     */
    @Bean("master")
    @ConfigurationProperties(prefix = "spring.datasource.db-one")
    public DataSource creeateDefaultDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("nacos")
    @ConfigurationProperties(prefix = "spring.datasource.db-two")
    public DataSource creeateOtherDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 设置动态数据源，通过@Primary 来确定主DataSource
     * @return
     */
    @Bean
    @Primary
    public DataSource createDynamicdataSource(@Qualifier("master") DataSource one, @Qualifier("nacos") DataSource two){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(one);
        //配置多数据源
        Map<Object, Object> map = new HashMap<>();
        map.put("master",one);
        map.put("nacos",two);
        dynamicDataSource.setTargetDataSources(map);
        return  dynamicDataSource;
    }
}
