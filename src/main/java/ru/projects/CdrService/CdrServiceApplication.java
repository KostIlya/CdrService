package ru.projects.CdrService;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.projects.CdrService.service.SwitchService;

/**
 * @author Ilya Kostylev
 * @version 1.1
 * @since 1.0
 * Основной класс проекта CdrService
 * Этот класс являет точкой входа в Spring Boot приложение. При запуске приложения генерируются CDR-записи за один год
 * с использованием сервиса {@link SwitchService}
 * @see SwitchService
 */
@SpringBootApplication
public class CdrServiceApplication {

	@Autowired
	SwitchService switchService;

	/**
	 * Точка входа в приложение
	 * @param args Аргументы командной строки
	 */
	public static void main(String[] args) {
		SpringApplication.run(CdrServiceApplication.class, args);
	}

	/**
	 * Метод, выполняемый после инициализации контекста Spring
	 * Генерирует CDR-записи за один год с использованием сервиса {@link SwitchService}
	 */
	@PostConstruct
	public void init() {
		switchService.generateCdrForOneYear();
	}
}
