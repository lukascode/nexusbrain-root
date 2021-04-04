package com.nexusbrain.app.api;

import com.nexusbrain.app.api.dto.response.ProjectDetailsResponse;
import com.nexusbrain.app.api.dto.response.ResourceCreatedResponse;
import com.nexusbrain.app.api.dto.response.TeamDetailsResponse;
import com.nexusbrain.app.api.error.ApiErrorDetails;
import com.nexusbrain.app.assertion.ErrorAssertions;
import com.nexusbrain.app.assertion.ProjectAssertions;
import com.nexusbrain.app.assertion.TeamAssertions;
import com.nexusbrain.app.assertion.WorkerAssertions;
import com.nexusbrain.app.base.BaseIT;
import com.nexusbrain.app.data.ProjectData;
import com.nexusbrain.app.data.WorkerData;
import com.nexusbrain.app.flow.ProjectFlow;
import com.nexusbrain.app.flow.TeamFlow;
import com.nexusbrain.app.flow.WorkerFlow;
import com.nexusbrain.app.repository.ProjectRepository;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import java.util.List;

public class ProjectControllerIT extends BaseIT {

    @Autowired
    private ProjectFlow projectFlow;

    @Autowired
    private ProjectAssertions projectAssertions;

    @Autowired
    private TeamAssertions teamAssertions;

    @Autowired
    private WorkerAssertions workerAssertions;

    @Autowired
    private ErrorAssertions errorAssertions;

    @Autowired
    private TeamFlow teamFlow;

    @Autowired
    private WorkerFlow workerFlow;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void shouldAddProjectProperly() {
        // when
        ResponseEntity<ResourceCreatedResponse<Long>> response = projectFlow.addProject().get();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        projectAssertions.assertThat(response.getBody().getResourceId())
                .exists()
                .hasName(ProjectData.DEFAULT_NAME)
                .hasDescription(ProjectData.DEFAULT_DESCRIPTION)
                .hasNumberOfTeams(0);
    }

    @Test
    public void shouldNotAddProjectWhenBadRequest() {
        // when
        ResponseEntity<ApiErrorDetails> response = projectFlow.addProject(
                ProjectData.AddProjectRequestBuilder.builder().withName(ProjectData.BAD_NAME).build()).getLeft();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        errorAssertions.assertThat(response.getBody())
                .hasMessage("BAD_REQUEST")
                .hasDescription("name: must not be blank")
                .hasEventId();
    }

    @Test
    public void shouldReturnNotFoundWhenProjectDoesNotExists() {
        // when
        ResponseEntity<ApiErrorDetails> response = projectFlow.getProject(ProjectData.BAD_PROJECT_ID).getLeft();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        errorAssertions.assertThat(response.getBody())
                .hasMessage("PROJECT_NOT_FOUND")
                .hasDescription(String.format("Project with id: %d not found", ProjectData.BAD_PROJECT_ID))
                .hasEventId();
    }

    @Test
    public void shouldUpdateProjectProperly() {
        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();

        // when
        HttpStatus status = projectFlow.updateProject(projectId).get().getStatusCode();

        // then
        Assertions.assertThat(status).isEqualTo(HttpStatus.OK);
        projectAssertions.assertThat(projectId)
                .exists()
                .hasName(ProjectData.DEFAULT_NAME_UPDATE)
                .hasDescription(ProjectData.DEFAULT_DESCRIPTION_UPDATE)
                .hasNumberOfTeams(0);
    }

    @Test
    public void shouldDeleteProject() {
        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();
        projectAssertions.assertThat(projectId).exists();

        // when
        HttpStatus status = projectFlow.deleteProject(projectId).get().getStatusCode();

        // then
        Assertions.assertThat(status).isEqualTo(HttpStatus.NO_CONTENT);
        projectAssertions.assertThat(projectId).notFound();
    }

