package com.nexusbrain.app.api.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateWorkerRequest {

    @NotBlank
    private String fullName;

    @Email
    private String email;

}
