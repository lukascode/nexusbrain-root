package com.nexusbrain.app.service;

import com.nexusbrain.app.api.dto.request.AddTeamRequest;
import com.nexusbrain.app.api.dto.request.SearchTeamsQueryRequest;
import com.nexusbrain.app.api.dto.request.UpdateTeamRequest;
import com.nexusbrain.app.api.dto.response.TeamDetailsResponse;
import com.nexusbrain.app.api.dto.response.WorkerDetailsResponse;
import com.nexusbrain.app.converter.TeamToDetailsConverter;
import com.nexusbrain.app.converter.WorkerToDetailsConverter;
import com.nexusbrain.app.model.Team;
import com.nexusbrain.app.model.Worker;
import com.nexusbrain.app.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.nexusbrain.app.exception.ApiException.teamNotFound;
import static com.nexusbrain.app.exception.ApiException.workerAlreadyHasTeam;

@Service
public class TeamService {

    private static final Logger LOG = LoggerFactory.getLogger(TeamService.class);

    private final TeamRepository teamRepository;
    private final TeamToDetailsConverter teamToDetailsConverter;
    private final WorkerService workerService;
    private final WorkerToDetailsConverter workerToDetailsConverter;

    public TeamService(TeamRepository teamRepository, TeamToDetailsConverter teamToDetailsConverter,
                       WorkerService workerService, WorkerToDetailsConverter workerToDetailsConverter) {
        this.teamRepository = teamRepository;
        this.teamToDetailsConverter = teamToDetailsConverter;
        this.workerService = workerService;
        this.workerToDetailsConverter = workerToDetailsConverter;
    }

    public Team getTeam(long teamId) {
        return teamRepository.findById(teamId).orElseThrow(() -> teamNotFound(teamId));
    }

    public TeamDetailsResponse getTeamDetails(long teamId) {
        Team team = getTeam(teamId);
        TeamDetailsResponse teamDetails = teamToDetailsConverter.convert(team);
        LOG.info("Got team {id: {}} successfully", teamId);
        return teamDetails;
    }

    public List<TeamDetailsResponse> findTeams(SearchTeamsQueryRequest query) {
        List<TeamDetailsResponse> teams = teamRepository.findTeams(query.getPhrase()).stream()
                                                        .map(teamToDetailsConverter::convert)
                                                        .collect(Collectors.toList());
        LOG.info("Got teams {size: {}}", teams.size());
        return teams;
    }

    public List<WorkerDetailsResponse> getTeamWorkers(long teamId) {
        Team team = getTeam(teamId);
        List<WorkerDetailsResponse> workers = team.getWorkers().stream()
                .map(workerToDetailsConverter::convert).collect(Collectors.toList());
        LOG.info("Got teams {size: {}}", workers.size());
        return workers;
    }

    @Transactional
    public void addWorkerToTeam(long workerId, long teamId) {
        Team team = getTeam(teamId);
        Worker worker = workerService.getWorker(workerId);
        if (worker.hasTeam()) {
            throw workerAlreadyHasTeam(workerId);
        }
        team.addWorker(worker);
        LOG.info("Worker {workerId: {}} added to Team {teamId: {}} successfully", workerId, teamId);
    }

    @Transactional
    public long addTeam(AddTeamRequest request) {
        Objects.requireNonNull(request);
        Team team = new Team(request.getName(), request.getDescription());
        teamRepository.save(team);
        LOG.info("Team {teamId: {}} added successfully", team.getId());
        return team.getId();
    }

    @Transactional
    public void updateTeam(long teamId, UpdateTeamRequest request) {
        Objects.requireNonNull(request);
        Team team = getTeam(teamId);
        team.setName(request.getName());
        team.setDescription(request.getDescription());
        LOG.info("Team {id: {}} updated successfully", teamId);
    }

    @Transactional
    public void deleteTeam(long teamId) {
        Team team = getTeam(teamId);
        team.removeWorkers();
        teamRepository.delete(team);
        LOG.info("Team {id: {}} deleted successfully", teamId);
    }
}
