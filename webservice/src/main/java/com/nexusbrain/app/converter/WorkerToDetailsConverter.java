package com.nexusbrain.app.converter;

import com.nexusbrain.app.api.dto.response.WorkerDetailsResponse;
import com.nexusbrain.app.model.Team;
import com.nexusbrain.app.model.Worker;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WorkerToDetailsConverter implements Converter<Worker, WorkerDetailsResponse> {
    @Override
    public WorkerDetailsResponse convert(Worker worker) {
        Objects.requireNonNull(worker);
        WorkerDetailsResponse workerDetails = new WorkerDetailsResponse();
        workerDetails.setId(worker.getId());
        workerDetails.setFullName(worker.getFullName());
        workerDetails.setEmail(worker.getEmail());
        if (worker.hasTeam()) {
            Team team = worker.getTeam();
            workerDetails.setTeamId(team.getId());
            workerDetails.setTeamName(team.getName());
        }
        return workerDetails;
    }
}
