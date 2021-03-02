package com.nexusbrain.app.converter;

import com.nexusbrain.app.api.dto.response.TeamDetailsResponse;
import com.nexusbrain.app.model.Project;
import com.nexusbrain.app.model.Team;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TeamToDetailsConverter implements Converter<Team, TeamDetailsResponse> {
    @Override
    public TeamDetailsResponse convert(Team team) {
        Objects.requireNonNull(team);
        TeamDetailsResponse response = new TeamDetailsResponse();
        response.setId(team.getId());
        response.setName(team.getName());
        response.setDescription(team.getDescription());
        response.setNumberOfWorkers(team.getNumberOfWorkers());
        if (team.hasProject()) {
            Project project = team.getProject();
            response.setProjectId(project.getId());
            response.setProjectName(project.getName());
        }
        return response;
    }
}
