package com.nexusbrain.app.api;

import com.nexusbrain.app.api.dto.request.UpdateTeamRequest;
import com.nexusbrain.app.api.dto.response.TeamDetailsResponse;
import com.nexusbrain.app.api.dto.response.WorkerDetailsResponse;
import com.nexusbrain.app.api.error.ApiErrorDetails;
import com.nexusbrain.app.assertion.ErrorAssertions;
import com.nexusbrain.app.assertion.TeamAssertions;
import com.nexusbrain.app.assertion.WorkerAssertions;
import com.nexusbrain.app.base.BaseIT;
import com.nexusbrain.app.data.TeamData;
import com.nexusbrain.app.flow.ProjectFlow;
import com.nexusbrain.app.flow.TeamFlow;
import com.nexusbrain.app.flow.WorkerFlow;
import com.nexusbrain.app.repository.TeamRepository;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import java.util.List;

public class TeamControllerIT extends BaseIT {

    @Autowired
    private ProjectFlow projectFlow;

    @Autowired
    private TeamFlow teamFlow;

    @Autowired
    private WorkerFlow workerFlow;

    @Autowired
    private TeamAssertions teamAssertions;

    @Autowired
    private WorkerAssertions workerAssertions;

    @Autowired
    private ErrorAssertions errorAssertions;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void shouldAddTeamProperly() {
        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();

        // when
        long teamId = projectFlow.addTeamToProject(projectId).get().getBody().getResourceId();

        // then
        teamAssertions.assertThat(teamId)
                .exists()
                .hasName(TeamData.DEFAULT_NAME)
                .hasDescription(TeamData.DEFAULT_DESCRIPTION)
                .hasProjectId(projectId)
                .hasNumberOfWorkers(0);
    }

    @Test
    public void shouldNotAddTeamWhenBadRequest() {
        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();

        // when
        ResponseEntity<ApiErrorDetails> response = projectFlow.addTeamToProject(
                projectId, TeamData.AddTeamRequestBuilder.builder().withName(TeamData.BAD_NAME).build()).getLeft();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        errorAssertions.assertThat(response.getBody())
                .hasMessage("BAD_REQUEST")
                .hasDescription("name: must not be blank")
                .hasEventId();
    }

    @Test
    public void shouldReturnNotFoundWhenTeamDoesNotExists() {
        // when
        ResponseEntity<ApiErrorDetails> response = teamFlow.getTeam(TeamData.BAD_TEAM_ID).getLeft();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        errorAssertions.assertThat(response.getBody())
                .hasMessage("TEAM_NOT_FOUND")
                .hasDescription(String.format("Team with id: %d not found", TeamData.BAD_TEAM_ID))
                .hasEventId();
    }

    @Test
    public void shouldUpdateTeamProperly() {
        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();
        long teamId = projectFlow.addTeamToProject(projectId).get().getBody().getResourceId();

        // when
        HttpStatus status = teamFlow.updateTeam(teamId).get().getStatusCode();
        Assertions.assertThat(status).isEqualTo(HttpStatus.OK);

        // then
        teamAssertions.assertThat(teamId)
                .hasName(TeamData.DEFAULT_NAME_UPDATE)
                .hasDescription(TeamData.DEFAULT_DESCRIPTION_UPDATE)
                .hasProjectId(projectId)
                .hasNumberOfWorkers(0);
    }

    @Test
    public void shouldNotUpdateTeamWhenBadRequest() {
        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();
        long teamId = projectFlow.addTeamToProject(projectId).get().getBody().getResourceId();

        // when
        UpdateTeamRequest updateRequest = TeamData.UpdateTeamRequestBuilder.builder()
                                                  .withName(TeamData.BAD_NAME).build();
        ResponseEntity<ApiErrorDetails> response = teamFlow.updateTeam(teamId, updateRequest).getLeft();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        errorAssertions.assertThat(response.getBody())
                .hasMessage("BAD_REQUEST")
                .hasDescription("name: must not be blank")
                .hasEventId();
    }

