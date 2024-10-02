package org.health.assignment.healthzassignment.exception;

import org.health.assignment.healthzassignment.dto.ErrorResponse;

public class BadRequestException extends RuntimeException {
    private ErrorResponse errorResponse;

    public BadRequestException(String message) {
        super(message);
        this.errorResponse = new ErrorResponse(message);
    }

    public BadRequestException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
