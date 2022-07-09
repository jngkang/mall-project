package com.mall.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    @Bean
    //自己注册SqlSessionFactory，目的是增强功能，添加分页插件
    //DataSource 代表数据源
    public SqlSessionFactory sqlSessionFactory(DataSource ds) throws Exception {

        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        //设置数据源
        factory.setDataSource(ds);

        //把分页插件设置到SqlSessionFactory插件库
        factory.setPlugins(new PageInterceptor());

        //返回具体实例对象
        SqlSessionFactory bean = factory.getObject();
        return bean;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}