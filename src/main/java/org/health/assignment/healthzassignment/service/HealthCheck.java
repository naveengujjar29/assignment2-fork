package org.health.assignment.healthzassignment.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HealthCheck implements IHealthCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheck.class);

    @Value("${spring.datasource.url}")
    private String mysqlUrl;

    @Value("${spring.datasource.username}")
    private String mysqlUserName;

    @Value("${spring.datasource.password}")
    private String mysqlPassword;

    @Override
    public boolean checkDBConnectionStatus() {
        boolean isConnectionSuccessful = false;
        try (Connection connection = DriverManager.getConnection(mysqlUrl, mysqlUserName, mysqlPassword)) {
            if (connection.isValid(2)) {
                LOGGER.info("Connection is successful.");
                isConnectionSuccessful = true;
            }
        } catch (SQLException e) {
            LOGGER.info("Failed to establish the connection.");
            isConnectionSuccessful = false;
        }
        return isConnectionSuccessful;
    }
}