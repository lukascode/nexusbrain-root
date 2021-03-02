package com.nexusbrain.app.api.dto.response;

import lombok.Data;

@Data
public class ProjectDetailsResponse {

    private Long id;

    private String name;

    private String description;

    private Integer numberOfTeams;

}
