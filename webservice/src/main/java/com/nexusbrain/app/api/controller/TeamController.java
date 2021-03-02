package com.nexusbrain.app.api.controller;

import com.nexusbrain.app.api.dto.request.AddTeamRequest;
import com.nexusbrain.app.api.dto.request.SearchTeamsQueryRequest;
import com.nexusbrain.app.api.dto.request.UpdateTeamRequest;
import com.nexusbrain.app.api.dto.response.ResourceCreatedResponse;
import com.nexusbrain.app.api.dto.response.TeamDetailsResponse;
import com.nexusbrain.app.api.dto.response.WorkerDetailsResponse;
import com.nexusbrain.app.service.TeamService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/nb/v1.0/teams")
public class TeamController {

    private static final Logger LOG = LoggerFactory.getLogger(TeamController.class);

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping("/add")
    public ResponseEntity<ResourceCreatedResponse<Long>> addTeam(@Valid @RequestBody AddTeamRequest request) {
        LOG.info("Received addTeam request");
        return new ResponseEntity<>(new ResourceCreatedResponse<>(teamService.addTeam(request)), HttpStatus.CREATED);
    }

    @PutMapping("/{teamId}/update")
    public ResponseEntity<Void> updateTeam(@PathVariable long teamId, @Valid @RequestBody UpdateTeamRequest request) {
        LOG.info("Received updateTeam request");
        teamService.updateTeam(teamId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{teamId}/get")
    public ResponseEntity<TeamDetailsResponse> getTeam(@PathVariable long teamId) {
        LOG.info("Received getTeam request");
        return new ResponseEntity<>(teamService.getTeamDetails(teamId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TeamDetailsResponse>> searchTeams(@Valid SearchTeamsQueryRequest query) {
        LOG.info("Received searchTeams request");
        return new ResponseEntity<>(teamService.findTeams(query), HttpStatus.OK);
    }

    @GetMapping("/{teamId}/workers")
    public ResponseEntity<List<WorkerDetailsResponse>> getTeamWorkers(@PathVariable long teamId) {
        LOG.info("Received getTeamWorkers request");
        return new ResponseEntity<>(teamService.getTeamWorkers(teamId), HttpStatus.OK);
    }

    @PutMapping("/{teamId}/workers/add")
    public ResponseEntity<Void> addWorkerToTeam(@PathVariable long teamId, @RequestParam long workerId) {
        LOG.info("Received addWorkerToTeam request");
        teamService.addWorkerToTeam(workerId, teamId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{teamId}/delete")
    public ResponseEntity<Void> deleteTeam(@PathVariable long teamId) {
        LOG.info("Received deleteTeam request");
        teamService.deleteTeam(teamId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
