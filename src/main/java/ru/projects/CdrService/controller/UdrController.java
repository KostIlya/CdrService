package ru.projects.CdrService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.projects.CdrService.model.Udr;
import ru.projects.CdrService.service.CdrReportService;
import ru.projects.CdrService.service.UdrService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/udr-service")
public class UdrController {

    @Autowired
    private UdrService udrService;

    @Autowired
    private CdrReportService cdrReportService;

    @GetMapping(path = "/udr/{msisdn}/{month}")
    public Udr getUdrByOneSubscriberForCertainMonthOrYear(@PathVariable String msisdn, @PathVariable Integer month) {
        return udrService.getUdrByOneSubscriber(msisdn, month);
    }

    @GetMapping(path = "/all-udr/{month}")
    public List<Udr> getUdrByAllSubscribersForCertainMonth(@PathVariable Integer month) {
        return udrService.getUdrByAllSubscribers(month);
    }

//    @GetMapping(path = "/report-cdr/{msisdn}")
//    public String generateReportBySubscriberForCertainPeriod(@PathVariable String msisdn) {
//        try {
//            return cdrReportService.generateCdrReport(msisdn);
//        } catch (Exception e) {
//            return "Error";
//        }
//    }
}
