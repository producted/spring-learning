package com.zhangpk.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 同理配置第二个数据源
 *
 * @Auther: Zhang Peike
 * @Date: 2019/6/25 13:38
 */
@Configuration
@MapperScan(basePackages = {"com.zhangpk.mapper.dbtwo"}, sqlSessionFactoryRef = "sqlSessionFactoryTwo")
public class MybatisDbTwoConfig {

    @Autowired
    @Qualifier("dbTwo")
    private DataSource dsTwo;

    @Bean
    public SqlSessionFactory sqlSessionFactoryTwo() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dsTwo);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 一个SqlSessionTemplate 对应一个sqlSessionFactory
     *
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionTemplate sessionTemplateTwo() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryTwo()); // 使用上面配置的Factory
    }
}
