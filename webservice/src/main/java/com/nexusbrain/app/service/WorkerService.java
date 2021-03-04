package com.nexusbrain.app.service;

import com.nexusbrain.app.api.dto.request.AddWorkerRequest;
import com.nexusbrain.app.api.dto.request.SearchWorkersQueryRequest;
import com.nexusbrain.app.api.dto.request.UpdateWorkerRequest;
import com.nexusbrain.app.api.dto.response.WorkerDetailsResponse;
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

    public Worker getWorker(long workerId) {
        return workerRepository.findById(workerId).orElseThrow(() -> workerNotFound(workerId));
    }

    public WorkerDetailsResponse getWorkerDetails(long workerId) {
        Worker worker = getWorker(workerId);
        WorkerDetailsResponse workerDetails = workerToDetailsConverter.convert(worker);
        LOG.info("Got worker {id: {}} successfully", workerId);
        return workerDetails;
    }

    public Page<WorkerDetailsResponse> findWorkers(Pageable pageable, SearchWorkersQueryRequest query) {
        Page<WorkerDetailsResponse> workers = workerRepository.findWorkers(pageable, query.getPhrase()).map(workerToDetailsConverter::convert);
        LOG.info("Got workers {size: {}}", workers.getNumberOfElements());
        return workers;
    }

    @Transactional
    public long addWorker(AddWorkerRequest request) {
        Objects.requireNonNull(request);
        Worker worker = new Worker(request.getFullName(), request.getEmail());
        workerRepository.save(worker);
        LOG.info("Worker {email: {}, id: {}} added successfully", request.getEmail(), worker.getId());
        return worker.getId();
    }

    @Transactional
    public void updateWorker(long workerId, UpdateWorkerRequest request) {
        Objects.requireNonNull(request);
        Worker worker = getWorker(workerId);
        worker.setFullName(request.getFullName());
        worker.setEmail(request.getEmail());
        LOG.info("Worker {id: {}} updated successfully", workerId);
    }

    @Transactional
    public void deleteWorker(long workerId) {
        Worker worker = getWorker(workerId);
        workerRepository.delete(worker);
        LOG.info("Worker {id: {}} deleted successfully", workerId);
    }
}
