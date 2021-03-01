package com.nexusbrain.app.service;

import com.nexusbrain.app.api.dto.AddWorkerRequest;
import com.nexusbrain.app.api.dto.SearchWorkersQuery;
import com.nexusbrain.app.api.dto.UpdateWorkerRequest;
import com.nexusbrain.app.api.dto.WorkerDetails;
import com.nexusbrain.app.converter.WorkerToDetailsConverter;
import com.nexusbrain.app.model.Worker;
import com.nexusbrain.app.repository.WorkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static com.nexusbrain.app.exception.ApiException.workerNotFound;

@Service
public class WorkerService {

    private static final Logger LOG = LoggerFactory.getLogger(WorkerService.class);

    private final WorkerRepository workerRepository;
    private final WorkerToDetailsConverter workerToDetailsConverter;

    public WorkerService(WorkerRepository workerRepository, WorkerToDetailsConverter workerToDetailsConverter) {
        this.workerRepository = workerRepository;
        this.workerToDetailsConverter = workerToDetailsConverter;
    }

    public WorkerDetails getWorker(long workerId) {
        Optional<Worker> maybeWorker = workerRepository.findById(workerId);
        Worker worker = maybeWorker.orElseThrow(() -> workerNotFound(workerId));
        WorkerDetails workerDetails = workerToDetailsConverter.convert(worker);
        LOG.info("Got worker {id: {}} successfully", workerId);
        return workerDetails;
    }

    public Page<WorkerDetails> findWorkers(Pageable pageable, SearchWorkersQuery query) {
        Page<WorkerDetails> workers = workerRepository.findWorkers(pageable, query.getPhrase(), query.getTeamId())
                                                                             .map(workerToDetailsConverter::convert);
        LOG.info("Got workers {size: {}}", workers.getNumberOfElements());
        return workers;
    }

    @Transactional
    public void addWorker(AddWorkerRequest request) {
        Objects.requireNonNull(request);
        Worker worker = new Worker(request.getFullName(), request.getEmail());
        workerRepository.save(worker);
        LOG.info("Worker {email: {}, id: {}} added successfully", request.getEmail(), worker.getId());
    }

    @Transactional
    public void updateWorker(long workerId, UpdateWorkerRequest request) {
        Objects.requireNonNull(request);
        Optional<Worker> maybeWorker = workerRepository.findById(workerId);
        Worker worker = maybeWorker.orElseThrow(() -> workerNotFound(workerId));
        worker.setFullName(request.getFullName());
        worker.setEmail(request.getEmail());
        LOG.info("Worker {id: {}} updated successfully", workerId);
    }

    @Transactional
    public void deleteWorker(long workerId) {
        Optional<Worker> maybeWorker = workerRepository.findById(workerId);
        Worker worker = maybeWorker.orElseThrow(() -> workerNotFound(workerId));
        workerRepository.delete(worker);
        LOG.info("Worker {id: {}} deleted successfully", workerId);
    }
}
