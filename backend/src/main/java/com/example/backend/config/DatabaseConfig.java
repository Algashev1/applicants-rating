package com.example.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${DB_NAME:applicants_db}")
    private String dbName;

    @Value("${DB_HOST:localhost}")
    private String dbHost;

    @Bean
    @Primary
    public DataSource dataSource() {
        createDatabaseIfNotExists();
        
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://" + dbHost + ":5432/" + dbName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        
        return dataSource;
    }

    private void createDatabaseIfNotExists() {
        String url = "jdbc:postgresql://" + dbHost + ":5432/postgres";
        
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            try (Statement statement = connection.createStatement()) {
                // Проверяем существование базы данных
                String checkDb = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'";
                boolean dbExists = statement.executeQuery(checkDb).next();

                if (!dbExists) {
                    // Создаем базу данных если она не существует
                    statement.execute("CREATE DATABASE " + dbName);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}