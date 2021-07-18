/**
 * bianque.com
 * Copyright (C) 2013-2021 All Rights Reserved.
 */
package com.xuleyan.finals.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 *
 * @author xuleyan
 * @version DataSourceConfig.java, v 0.1 2021-07-16 11:18 下午
 */
@Configuration
@MapperScan(basePackages = "com.xuleyan.finals.dal.mapper", sqlSessionTemplateRef = "xlySqlSessionTemplate")
@PropertySource(value = "classpath:druid-${spring.profiles.active}.properties")
public class DataSourceConfig {

    @Bean(name = "xlyDataSource")
    @ConfigurationProperties(prefix = "xuleyan")
    public DataSource xlyDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(name = "xlySqlSessionFactory")
    public SqlSessionFactory xlySqlSessionFactory(@Qualifier("xlyDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:sqlmapper/**/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "xlyTransactionManager")
    public DataSourceTransactionManager xlyTransactionManager(@Qualifier("xlyDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "xlySqlSessionTemplate")
    public SqlSessionTemplate xlySqlSessionTemplate(@Qualifier("xlySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}