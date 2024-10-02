package org.health.assignment.healthzassignment.controller;


import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.health.assignment.healthzassignment.exception.BadRequestException;
import org.health.assignment.healthzassignment.service.IHealthCheck;
import org.health.assignment.healthzassignment.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

//api's entry point

/**
 * This controller class will be used to check the health of application with DB.
 */
@RestController //rest controller is for api's
@RequestMapping("/healthz")//this controller is mapped to healthz
public class HealthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthController.class);

    private final IHealthCheck healthCheck;

    private final CommonUtils commonUtils;

    @Autowired
    public HealthController(IHealthCheck healthCheck, CommonUtils commonUtils) {
        this.healthCheck = healthCheck;
        this.commonUtils = commonUtils;
    }

    @GetMapping
    public ResponseEntity<Void> checkDatabaseHealth(@RequestBody(required = false) JsonNode body, HttpServletRequest httpServletRequest) {
        if (body != null) {
            LOGGER.error("Payload is not supported in GET API.");
            throw new BadRequestException("Payload is not supported in GET API.");
        }
        if (httpServletRequest.getQueryString() != null) {
            LOGGER.error("Query Parameter is not supported in this API.");
            throw new BadRequestException("Query Parameter is not supported in this API.");
        }
        boolean connectionStatus = healthCheck.checkDBConnectionStatus();
        if (connectionStatus) {
            LOGGER.info("Failed to get the connection.");
            return new ResponseEntity<>(commonUtils.getHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(commonUtils.getHeaders(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.POST,
            RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.TRACE})
    public void handleUnsupportedMethodForHealthzApi(HttpServletRequest request) throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException(request.getMethod());
    }

}
