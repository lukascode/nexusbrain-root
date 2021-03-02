package com.nexusbrain.app.api.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateProjectRequest {

    @NotBlank
    private String name;

    @NotNull
    private String description;

}
