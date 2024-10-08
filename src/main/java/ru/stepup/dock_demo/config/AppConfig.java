package ru.stepup.dock_demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.stepup.dock_demo.config.properties.DatabaseProperty;


import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableWebMvc
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

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    // обращение по адресу: http://localhost:8080/swagger-ui/index.html#
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .servers(
                        List.of(new Server().url("http://localhost:8080")
                        )
                )
                .info(
                  new Info().title("Our bank API")
                );
    }
}