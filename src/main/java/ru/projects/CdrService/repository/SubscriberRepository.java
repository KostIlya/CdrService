package ru.projects.CdrService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.projects.CdrService.model.Subscriber;

/**
 * Репозиторий для работы с абонентами
 * @see Subscriber
 */
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    /**
     * Проверяет существует ли абонент с номером {@param msisdn} в репозитории
     * @param msisdn Номер абонента
     * @return True, если абонент с номером {@param msisdn} существует в репозитории. False, в ином случае.
     */
    boolean existsByMsisdn(String msisdn);
}
