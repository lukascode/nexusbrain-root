package com.nexusbrain.app.converter;

import com.nexusbrain.app.api.dto.response.ProjectDetailsResponse;
import com.nexusbrain.app.model.Project;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProjectToDetailsConverter implements Converter<Project, ProjectDetailsResponse> {
    @Override
    public ProjectDetailsResponse convert(Project project) {
        Objects.requireNonNull(project);
        ProjectDetailsResponse response = new ProjectDetailsResponse();
        response.setId(project.getId());
        response.setName(project.getName());
        response.setDescription(project.getDescription());
        response.setNumberOfTeams(project.getNumberOfTeams());
        return response;
    }
}
