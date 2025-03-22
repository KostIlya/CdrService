package ru.projects.CdrService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.projects.CdrService.service.CdrReportService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/cdr-report-service")
public class CdrReportController {
    @Autowired
    private CdrReportService cdrReportService;

    @PostMapping(path = "/report-cdr")
    public ResponseEntity<String> generateReportBySubscriberForCertainPeriod(@RequestBody ReportRequestBody request) {
        if (request == null || request.getMsisdn() == null
                || request.getStartDate() == null
                || request.getEndDate() == null) {
            return ResponseEntity.badRequest().body("ERROR: All parameters are required.");
        }

        try {
            UUID uuid = cdrReportService.generateCdrReport(request.getMsisdn(), request.getStartDate(), request.getEndDate());
            return ResponseEntity.ok("The report was created successfully. UUID: " + uuid);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }

    public static class ReportRequestBody {
        private final String msisdn;
        private final String startDate;
        private final String endDate;

        public ReportRequestBody(String msisdn, String startDate, String endDate) {
            this.msisdn = msisdn;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public String getMsisdn() {
            return msisdn;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }
    }
}
