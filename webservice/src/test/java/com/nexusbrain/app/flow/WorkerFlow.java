package com.nexusbrain.app.flow;

import com.google.gson.reflect.TypeToken;
import com.nexusbrain.app.api.dto.request.AddWorkerRequest;
import com.nexusbrain.app.api.dto.request.UpdateWorkerRequest;
import com.nexusbrain.app.api.dto.response.ResourceCreatedResponse;
import com.nexusbrain.app.api.dto.response.WorkerDetailsResponse;
import com.nexusbrain.app.api.error.ApiErrorDetails;
import com.nexusbrain.app.data.WorkerData;
import io.vavr.control.Either;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class WorkerFlow {

    private final TestRestTemplate testRestTemplate;

    public WorkerFlow(TestRestTemplate testRestTemplate) {
        this.testRestTemplate = testRestTemplate;
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<ResourceCreatedResponse<Long>>> addWorker() {
        return addWorker(WorkerData.AddWorkerRequestBuilder.builder().build());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<ResourceCreatedResponse<Long>>> addWorker(AddWorkerRequest request) {
        ResponseEntity<String> response = testRestTemplate.postForEntity("/nb/v1.0/workers/add", request, String.class);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<ResourceCreatedResponse<Long>>(){}.getType());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<WorkerDetailsResponse>> getWorker(long workerId) {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/nb/v1.0/workers/{workerId}/get", String.class, workerId);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<WorkerDetailsResponse>(){}.getType());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<Void>> updateWorker(long workerId) {
        return updateWorker(workerId, WorkerData.UpdateWorkerRequestBuilder.builder().build());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<Void>> updateWorker(long workerId, UpdateWorkerRequest request) {
        RequestEntity<UpdateWorkerRequest> requestEntity = new RequestEntity<>(request, HttpMethod.PUT, URI.create(String.format("/nb/v1.0/workers/%d/update", workerId)));
        ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<Void>(){}.getType());
    }

    public Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<Void>> deleteWorker(long workerId) {
        RequestEntity<Void> requestEntity = new RequestEntity<>(HttpMethod.DELETE, URI.create(String.format("/nb/v1.0/workers/%d/delete", workerId)));
        ResponseEntity<String> response = testRestTemplate.exchange(requestEntity, String.class);
        return FlowUtil.parseAndGetResponse(response, new TypeToken<Void>(){}.getType());
    }
}
