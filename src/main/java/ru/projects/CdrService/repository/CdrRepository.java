package ru.projects.CdrService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.projects.CdrService.model.Cdr;

import java.util.List;

public interface CdrRepository extends JpaRepository<Cdr, Long> {

    @Query("SELECT c FROM Cdr c WHERE c.callerNumber = :msisdn OR c.receiverNumber = :msisdn")
    List<Cdr> findAllByCallerNumberOrReceiverNumber(@Param("msisdn") String msisdn);

    @Query(value = "SELECT * FROM cdr c WHERE (c.caller_number = :msisdn OR c.receiver_number = :msisdn) AND MONTH(c.beginning_call) = :month",
            nativeQuery = true)
    List<Cdr> findAllByMonthAndCallerNumberOrReceiverNumber(@Param("msisdn") String msisdn, @Param("month") int month);

    @Query(value = "SELECT * FROM cdr c WHERE MONTH(c.beginning_call) = :month",
            nativeQuery = true)
    List<Cdr> findAllByMonth(@Param("month") int month);
}
