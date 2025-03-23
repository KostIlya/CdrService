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

/**
 * Имитирует работы коммутатора. Генерирует случайным образом и сохраняет в репозиторий CDR-записи
 * @see CdrRepository
 * @see SubscriberRepository
 */
@Service
public class SwitchService {
    @Autowired
    CdrRepository cdrRepository;
    @Autowired
    SubscriberRepository subscriberRepository;
    private Random random = new Random();

    /**
     * Генерирует CDR-записи случайным образом за один год
     */
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

    /**
     * Генерирует CDR-запись с двумя случайными абонентами с началом даты и времени звонка в {@param currentTime} и
     * сохранят CDR-запись в репозитории
     * @param currentTime Дата и время начала звонка
     */
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
