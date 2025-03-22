package ru.projects.CdrService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.projects.CdrService.model.Cdr;
import ru.projects.CdrService.model.Subscriber;
import ru.projects.CdrService.repository.CdrRepository;
import ru.projects.CdrService.repository.SubscriberRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class SwitchService {
    @Autowired
    CdrRepository cdrRepository;
    @Autowired
    SubscriberRepository subscriberRepository;
    private Random random = new Random();

    public void generateCdrForOneYear() {
        LocalDateTime currentTime = LocalDateTime.now().minusYears(1);
        LocalDateTime endTime = LocalDateTime.now();
        while (currentTime.isBefore(endTime)) {
            if (random.nextBoolean()) {
                generateCdr(currentTime);
            }

            currentTime = currentTime.plusMinutes(random.nextLong(60));
        }
    }

    private void generateCdr(LocalDateTime currentTime) {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        Cdr cdr = new Cdr();

        int indexCallerNumber = random.nextInt(0, subscribers.size());
        int indexReceiverNumber;

        do {
            indexReceiverNumber = random.nextInt(0, subscribers.size());
        } while (indexReceiverNumber == indexCallerNumber);

        cdr.setCallType(random.nextBoolean() ? "01" : "02");
        cdr.setCallerNumber(subscribers.get(indexCallerNumber).getMsisdn());
        cdr.setReceiverNumber(subscribers.get(indexReceiverNumber).getMsisdn());
        cdr.setStartCall(currentTime);
        cdr.setEndCall(currentTime.plusSeconds(random.nextLong(0,60*60)));

        cdrRepository.save(cdr);
    }
}
