package com.nexusbrain.app.exception;

import com.nexusbrain.app.api.error.ApiErrorDetails;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ApiException extends RuntimeException {

    private final ApiErrorDetails errorDetails;

    private ApiException(ApiErrorDetails errorDetails, Throwable cause) {
        super(String.format("%s: %s", errorDetails.getMessage(), errorDetails.getDescription()), cause);
        this.errorDetails = errorDetails;
    }

    private ApiException(String message, String description, HttpStatus status) {
        this(new ApiErrorDetails(message, description, status), null);
    }

    private ApiException(String message, String description, HttpStatus status, Throwable cause) {
        this(new ApiErrorDetails(message, description, status), cause);
    }

    public ApiErrorDetails getErrorDetails() {
        return errorDetails;
    }

    public static ApiException workerNotFound(long workerId) {
        return new ApiException("WORKER_NOT_FOUND", String.format("Worker with id: %d not found", workerId), NOT_FOUND);
    }

    public static ApiException emailAlreadyExists(String email) {
        return new ApiException("EMAIL_ALREADY_EXISTS", String.format("Email '%s' already exists in the database", email), CONFLICT);
    }

    public static ApiException teamNotFound(long teamId) {
        return new ApiException("TEAM_NOT_FOUND", String.format("Team with id: %d not found", teamId), NOT_FOUND);
    }

    public static ApiException teamAlreadyHasWorker(long teamId, long workerId) {
        return new ApiException("TEAM_ALREADY_HAS_WORKER", String.format("Team with id: %d already has worker with id: %d", teamId, workerId), CONFLICT);
    }

    public static ApiException projectNotFound(long projectId) {
        return new ApiException("PROJECT_NOT_FOUND", String.format("Project with id: %d not found", projectId), NOT_FOUND);
    }

    public static ApiException projectNotContainTeam(long projectId, long teamId) {
        return new ApiException("PROJECT_NOT_CONTAIN_TEAM", String.format("Project with id: %d not contain team with id: %d", projectId, teamId), NOT_FOUND);
    }

    public static ApiException internalServerError(Throwable cause) {
        return new ApiException("INTERNAL_SERVER_ERROR", "Internal Server error", INTERNAL_SERVER_ERROR, cause);
    }
}
