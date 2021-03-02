package com.nexusbrain.app.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResourceCreatedResponse<T> {

    private T resourceId;

}
