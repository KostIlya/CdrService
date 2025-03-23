package ru.projects.CdrService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.projects.CdrService.model.Cdr;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с CDR-записями
 * @see Cdr
 */
public interface CdrRepository extends JpaRepository<Cdr, Long> {

    /**
     * Возвращает список CDR-записей, в которых номер {@param msisdn} соответствует номеру абонента, инициирующему
     * звонок, или номеру абонента, принимающему звонок
     * @param msisdn Номер абонента
     * @return Список CDR-записей, содержащим указанный номер абонента
     */
    @Query(value = "SELECT * FROM Cdr c WHERE c.caller_number = :msisdn OR c.receiver_number = :msisdn",
            nativeQuery = true)
    List<Cdr> findAllByCallerNumberOrReceiverNumber(@Param("msisdn") String msisdn);

    /**
     * Возвращает список CDR-записей за определенный месяц, в которых номер {@param msisdn} соответствует номеру абонента, инициирующему
     * звонок, или номеру абонента, принимающему звонок
     * @param msisdn Номер абонента
     * @param month Месяц, за который запрошена CDR-записи
     * @return Список CDR-записей за указанный месяц, содержащим указанный номер абонента
     */
    @Query(value = "SELECT * FROM cdr c WHERE (c.caller_number = :msisdn OR c.receiver_number = :msisdn) AND MONTH(c.start_call) = :month",
            nativeQuery = true)
    List<Cdr> findAllByMonthAndCallerNumberOrReceiverNumber(@Param("msisdn") String msisdn, @Param("month") int month);

    /**
     * Возвращает список CDR-записей за определенный месяц
     * @param month Месяц, за который запрошена CDR-записи
     * @return Список CDR-записей за указанный месяц
     */
    @Query(value = "SELECT * FROM cdr c WHERE MONTH(c.beginning_call) = :month",
            nativeQuery = true)
    List<Cdr> findAllByMonth(@Param("month") int month);

    /**
     * Возвращает список CDR-записей за определенный период, в которых номер {@param msisdn} соответствует номеру абонента, инициирующему
     * звонок, или номеру абонента, принимающему звонок
     * @param msisdn Номер абонента
     * @param startDate Дата начала запрашиваемого периода
     * @param endDate Дата конца запрашиваемого периода
     * @return Список CDR-записей за указанный период, содержащим указанный номер абонента
     */
    @Query(value = "SELECT * FROM cdr c WHERE (c.caller_number = :msisdn OR c.receiver_number = :msisdn) AND c.start_call BETWEEN :startDate AND :endDate",
            nativeQuery = true)
    List<Cdr> findAllByCertainPeriodAndCallerNumberOrReceiverNumber(@Param("msisdn") String msisdn, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
