package com.nexusbrain.app.service;

import com.nexusbrain.app.api.dto.request.AddProjectRequest;
import com.nexusbrain.app.api.dto.request.SearchProjectsQueryRequest;
import com.nexusbrain.app.api.dto.request.UpdateProjectRequest;
import com.nexusbrain.app.api.dto.response.ProjectDetailsResponse;
import com.nexusbrain.app.api.dto.response.TeamDetailsResponse;
import com.nexusbrain.app.converter.ProjectToDetailsConverter;
import com.nexusbrain.app.model.Project;
import com.nexusbrain.app.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.nexusbrain.app.exception.ApiException.projectNotFound;

@Service
public class ProjectService {

    private static final Logger LOG = LoggerFactory.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;
    private final ProjectToDetailsConverter projectToDetailsConverter;

    public ProjectService(ProjectRepository projectRepository, ProjectToDetailsConverter projectToDetailsConverter) {
        this.projectRepository = projectRepository;
        this.projectToDetailsConverter = projectToDetailsConverter;
    }

    public Project getProject(long projectId) {
        return projectRepository.findById(projectId).orElseThrow(() -> projectNotFound(projectId));
    }

    public ProjectDetailsResponse getProjectDetails(long projectId) {
        Project project = getProject(projectId);
        ProjectDetailsResponse projectDetails = projectToDetailsConverter.convert(project);
        LOG.info("Got project {id: {}} successfully", projectId);
        return projectDetails;
    }

    public List<ProjectDetailsResponse> findProjects(SearchProjectsQueryRequest query) {
        return null;
    }

    public List<TeamDetailsResponse> getProjectTeams(long projectId) {
        return null;
    }

    @Transactional
    public long addProject(AddProjectRequest request) {
        Objects.requireNonNull(request);
        Project project = new Project(request.getName(), request.getDescription());
        projectRepository.save(project);
        LOG.info("Project {projectId: {}} added successfully", project.getId());
        return project.getId();
    }

    @Transactional
    public void updateProject(long projectId, UpdateProjectRequest request) {
        Objects.requireNonNull(request);
        Project project = getProject(projectId);
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        LOG.info("Project {id: {}} updated successfully", projectId);
    }

    @Transactional
    public void deleteProject(long projectId) {
        Project project = getProject(projectId);
        project.removeTeams();
        projectRepository.delete(project);
        LOG.info("Project {id: {}} deleted successfully", projectId);
    }
}