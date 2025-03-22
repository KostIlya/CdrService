package ru.projects.CdrService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.projects.CdrService.model.Cdr;
import ru.projects.CdrService.model.Subscriber;
import ru.projects.CdrService.repository.CdrRepository;
import ru.projects.CdrService.repository.SubscriberRepository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class CdrReportService {
    @Autowired
    private CdrRepository cdrRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;
    private final String DATE_FORMAT = "yyyy-MM-dd";

    public UUID generateCdrReport(String msisdn, String startDate, String endDate) throws IOException {
        if (!isDate(startDate) || !isDate(endDate)) {
            throw new IOException("Invalid input parameters.");
        }

        if (!isExistMsisdn(msisdn)) {
            throw new IOException("The subscriber does not exist");
        }

        String DIR_REPORTS = "reports";
        UUID uuid = UUID.randomUUID();
        String fileName = DIR_REPORTS + "\\" + msisdn + "_" + uuid + ".csv";
        Path filePath = Paths.get(fileName);

        List<Cdr> cdrList = getCdrList(msisdn, startDate, endDate);

        try (BufferedWriter report = new BufferedWriter(new FileWriter(fileName))) {
            if (!cdrList.isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                for (var cdr : cdrList) {
                    report.write(cdr.getCallType() + "," + cdr.getCallerNumber() + "," + cdr.getReceiverNumber() + ","
                            + cdr.getStartCall().format(formatter) + "," + cdr.getEndCall().format(formatter));
                    report.newLine();
                }
            } else {
                report.write("There are no cdr records for the specified subscriber for the requested period.");
            }
        } catch (IOException e) {
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            }
            throw new IOException("Writing a report.");
        }

        return uuid;
    }

    private boolean isExistMsisdn(String msisdn) {
        return subscriberRepository.findAll().stream()
                .map(Subscriber::getMsisdn)
                .toList()
                .contains(msisdn);
    }

    private boolean isDate(String date) {
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private LocalDateTime toLocalDateTime(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
        return localDate.atStartOfDay();
    }

    private List<Cdr> getCdrList(String msisdn, String startDate, String endDate) throws IOException {
        LocalDateTime ldtStartDate = toLocalDateTime(startDate);
        LocalDateTime ldtEndDate = toLocalDateTime(endDate);

        if (ldtStartDate.isAfter(ldtEndDate)) {
            throw new IOException("The start date is later than the end date.");
        }

        List<Cdr> cdrList = cdrRepository.findAllByCertainPeriodAndCallerNumberOrReceiverNumber(msisdn, ldtStartDate, ldtEndDate);

        return cdrList;
    }
}
