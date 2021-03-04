package com.nexusbrain.app.assertion;

import com.nexusbrain.app.api.dto.response.TeamDetailsResponse;
import com.nexusbrain.app.api.error.ApiErrorDetails;
import com.nexusbrain.app.flow.TeamFlow;
import com.nexusbrain.app.model.Team;
import io.vavr.control.Either;
import org.assertj.core.api.Assertions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class TeamAssertions {

    private final TeamFlow teamFlow;

    public TeamAssertions(TeamFlow teamFlow) {
        this.teamFlow = teamFlow;
    }

    public TeamAssertion assertThat(long teamId) {
        return new TeamAssertion(teamFlow.getTeam(teamId));
    }

    public static class TeamAssertion {
        private final Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<TeamDetailsResponse>> either;

        public TeamAssertion(Either<ResponseEntity<ApiErrorDetails>, ResponseEntity<TeamDetailsResponse>> either) {
            this.either = either;
        }

        public TeamAssertion exists() {
            Assertions.assertThat(either.get().getStatusCode()).isEqualTo(HttpStatus.OK);
            return this;
        }

        public TeamAssertion notFound() {
            Assertions.assertThat(either.getLeft().getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            return this;
        }

        public TeamAssertion hasName(String name) {
            Assertions.assertThat(either.get().getBody().getName()).isEqualTo(name);
            return this;
        }

        public TeamAssertion hasDescription(String description) {
            Assertions.assertThat(either.get().getBody().getDescription()).isEqualTo(description);
            return this;
        }

        public TeamAssertion hasProjectId(long projectId) {
            Assertions.assertThat(either.get().getBody().getProjectId()).isEqualTo(projectId);
            return this;
        }

        public TeamAssertion hasProjectName(String projectName) {
            Assertions.assertThat(either.get().getBody().getProjectName()).isEqualTo(projectName);
            return this;
        }

        public TeamAssertion hasNumberOfWorkers(long numberOfWorkers) {
            Assertions.assertThat(either.get().getBody().getNumberOfWorkers()).isEqualTo(numberOfWorkers);
            return this;
        }
    }
}
