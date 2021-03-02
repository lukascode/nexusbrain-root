package com.nexusbrain.app.api.controller;

import com.nexusbrain.app.api.dto.request.AddWorkerRequest;
import com.nexusbrain.app.api.dto.request.SearchWorkersQueryRequest;
import com.nexusbrain.app.api.dto.request.UpdateWorkerRequest;
import com.nexusbrain.app.api.dto.response.ResourceCreatedResponse;
import com.nexusbrain.app.api.dto.response.WorkerDetailsResponse;
import com.nexusbrain.app.service.WorkerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequestMapping("/nb/v1.0/workers")
public class WorkerController {

    private static final Logger LOG = LoggerFactory.getLogger(WorkerController.class);

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping("/add")
    public ResponseEntity<ResourceCreatedResponse<Long>> addWorker(@Valid @RequestBody AddWorkerRequest request) {
        LOG.info("Received addWorker request");
        return new ResponseEntity<>(new ResourceCreatedResponse<>(workerService.addWorker(request)), HttpStatus.CREATED);
    }

    @PutMapping("/{workerId}/update")
    public ResponseEntity<Void> updateWorker(@PathVariable long workerId, @Valid @RequestBody UpdateWorkerRequest request) {
        LOG.info("Received updateWorker request");
        workerService.updateWorker(workerId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{workerId}/get")
    public ResponseEntity<WorkerDetailsResponse> getWorker(@PathVariable long workerId) {
        LOG.info("Received getWorkerDetails request");
        return new ResponseEntity<>(workerService.getWorkerDetails(workerId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<WorkerDetailsResponse>> searchWorkers(Pageable pageable, @Valid SearchWorkersQueryRequest query) {
        LOG.info("Received searchWorkers request");
        return new ResponseEntity<>(workerService.findWorkers(pageable, query), HttpStatus.OK);
    }

    @DeleteMapping("/{workerId}/delete")
    public ResponseEntity<Void> deleteWorker(@PathVariable long workerId) {
        LOG.info("Received deleteWorker request");
        workerService.deleteWorker(workerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
