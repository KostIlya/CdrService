package ru.projects.CdrService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.projects.CdrService.service.CdrReportService;
import java.util.UUID;

/**
 * Контроллер для генерации и сохранения CDR-отчетов
 * @see CdrReportService
 */
@RestController
@RequestMapping(path = "/api/v1/cdr-report-service")
public class CdrReportController {
    @Autowired
    private CdrReportService cdrReportService;

    /**
     * Генерирует и сохраняет CDR-отчет для конкретного пользователя и за определенный период
     * @param request Данные запроса, содержащие номер абонента(msisdn), начальную дату(startDate),
     *                конечную дату(endDate)
     * @return Возвращает объект {@link ResponseEntity} с текстовым сообщением:
     *      - {@code 200 OK} с сообщением "The report was created successfully. UUID: " + uuid, где uuid - уникальный
     *      идентификатор отчета
     *      - {@code 400 Bad Request} с сообщением "ERROR: All parameters are required.", если в запросе отствуют
     *      необходимые параметры;
     *      - {@code 400 Bad Request} с сообщением об ошибке, если произошла ошибка при создании отчета.
     * @see CdrReportController.ReportRequestBody
     */
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
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERROR: " + e.getMessage());
        }
    }

    /**
     * Внутренний статический класс содержащий данные для запроса о создании CDR-отчета:
     *      - msisdn - номер абонента
     *      - startDate - дата начала периода
     *      - endDate - дата конца периода
     * @see CdrReportController#generateReportBySubscriberForCertainPeriod(ReportRequestBody)
     */
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
