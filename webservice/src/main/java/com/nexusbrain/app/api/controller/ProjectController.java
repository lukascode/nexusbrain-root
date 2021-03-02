package com.nexusbrain.app.api.controller;

import com.nexusbrain.app.api.dto.request.AddProjectRequest;
import com.nexusbrain.app.api.dto.request.SearchProjectsQueryRequest;
import com.nexusbrain.app.api.dto.request.UpdateProjectRequest;
import com.nexusbrain.app.api.dto.response.ProjectDetailsResponse;
import com.nexusbrain.app.api.dto.response.ResourceCreatedResponse;
import com.nexusbrain.app.api.dto.response.TeamDetailsResponse;
import com.nexusbrain.app.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/nb/v1.0/projects")
public class ProjectController {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectController.class);

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/add")
    public ResponseEntity<ResourceCreatedResponse<Long>> addProject(@Valid @RequestBody AddProjectRequest request) {
        LOG.info("Received addProject request");
        return new ResponseEntity<>(new ResourceCreatedResponse<>(projectService.addProject(request)), HttpStatus.CREATED);
    }

    @PutMapping("/{projectId}/update")
    public ResponseEntity<Void> updateProject(@PathVariable long projectId, @Valid @RequestBody UpdateProjectRequest request) {
        LOG.info("Received updateProject request");
        projectService.updateProject(projectId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{projectId}/get")
    public ResponseEntity<ProjectDetailsResponse> getProject(@PathVariable long projectId) {
        LOG.info("Received getProject request");
        return new ResponseEntity<>(projectService.getProjectDetails(projectId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProjectDetailsResponse>> searchProjects(@Valid SearchProjectsQueryRequest query) {
        LOG.info("Received searchProjects request");
        return new ResponseEntity<>(projectService.findProjects(query), HttpStatus.OK);
    }

    @GetMapping("/{projectId}/teams")
    public ResponseEntity<List<TeamDetailsResponse>> getProjectTeams(@PathVariable long projectId) {
        LOG.info("Received getProjectTeams request");
        return new ResponseEntity<>(projectService.getProjectTeams(projectId), HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}/delete")
    public ResponseEntity<Void> deleteProject(@PathVariable long projectId) {
        LOG.info("Received deleteTeam request");
        projectService.deleteProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