    @Test
    public void shouldDeleteProjectWithTeamsAndWorkersProperly() {
        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();
        long team1 = projectFlow.addTeamToProject(projectId).get().getBody().getResourceId();
        long team2 = projectFlow.addTeamToProject(projectId).get().getBody().getResourceId();
        long worker1 = workerFlow.addWorker(
                WorkerData.AddWorkerRequestBuilder
                        .builder().withEmail("test1@test").build()
        ).get().getBody().getResourceId();
        long worker2 = workerFlow.addWorker(
                WorkerData.AddWorkerRequestBuilder
                        .builder().withEmail("test2@test").build()
        ).get().getBody().getResourceId();
        teamFlow.addWorkerToTeam(team1, worker1);
        teamFlow.addWorkerToTeam(team2, worker1);
        teamFlow.addWorkerToTeam(team2, worker2);

        projectAssertions.assertThat(projectId)
                .exists()
                .hasNumberOfTeams(2);

        teamAssertions.assertThat(team1)
                .exists()
                .hasProjectId(projectId)
                .hasNumberOfWorkers(1);

        teamAssertions.assertThat(team2)
                .exists()
                .hasProjectId(projectId)
                .hasNumberOfWorkers(2);

        workerAssertions.assertThat(worker1)
                .exists()
                .hasNumberOfTeams(2);

        workerAssertions.assertThat(worker2)
                .exists()
                .hasNumberOfTeams(1);

        // when
        HttpStatus status = projectFlow.deleteProject(projectId).get().getStatusCode();

        // then
        Assertions.assertThat(status).isEqualTo(HttpStatus.NO_CONTENT);

        projectAssertions.assertThat(projectId).notFound();
        teamAssertions.assertThat(team1).notFound();
        teamAssertions.assertThat(team2).notFound();

        workerAssertions.assertThat(worker1)
                .exists()
                .hasNumberOfTeams(0);

        workerAssertions.assertThat(worker2)
                .exists()
                .hasNumberOfTeams(0);
    }

    @Test
    public void shouldAddTeamToProject() {
        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();

        // when
        HttpStatus status = projectFlow.addTeamToProject(projectId).get().getStatusCode();

        // then
        Assertions.assertThat(status).isEqualTo(HttpStatus.CREATED);

        projectAssertions.assertThat(projectId)
                .exists()
                .hasNumberOfTeams(1);
    }

    @Test
    public void shouldRemoveTeamFromProject() {
        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();
        long teamId = projectFlow.addTeamToProject(projectId).get().getBody().getResourceId();

        // when
        teamFlow.deleteTeam(teamId);

        // then
        projectAssertions.assertThat(projectId)
                .exists()
                .hasNumberOfTeams(0);
    }

    @Test
    public void shouldMoveTeamToAnotherProject() {
        // given
        long project1 = projectFlow.addProject().get().getBody().getResourceId();
        long project2 = projectFlow.addProject().get().getBody().getResourceId();
        long teamId = projectFlow.addTeamToProject(project1).get().getBody().getResourceId();

        projectAssertions.assertThat(project1)
                .exists()
                .hasNumberOfTeams(1);
        projectAssertions.assertThat(project2)
                .exists()
                .hasNumberOfTeams(0);

        // when
        HttpStatus status = projectFlow.moveTeamToAnotherProject(project1, teamId, project2).get().getStatusCode();

        // then
        Assertions.assertThat(status).isEqualTo(HttpStatus.OK);

        projectAssertions.assertThat(project1)
                .exists()
                .hasNumberOfTeams(0);
        projectAssertions.assertThat(project2)
                .exists()
                .hasNumberOfTeams(1);
    }

    @Test
    public void searchProjectsTest() {
        projectRepository.deleteAll();

        // given
        projectFlow.addProject();
        projectFlow.addProject();
        projectFlow.addProject();

        // when
        ResponseEntity<List<ProjectDetailsResponse>> response = projectFlow.searchProjects().get();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).hasSize(3);
    }

    @Test
    public void searchProjectsByPhrase() {
        projectRepository.deleteAll();

        // given
        projectFlow.addProject();
        projectFlow.addProject();
        projectFlow.addProject(ProjectData.AddProjectRequestBuilder
                .builder().withDescription("creative project").build());

        // when
        ResponseEntity<List<ProjectDetailsResponse>> response = projectFlow.searchProjects(ProjectData.SearchProjectsQueryRequestBuilder
                .builder().withPhrase("creative").build()).get();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).hasSize(1);
    }

    @Test
    public void getProjectTeamsTest() {
        projectRepository.deleteAll();

        // given
        long projectId = projectFlow.addProject().get().getBody().getResourceId();
        projectFlow.addTeamToProject(projectId);
        projectFlow.addTeamToProject(projectId);
        projectFlow.addTeamToProject(projectId);
        projectFlow.addTeamToProject(projectId);

        // when
        ResponseEntity<List<TeamDetailsResponse>> response = projectFlow.getProjectTeams(projectId).get();

        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).hasSize(4);
    }
}
