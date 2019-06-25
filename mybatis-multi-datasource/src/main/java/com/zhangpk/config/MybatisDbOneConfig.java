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
 * 为了方便理解，这里直接为mapper下进行分类
 *
 * sqlSessionFactoryRef 属性是为了区分工厂，配置会有体现
 *
 * @Auther: Zhang Peike
 * @Date: 2019/6/25 11:46
 */
@Configuration
@MapperScan(basePackages = {"com.zhangpk.mapper.dbone"}, sqlSessionFactoryRef = "sqlSessionFactoryOne")
public class MybatisDbOneConfig {

    @Autowired
    @Qualifier("dbOne")
    private DataSource dsOne;

    @Bean
    public SqlSessionFactory sqlSessionFactoryOne() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dsOne);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 一个SqlSessionTemplate 对应一个sqlSessionFactory
     *
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionTemplate sessionTemplateOne() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactoryOne()); // 使用上面配置的Factory
    }

}
