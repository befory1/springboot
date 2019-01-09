package com.zhanghan.zhboot.config;

import com.zaxxer.hikari.HikariConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = UserDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "userSqlSessionFactory")
public class UserDataSourceConfig {

    static final String PACKAGE = "com.zhanghan.zhboot.mybatis.mapper.user";

    @Autowired
    private UserDataSourceProperties userDataSourceProperties;


    @Bean(name = "userDataSource")
    public DataSource appDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(userDataSourceProperties.getUrl());
        config.setUsername(userDataSourceProperties.getUsername());
        config.setPassword(userDataSourceProperties.getPassword());
        config.setConnectionTestQuery("select 1");
        config.setConnectionTimeout(userDataSourceProperties.getConnectionTimeout());
        config.setIdleTimeout(userDataSourceProperties.getIdleTimeout());
        config.setMaxLifetime(userDataSourceProperties.getMaxLifetime());
        config.setMaximumPoolSize(userDataSourceProperties.getMaximumPoolSize());
        config.setMinimumIdle(userDataSourceProperties.getMinimumIdle());
        return new com.zaxxer.hikari.HikariDataSource(config);
    }

    @Bean(name = "userTransactionManager")
    public DataSourceTransactionManager userTransactionManager() {
        return new DataSourceTransactionManager(appDataSource());
    }

    @Bean(name = "userSqlSessionFactory")
    public SqlSessionFactory userSqlSessionFactory(
            @Qualifier("userDataSource") DataSource userDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(userDataSource);
        return sessionFactory.getObject();
    }
}
