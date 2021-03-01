package com.nexusbrain.app.converter;

import com.nexusbrain.app.api.dto.WorkerDetails;
import com.nexusbrain.app.model.Team;
import com.nexusbrain.app.model.Worker;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WorkerToDetailsConverter implements Converter<Worker, WorkerDetails> {
    @Override
    public WorkerDetails convert(Worker worker) {
        Objects.requireNonNull(worker);
        WorkerDetails workerDetails = new WorkerDetails();
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
