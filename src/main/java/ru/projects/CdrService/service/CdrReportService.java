//package ru.projects.CdrService.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ru.projects.CdrService.model.Cdr;
//import ru.projects.CdrService.repository.CdrRepository;
//
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.FileWriter;
//import java.nio.file.Files;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class CdrReportService {
//    @Autowired
//    private CdrRepository repository;
//
//    public String generateCdrReport(String msisdn) throws Exception{
//        UUID uuid = UUID.randomUUID();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
//        String filePath = "reports\\" + msisdn + "_" + uuid + ".csv";
//        List<Cdr> cdrList = repository.findAllByCallerNumberOrReceiverNumber(msisdn);
//        try (BufferedWriter report = new BufferedWriter(new FileWriter(filePath))) {
//            for (var cdr: cdrList) {
//                report.write(cdr.getCallType() + "," + cdr.getCallerNumber() + "," + cdr.getReceiverNumber() + ","
//                        + cdr.getBeginningCall().format(formatter) + "," + cdr.getTerminationCall().format(formatter));
//                report.newLine();
//            }
//
//            return "nice";
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new Exception();
//        }
//    }
//}
