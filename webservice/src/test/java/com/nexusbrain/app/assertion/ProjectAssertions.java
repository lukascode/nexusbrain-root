package com.nexusbrain.app.assertion;

import com.nexusbrain.app.api.dto.response.ProjectDetailsResponse;
import com.nexusbrain.app.api.error.ApiErrorDetails;
import com.nexusbrain.app.flow.ProjectFlow;
import io.vavr.control.Either;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ProjectAssertions {

    private final ProjectFlow projectFlow;

    public ProjectAssertions(ProjectFlow projectFlow) {
        this.projectFlow = projectFlow;
    }

    public ProjectAssertion assertThat(long projectId) {
        return new ProjectAssertion(projectFlow.getProject(projectId));
    }

    public static class ProjectAssertion {
        private final Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<ProjectDetailsResponse>> either;

        public ProjectAssertion(Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<ProjectDetailsResponse>> either) {
            this.either = either;
        }

        public ProjectAssertion exists() {
            Assertions.assertThat(either.get().getStatusCode()).isEqualTo(HttpStatus.OK);
            return this;
        }

        public ProjectAssertion notFound() {
            Assertions.assertThat(either.getLeft().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            return this;
        }

        public ProjectAssertion hasName(String name) {
            Assertions.assertThat(either.get().getBody().getName()).isEqualTo(name);
            return this;
        }

        public ProjectAssertion hasDescription(String description) {
            Assertions.assertThat(either.get().getBody().getDescription()).isEqualTo(description);
            return this;
        }

        public ProjectAssertion hasNumberOfTeams(int numberOfTeams) {
            Assertions.assertThat(either.get().getBody().getNumberOfTeams()).isEqualTo(numberOfTeams);
            return this;
        }
    }
}
