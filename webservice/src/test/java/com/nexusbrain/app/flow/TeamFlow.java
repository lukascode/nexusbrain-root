package com.nexusbrain.app.flow;

import com.google.gson.reflect.TypeToken;
import com.nexusbrain.app.api.dto.request.SearchTeamsQueryRequest;
import com.nexusbrain.app.api.dto.request.UpdateTeamRequest;
import com.nexusbrain.app.api.dto.response.TeamDetailsResponse;
import com.nexusbrain.app.api.dto.response.WorkerDetailsResponse;
import com.nexusbrain.app.api.error.ApiErrorDetails;
import com.nexusbrain.app.data.TeamData;
import io.vavr.control.Either;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;

@Component
public class TeamFlow {

    private final TestRestTemplate testRestTemplate;

    public TeamFlow(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<TeamDetailsResponse>> getTeam(long teamId) {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/nb/v1.0/teams/{teamId}/get", String.class, teamId);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<TeamDetailsResponse>(){}.getType());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<Void>> updateTeam(long teamId) {
        return updateTeam(teamId, TeamData.UpdateTeamRequestBuilder.builder().build());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<Void>> updateTeam(long teamId, UpdateTeamRequest request) {
        RequestEntity<UpdateTeamRequest> requestEntity = new RequestEntity<>(request, HttpMethod.PUT, URI.create(String.format("/nb/v1.0/teams/%d/update", teamId)));
        ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<Void>(){}.getType());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<Void>> deleteTeam(long teamId) {
        RequestEntity<UpdateTeamRequest> requestEntity = new RequestEntity<>(HttpMethod.DELETE, URI.create(String.format("/nb/v1.0/teams/%d/delete", teamId)));
        ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<Void>(){}.getType());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<Void>> addWorkerToTeam(long teamId, long workerId) {
        RequestEntity<UpdateTeamRequest> requestEntity = new RequestEntity<>(HttpMethod.PUT, URI.create(String.format("/nb/v1.0/teams/%d/workers/add?workerId=%d", teamId, workerId)));
        ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<Void>(){}.getType());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<Void>> removeWorkerFromTeam(long teamId, long workerId) {
        RequestEntity<UpdateTeamRequest> requestEntity = new RequestEntity<>(HttpMethod.DELETE, URI.create(String.format("/nb/v1.0/teams/%d/workers/remove?workerId=%d", teamId, workerId)));
        ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<Void>(){}.getType());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<List<TeamDetailsResponse>>> searchTeams() {
        return searchTeams(TeamData.SearchTeamsQueryRequestBuilder.builder().build());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<List<TeamDetailsResponse>>> searchTeams(SearchTeamsQueryRequest request) {
        String url = "/nb/v1.0/teams/search";
        if (request.getPhrase() != null) {
            url += "?phrase=" + request.getPhrase();
        }
        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<List<TeamDetailsResponse>>(){}.getType());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<List<WorkerDetailsResponse>>> getTeamWorkers(long teamId) {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/nb/v1.0/teams/{teamId}/workers", String.class, teamId);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<List<WorkerDetailsResponse>>(){}.getType());
    }
}
