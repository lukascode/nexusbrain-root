package com.nexusbrain.app.api.dto;

import lombok.Data;

@Data
public class WorkerDetails {

    private Long id;

    private String fullName;

    private String email;

    private String teamName;

    private Long teamId;

}
