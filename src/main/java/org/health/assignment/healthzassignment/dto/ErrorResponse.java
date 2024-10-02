package org.health.assignment.healthzassignment.dto;

public class ErrorResponse {
    private String errorMessage;

    public ErrorResponse(String error) {
        this.errorMessage = error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
