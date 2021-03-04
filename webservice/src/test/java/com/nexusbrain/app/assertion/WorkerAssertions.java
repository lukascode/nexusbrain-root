package com.nexusbrain.app.assertion;

import com.nexusbrain.app.api.dto.response.WorkerDetailsResponse;
import com.nexusbrain.app.api.error.ApiErrorDetails;
import com.nexusbrain.app.flow.WorkerFlow;
import io.vavr.control.Either;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class WorkerAssertions {

    private final WorkerFlow workerFlow;

    public WorkerAssertions(WorkerFlow workerFlow) {
        this.workerFlow = workerFlow;
    }

    public WorkerAssertion assertThat(long workerId) {
        return new WorkerAssertion(workerFlow.getWorker(workerId));
    }

    public static class WorkerAssertion {
        private final Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<WorkerDetailsResponse>> either;

        public WorkerAssertion(Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<WorkerDetailsResponse>> either) {
            this.either = either;
        }

        public WorkerAssertion exists() {
            Assertions.assertThat(right().getStatusCode()).isEqualTo(HttpStatus.OK);
            return this;
        }

        public WorkerAssertion notFound() {
            Assertions.assertThat(left().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            return this;
        }

        public WorkerAssertion hasEmail(String email) {
            Assertions.assertThat(right().getBody().getEmail()).isEqualTo(email);
            return this;
        }

        public WorkerAssertion hasFullName(String fullName) {
            Assertions.assertThat(right().getBody().getFullName()).isEqualTo(fullName);
            return this;
        }

        public WorkerAssertion hasNumberOfTeams(int numberOfTeams) {
            Assertions.assertThat(right().getBody().getNumberOfTeams()).isEqualTo(numberOfTeams);
            return this;
        }

        private ResponseEntity<ApiErrorDetails> left() {
            return either.getLeft();
        }

        private ResponseEntity<WorkerDetailsResponse> right() {
            return either.get();
        }
    }
}
