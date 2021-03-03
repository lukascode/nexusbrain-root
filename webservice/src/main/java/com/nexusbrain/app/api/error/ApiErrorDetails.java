package com.nexusbrain.app.api.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiErrorDetails {

    private String message;

    private String description;

    private String eventId;

    @JsonIgnore
    private HttpStatus status;

    public ApiErrorDetails() {}

    public ApiErrorDetails(String message, String description, HttpStatus status) {
        this.message = message;
        this.description = description;
        this.status = status;
    }
}
