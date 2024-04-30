package com.example.demo.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

/**
 * @author chris
 * @className DataSourceConfig
 * @date 2023/12/13 09:31
 * @description:
 */
@Configuration
public class DataSourceConfig {

    private static final String DBPRE="spring.datasource.hikari";
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    private Environment environment;

    @Bean(name = "demoConfig")
    @Primary
    public HikariConfig hikariConfig(){return getConfigByPre();}

    @RefreshScope
    @Bean(name = "demods")
    @Primary
    public HikariDataSource dataSource(@Qualifier("demoConfig") HikariConfig config){
        // 底层会自动拿到spring.datasource中的配置， 创建一个DruidDataSource
        return new HikariDataSource(config);
    }

    public HikariConfig getConfigByPre(){
        HikariConfig config = new HikariConfig();
        try{
            String jdbcUrl= environment.getProperty(DBPRE+".jdbc-url",String.class);
            String userName= environment.getProperty(DBPRE+".username",String.class);
            String passWord= environment.getProperty(DBPRE+".password",String.class);
            String driverClassName= environment.getProperty(DBPRE+".driver-class-name",String.class);
            String poolName= environment.getProperty(DBPRE+".pool-name",String.class);
            Integer minimumIdle= environment.getProperty(DBPRE+".minimum-idle",Integer.class);
            Integer maximumPoolSize= environment.getProperty(DBPRE+".maximum-pool-size",Integer.class);
            Boolean autoCommit= environment.getProperty(DBPRE+".auto-commit",Boolean.class);
            Integer idleTimeout= environment.getProperty(DBPRE+".idle-timeout",Integer.class);
            Integer maxLifetime= environment.getProperty(DBPRE+".max-lifetime",Integer.class);
            Integer connectionTimeout= environment.getProperty(DBPRE+".connection-timeout",Integer.class);
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(userName);
            config.setPassword(passWord);
            config.setDriverClassName(driverClassName);
            config.setPoolName(poolName);
            if(null!=minimumIdle){
                config.setMinimumIdle(minimumIdle);
            }
            if(null!=maximumPoolSize){
                config.setMaximumPoolSize(maximumPoolSize);
            }
            if(null!=autoCommit){
                config.setAutoCommit(autoCommit);
            }
            if(null!=idleTimeout){
                config.setIdleTimeout(idleTimeout);
            }
            if(null!=maxLifetime){
                config.setMaxLifetime(maxLifetime);
            }
            if(null!=connectionTimeout){
                config.setConnectionTimeout(connectionTimeout);
            }
        }catch (Exception e){
            LOGGER.error("创建HikariConfig异常",e);
        }
        LOGGER.info("config:{}",config);
        return config;
    }

}
