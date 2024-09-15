package ru.stepup.dock_demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.stepup.dock_demo.config.properties.DatabaseProperty;


import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class AppConfig {

    public static final String PROPERTY_PREFIX = "spring.datasource";
    public static final String DATABASE_PROPERTY = "oneDatabaseProperty";

    @Bean(DATABASE_PROPERTY)
    @ConfigurationProperties(prefix = PROPERTY_PREFIX)
    public DatabaseProperty appDatabaseProperty() {
        return new DatabaseProperty();
    }

@Bean
@DependsOn({DATABASE_PROPERTY})
public DataSource dataSource() {
    DatabaseProperty databaseProperty = appDatabaseProperty();
    System.out.println("1) ##################################### databaseProperty.classDriver() = " + databaseProperty.getDriverClassName());
    System.out.println("2) ##################################### databaseProperty.getUrl() = " + databaseProperty.getUrl());
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(databaseProperty.getDriverClassName());
    dataSource.setUrl(databaseProperty.getUrl());
    dataSource.setUsername(databaseProperty.getUsername());
    dataSource.setPassword(databaseProperty.getPassword());

    System.out.println("2) #####################################");
    return dataSource;
}
/*
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        System.out.println("1) #####################################");
        dataSource.setUrl("jdbc:postgresql://dbl:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");
        System.out.println("2) #####################################");
        return dataSource;
    }
*/

    @Bean
    public JdbcTemplate jdbcTemplate() {
        System.out.println("3) #####################################");
        return new JdbcTemplate(dataSource());
    }
}