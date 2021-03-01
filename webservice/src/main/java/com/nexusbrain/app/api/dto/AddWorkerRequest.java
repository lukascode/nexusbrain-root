package com.nexusbrain.app.api.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AddWorkerRequest {

    @NotBlank
    private String fullName;

    @Email
    private String email;

}
