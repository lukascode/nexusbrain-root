package com.nexusbrain.app.flow;

import com.google.gson.reflect.TypeToken;
import com.nexusbrain.app.api.dto.request.AddProjectRequest;
import com.nexusbrain.app.api.dto.request.AddTeamRequest;
import com.nexusbrain.app.api.dto.response.ProjectDetailsResponse;
import com.nexusbrain.app.api.dto.response.ResourceCreatedResponse;
import com.nexusbrain.app.api.error.ApiErrorDetails;
import com.nexusbrain.app.data.ProjectData;
import com.nexusbrain.app.data.TeamData;
import io.vavr.control.Either;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class ProjectFlow {

    private final TestRestTemplate testRestTemplate;

    public ProjectFlow(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<ResourceCreatedResponse<Long>>> addProject() {
        return addProject(ProjectData.AddProjectRequestBuilder.builder().build());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<ResourceCreatedResponse<Long>>> addProject(AddProjectRequest request) {
        ResponseEntity<String> response = testRestTemplate.postForEntity("/nb/v1.0/projects/add", request, String.class);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<ResourceCreatedResponse<Long>>(){}.getType());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<ResourceCreatedResponse<Long>>> addTeamToProject(long projectId) {
        return addTeamToProject(projectId, TeamData.AddTeamRequestBuilder.builder().build());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<ResourceCreatedResponse<Long>>> addTeamToProject(long projectId, AddTeamRequest request) {
        RequestEntity<AddTeamRequest> requestEntity = new RequestEntity<>(request, HttpMethod.PUT, URI.create(String.format("/nb/v1.0/projects/%d/teams/add", projectId)));
        ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<ResourceCreatedResponse<Long>>(){}.getType());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<ProjectDetailsResponse>> getProject(long projectId) {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/nb/v1.0/projects/{projectId}/get", String.class, projectId);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<ProjectDetailsResponse>(){}.getType());
    }
}
