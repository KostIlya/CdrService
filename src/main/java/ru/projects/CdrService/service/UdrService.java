package ru.projects.CdrService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.projects.CdrService.model.Cdr;
import ru.projects.CdrService.model.Subscriber;
import ru.projects.CdrService.model.Udr;
import ru.projects.CdrService.repository.CdrRepository;
import ru.projects.CdrService.repository.SubscriberRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class UdrService {
    @Autowired
    private CdrRepository cdrRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    public Udr getUdrByOneSubscriber(String msisdn, int month) {
        if (!isExistMsisdn(msisdn)) {
            return null;
        }

        Udr udr = new Udr();
        List<Cdr> cdrRecords;
        if (month < 1 || month > 12) {
            cdrRecords = cdrRepository.findAllByCallerNumberOrReceiverNumber(msisdn);
        } else {
            cdrRecords = cdrRepository.findAllByMonthAndCallerNumberOrReceiverNumber(msisdn, month);
        }

        udr.setMsisdn(msisdn);
        udr.setIncomingCall(new Udr.CallDuration());
        udr.setOutcomingCall(new Udr.CallDuration());
        for (var cdr: cdrRecords) {
            int secondCalDuration = cdr.getEndCall().getSecond() - cdr.getStartCall().getSecond();

            if (cdr.getCallerNumber().equals(msisdn)) {
                udr.getIncomingCall().setTotalTime(udr.getIncomingCall().getTotalTime().plusSeconds(secondCalDuration));
            } else {
                udr.getOutcomingCall().setTotalTime(udr.getOutcomingCall().getTotalTime().plusSeconds(secondCalDuration));
            }
        }

        return udr;
    }

    private boolean isExistMsisdn(String msisdn) {
        List<String> subscribersMsisdn = subscriberRepository.findAll().stream().map(Subscriber::getMsisdn).toList();

        return subscribersMsisdn.contains(msisdn);
    }

    public List<Udr> getUdrByAllSubscribers(int month) {
        if (month < 1 || month > 12) {
            return null;
        }

        List<Udr> udrRecords = new ArrayList<>();
        List<Cdr> cdrRecords = cdrRepository.findAllByMonth(month);
        List<String> msisdnList = subscriberRepository.findAll().stream()
                .map(Subscriber::getMsisdn)
                .toList();

        for (var msisdn: msisdnList) {
            udrRecords.add(getUdrByOneSubscriber(msisdn, month));
        }

        return udrRecords;
    }
}
