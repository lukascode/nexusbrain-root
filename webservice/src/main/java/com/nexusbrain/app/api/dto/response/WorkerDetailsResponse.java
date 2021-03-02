package com.nexusbrain.app.api.dto.response;

import lombok.Data;

@Data
public class WorkerDetailsResponse {

    private Long id;

    private String fullName;

    private String email;

    private String teamName;

    private Long teamId;

}
