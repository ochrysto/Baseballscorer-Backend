package com.example.baseballscoresheet.testcontainers;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgresContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"))
            .withDatabaseName("test_db")
            .withUsername("test-db-user")
            .withPassword("test-db-password");

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        postgreSQLContainer.start();

        TestPropertyValues.of(
                "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                "spring.datasource.password=" + postgreSQLContainer.getPassword()).applyTo(applicationContext.getEnvironment());
    }
}