    @Test
    public void shouldAddWorkerToTeamProperly() {
        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();
        long teamId = projectFlow.addTeamToProject(projectId).get().getBody().getResourceId();
        long workerId = workerFlow.addWorker().get().getBody().getResourceId();

        // when
        ResponseEntity<Void> response = teamFlow.addWorkerToTeam(teamId, workerId).get();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        teamAssertions.assertThat(teamId)
                .exists()
                .hasName(TeamData.DEFAULT_NAME)
                .hasDescription(TeamData.DEFAULT_DESCRIPTION)
                .hasProjectId(projectId)
                .hasNumberOfWorkers(1);

        workerAssertions.assertThat(workerId)
                .exists()
                .hasNumberOfTeams(1);
    }

    @Test
    public void shouldRemoveWorkerFromTeamProperly() {
        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();
        long teamId = projectFlow.addTeamToProject(projectId).get().getBody().getResourceId();
        long workerId = workerFlow.addWorker().get().getBody().getResourceId();
        HttpStatus status = teamFlow.addWorkerToTeam(teamId, workerId).get().getStatusCode();
        Assertions.assertThat(status).isEqualTo(HttpStatus.OK);
        teamAssertions.assertThat(teamId)
                .exists()
                .hasNumberOfWorkers(1);
        workerAssertions.assertThat(workerId)
                .exists()
                .hasNumberOfTeams(1);

        // when
        ResponseEntity<Void> response = teamFlow.removeWorkerFromTeam(teamId, workerId).get();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        teamAssertions.assertThat(teamId)
                .exists()
                .hasNumberOfWorkers(0);
        workerAssertions.assertThat(workerId)
                .exists()
                .hasNumberOfTeams(0);
    }

    @Test
    public void shouldDeleteTeamProperly() {
        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();
        long teamId = projectFlow.addTeamToProject(projectId).get().getBody().getResourceId();
        long workerId = workerFlow.addWorker().get().getBody().getResourceId();
        teamFlow.addWorkerToTeam(teamId, workerId).get().getStatusCode();

        // when
        teamFlow.deleteTeam(teamId);

        // then
        teamAssertions.assertThat(teamId).notFound();
        workerAssertions.assertThat(workerId)
                .exists()
                .hasNumberOfTeams(0);
    }

    @Test
    public void searchTeamsTest() {
        teamRepository.deleteAll();

        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();
        projectFlow.addTeamToProject(projectId);
        projectFlow.addTeamToProject(projectId);
        projectFlow.addTeamToProject(projectId);

        // when
        ResponseEntity<List<TeamDetailsResponse>> response = teamFlow.searchTeams().get();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).hasSize(3);
    }

    @Test
    public void searchTeamsByPhrase() {
        teamRepository.deleteAll();

        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();
        projectFlow.addTeamToProject(projectId);
        projectFlow.addTeamToProject(projectId);
        projectFlow.addTeamToProject(projectId,
                TeamData.AddTeamRequestBuilder.builder().withName("best team").build());

        // when
        ResponseEntity<List<TeamDetailsResponse>> response = teamFlow.searchTeams(
                TeamData.SearchTeamsQueryRequestBuilder.builder().withPhrase("best").build()).get();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).hasSize(1);
    }

    @Test
    public void getTeamWorkersTest() {
        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();
        long teamId = projectFlow.addTeamToProject(projectId).get().getBody().getResourceId();
        long worker1 = workerFlow.addWorker().get().getBody().getResourceId();
        long worker2 = workerFlow.addWorker().get().getBody().getResourceId();
        teamFlow.addWorkerToTeam(teamId, worker1);
        teamFlow.addWorkerToTeam(teamId, worker2);

        // when
        ResponseEntity<List<WorkerDetailsResponse>> response = teamFlow.getTeamWorkers(teamId).get();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).hasSize(2);
    }
}
