package com.nexusbrain.app;

import com.nexusbrain.app.model.Worker;
import com.nexusbrain.app.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
class Test {
	@Autowired
	private WorkerRepository workerRepository;

	@PostConstruct
	public void init() {
		Pageable pageable = PageRequest.of(0, 20);
		Page<Worker> test = workerRepository.findWorkers(null, null, null);
		System.out.println("done");
	}
}

@SpringBootApplication
public class NexusbrainApplication {

	public static void main(String[] args) {
		SpringApplication.run(NexusbrainApplication.class, args);
	}
}
