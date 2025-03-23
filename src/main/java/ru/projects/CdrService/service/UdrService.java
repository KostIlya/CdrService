package ru.projects.CdrService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.projects.CdrService.model.Cdr;
import ru.projects.CdrService.model.Subscriber;
import ru.projects.CdrService.model.Udr;
import ru.projects.CdrService.repository.CdrRepository;
import ru.projects.CdrService.repository.SubscriberRepository;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для работы с UDR-записями абонентов
 * @see CdrRepository
 * @see SubscriberRepository
 */
@Service
public class UdrService {
    @Autowired
    private CdrRepository cdrRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    /**
     * Возвращает UDR-запись за запрошенный месяц или за весь год
     * @param msisdn Номер абонента
     * @param month Месяц, за который необходимо получить UDR-запись(1-12). Если указано число меньше 1 или больше 12,
     *              то UDR-запись составляется за весь год
     * @return Возвращает UDR-запись за запрошенный месяц, или за весь год, или null, если абонента с номером {@param msisdn}
     *      не существует
     * @see Udr
     */
    public Udr getUdrByOneSubscriber(String msisdn, int month) {
        if (!subscriberRepository.existsByMsisdn(msisdn)) {
            return null;
        }
        Udr udr = new Udr();
        List<Cdr> cdrRecords;
        if (month >= 1 && month <= 12) {
            cdrRecords = cdrRepository.findAllByMonthAndCallerNumberOrReceiverNumber(msisdn, month);
        } else {
            cdrRecords = cdrRepository.findAllByCallerNumberOrReceiverNumber(msisdn);
        }

        udr.setMsisdn(msisdn);
        udr.setIncomingCall(new Udr.CallDuration());
        udr.setOutgoingCall(new Udr.CallDuration());
        for (var cdr: cdrRecords) {

            Duration duration = Duration.between(cdr.getStartCall(), cdr.getEndCall());

            if (cdr.getCallerNumber().equals(msisdn)) {
                udr.getIncomingCall().setTotalTime(udr.getIncomingCall().getTotalTime().plus(duration));
            } else {
                udr.getOutgoingCall().setTotalTime(udr.getOutgoingCall().getTotalTime().plus(duration));
            }
        }

        return udr;
    }

    /**
     * Возвращает список UDR записей для всех абонентов за указанный месяц
     * @param month Месяц, за который необходимо получить UDR-записи(1-12).
     * @return Список UDR-записей за запрошенный месяц
     * @see Udr
     */
    public List<Udr> getUdrByAllSubscribers(int month) {
        List<Udr> udrRecords = new ArrayList<>();
        List<String> msisdnList = subscriberRepository.findAll().stream()
                .map(Subscriber::getMsisdn)
                .toList();

        for (var msisdn: msisdnList) {
            udrRecords.add(getUdrByOneSubscriber(msisdn, month));
        }

        return udrRecords;
    }
}
