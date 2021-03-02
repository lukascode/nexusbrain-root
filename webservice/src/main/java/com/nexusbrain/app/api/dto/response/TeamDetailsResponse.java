package com.nexusbrain.app.api.dto.response;

import lombok.Data;

@Data
public class TeamDetailsResponse {

    private Long id;

    private String name;

    private String description;

    private Long projectId;

    private String projectName;

    private Integer numberOfWorkers;

}
