package com.nexusbrain.app.api.dto;

import lombok.Data;

@Data
public class SearchWorkersQuery {

    private String phrase;

    private Long teamId;

}
