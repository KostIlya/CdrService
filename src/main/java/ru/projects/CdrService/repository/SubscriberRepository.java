package ru.projects.CdrService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.projects.CdrService.model.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
}
