package com.nexusbrain.app.api;

import com.nexusbrain.app.api.dto.request.UpdateWorkerRequest;
import com.nexusbrain.app.api.dto.response.ResourceCreatedResponse;
import com.nexusbrain.app.api.dto.response.WorkerDetailsResponse;
import com.nexusbrain.app.api.error.ApiErrorDetails;
import com.nexusbrain.app.assertion.ErrorAssertions;
import com.nexusbrain.app.assertion.WorkerAssertions;
import com.nexusbrain.app.base.BaseIT;
import com.nexusbrain.app.data.WorkerData;
import com.nexusbrain.app.flow.WorkerFlow;
import com.nexusbrain.app.repository.WorkerRepository;
import io.vavr.control.Either;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

public class WorkerControllerIT extends BaseIT {

    @Autowired
    private WorkerFlow workerFlow;

    @Autowired
    private WorkerAssertions workerAssertions;

    @Autowired
    private ErrorAssertions errorAssertions;

    @Autowired
    private WorkerRepository workerRepository;

    @Test
    public void shouldAddWorkerProperly() {
        // when
        Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<ResourceCreatedResponse<Long>>> either = workerFlow.addWorker();
        ResponseEntity<ResourceCreatedResponse<Long>> response = either.get();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        workerAssertions.assertThat(response.getBody().getResourceId())
                .exists()
                .hasEmail(WorkerData.DEFAULT_EMAIL)
                .hasFullName(WorkerData.DEFAULT_FULL_NAME);
    }

    @Test
    public void shouldNotAddWorkerWhenBadEmail() {
        // when
        Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<ResourceCreatedResponse<Long>>> either = workerFlow.addWorker(
                WorkerData.AddWorkerRequestBuilder.builder()
                        .withEmail(WorkerData.BAD_EMAIL).build()
        );
        ResponseEntity<ApiErrorDetails> response = either.getLeft();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        errorAssertions.assertThat(response.getBody())
                .hasMessage("BAD_REQUEST")
                .hasDescription("email: must be a well-formed email address")
                .hasEventId();
    }

    @Test
    public void shouldReturnNotFoundWhenWorkerDoesNotExists() {
        // when
        Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<WorkerDetailsResponse>> either = workerFlow.getWorker(WorkerData.BAD_WORKER_ID);
        ResponseEntity<ApiErrorDetails> response = either.getLeft();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        errorAssertions.assertThat(response.getBody())
                .hasMessage("WORKER_NOT_FOUND")
                .hasDescription(String.format("Worker with id: %d not found", WorkerData.BAD_WORKER_ID))
                .hasEventId();
    }

    @Test
    public void shouldUpdateWorkerProperly() {
        // given
        long workerId = workerFlow.addWorker().get().getBody().getResourceId();

        // when
        HttpStatus status = workerFlow.updateWorker(workerId).get().getStatusCode();
        Assertions.assertThat(status).isEqualTo(HttpStatus.OK);

        // then
        workerAssertions.assertThat(workerId)
                .exists()
                .hasFullName(WorkerData.DEFAULT_FULL_NAME_UPDATE)
                .hasEmail(WorkerData.DEFAULT_EMAIL_UPDATE);
    }

    @Test
    public void shouldNotUpdateWorkerWhenBadRequest() {
        // given
        long workerId = workerFlow.addWorker().get().getBody().getResourceId();

        // when
        UpdateWorkerRequest updateRequest = WorkerData.UpdateWorkerRequestBuilder.builder()
                                                      .withFullName(WorkerData.BAD_FULL_NAME).build();
        Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<Void>> either = workerFlow.updateWorker(workerId, updateRequest);
        ResponseEntity<ApiErrorDetails> response = either.getLeft();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        errorAssertions.assertThat(response.getBody())
                .hasMessage("BAD_REQUEST")
                .hasDescription("fullName: must not be blank")
                .hasEventId();
    }

    @Test
    public void shouldDeleteWorkerProperly() {
        // given
        long workerId = workerFlow.addWorker().get().getBody().getResourceId();

        // when
        Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<Void>> either = workerFlow.deleteWorker(workerId);
        ResponseEntity<Void> response = either.get();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        ResponseEntity<ApiErrorDetails> getResponse = workerFlow.getWorker(workerId).getLeft();
        Assertions.assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        errorAssertions.assertThat(getResponse.getBody())
                .hasMessage("WORKER_NOT_FOUND")
                .hasDescription(String.format("Worker with id: %d not found", workerId))
                .hasEventId();
    }

    @Test
    public void searchWorkersTest() {
        workerRepository.deleteAll();

        // given 3 workers with default name and email
        workerFlow.addWorker();
        workerFlow.addWorker();
        workerFlow.addWorker();

        // when
        Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<Page<WorkerDetailsResponse>>> either = workerFlow.searchWorkers();

        ResponseEntity<Page<WorkerDetailsResponse>> response = either.get();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getNumberOfElements()).isEqualTo(3);
    }

    @Test
    public void searchWorkersByPhraseTest() {
        workerRepository.deleteAll();

        // given
        workerFlow.addWorker(
                WorkerData.AddWorkerRequestBuilder
                        .builder().withEmail("john@example.com").build()
        );
        workerFlow.addWorker(
                WorkerData.AddWorkerRequestBuilder
                        .builder().withEmail("mark@example.com").build()
        );
        workerFlow.addWorker(
                WorkerData.AddWorkerRequestBuilder
                        .builder().withEmail("john@test").build()
        );

        // when
        Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<Page<WorkerDetailsResponse>>> either = workerFlow.searchWorkers(
                WorkerData.SearchWorkersQueryRequestBuilder
                        .builder().withPhrase("example.com").build()
        );

        ResponseEntity<Page<WorkerDetailsResponse>> response = either.get();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getNumberOfElements()).isEqualTo(2);
    }
}