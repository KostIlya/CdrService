package ru.projects.CdrService;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.projects.CdrService.service.SwitchService;

@SpringBootApplication
public class CdrServiceApplication {

	@Autowired
	SwitchService switchService;

	public static void main(String[] args) {
		SpringApplication.run(CdrServiceApplication.class, args);
	}

	@PostConstruct
	public void init() {
		switchService.generateCdrForOneYear();
	}
}
