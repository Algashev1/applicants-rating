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

    @Bean
    @Primary
    public DataSource dataSource() {
        createDatabaseIfNotExists();
        
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/" + dbName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        
        return dataSource;
    }

    private void createDatabaseIfNotExists() {
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                username,
                password)) {
            
            try (Statement statement = connection.createStatement()) {
                // Check if database exists
                String sql = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'";
                boolean dbExists = statement.executeQuery(sql).next();

                if (!dbExists) {
                    // Create database if it doesn't exist
                    statement.execute("CREATE DATABASE " + dbName);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
}