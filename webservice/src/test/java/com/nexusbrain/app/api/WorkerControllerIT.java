package com.nexusbrain.app.api;

import com.nexusbrain.app.api.dto.request.AddWorkerRequest;
import com.nexusbrain.app.api.dto.response.ResourceCreatedResponse;
import com.nexusbrain.app.api.dto.response.WorkerDetailsResponse;
import com.nexusbrain.app.base.BaseIT;
import com.nexusbrain.app.repository.WorkerRepository;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import java.net.URI;

public class WorkerControllerIT extends BaseIT {

    private static final String FULL_NAME = "John Doe";
    private static final String EMAIL = "john@example.com";
    private static final String BAD_EMAIL = "bad_email";

    @Autowired
    private WorkerRepository workerRepository;

    @Test
    public void shouldSaveWorkerProperly() {
        // given
        AddWorkerRequest addWorkerRequest = prepareAddWorkerRequest();

        RequestEntity<AddWorkerRequest> postRequest = new RequestEntity<>(
                addWorkerRequest,
                HttpMethod.POST,
                URI.create("/nb/v1.0/workers/add")
        );

        // when
        ResponseEntity<ResourceCreatedResponse<Long>> response = testRestTemplate.exchange(postRequest, new ParameterizedTypeReference<ResourceCreatedResponse<Long>>(){});

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Long workerId = response.getBody().getResourceId();
        Assertions.assertThat(workerId).isNotNull();
        Assertions.assertThat(workerRepository.existsById(workerId));

        ResponseEntity<WorkerDetailsResponse> getResponse = testRestTemplate.getForEntity("/nb/v1.0/workers/{workerId}/get", WorkerDetailsResponse.class, workerId);
        Assertions.assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        WorkerDetailsResponse workerDetails = getResponse.getBody();
        Assertions.assertThat(workerDetails.getFullName()).isEqualTo(FULL_NAME);
        Assertions.assertThat(workerDetails.getEmail()).isEqualTo(EMAIL);
        Assertions.assertThat(workerDetails.getTeamId()).isNull();
        Assertions.assertThat(workerDetails.getTeamName()).isNull();
    }

    private AddWorkerRequest prepareAddWorkerRequest() {
        AddWorkerRequest addWorkerRequest = new AddWorkerRequest();
        addWorkerRequest.setFullName(FULL_NAME);
        addWorkerRequest.setEmail(EMAIL);
        return addWorkerRequest;
    }

    private AddWorkerRequest prepareAddWorkerRequestWithBadEmail() {
        AddWorkerRequest addWorkerRequest = new AddWorkerRequest();
        addWorkerRequest.setFullName(FULL_NAME);
        addWorkerRequest.setEmail(BAD_EMAIL);
        return addWorkerRequest;
    }

}