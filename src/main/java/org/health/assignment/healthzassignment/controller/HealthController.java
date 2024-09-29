package org.health.assignment.healthzassignment.controller;


import com.fasterxml.jackson.databind.JsonNode;
//import org.health.assignment.healthzassignment.dto.HealthStatus;
import org.health.assignment.healthzassignment.exception.BadRequestException;
import org.health.assignment.healthzassignment.service.IHealthCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//api's entry point

/**
 * This controller class will be used to check the health of application with DB.
 */
@RestController //rest controller is for api's
@RequestMapping("/healthz")//this controller is mapped to healthz
public class HealthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthController.class);


    @Autowired
    private IHealthCheck healthCheck;

    @GetMapping
    public ResponseEntity<Void> checkDatabaseHealth(@RequestBody(required = false) JsonNode body) {
        if (body != null) {
            LOGGER.error("Body is not supported in GET API.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Pragma", "no-cache");
        headers.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        boolean connectionStatus = healthCheck.checkDBConnectionStatus();
        if (connectionStatus) {
            LOGGER.info("Failed to get the connection.");
            return new ResponseEntity(headers, HttpStatus.OK);
        }
        return new ResponseEntity(headers, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
