package ru.projects.CdrService.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import ru.projects.CdrService.service.CdrReportService;
import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class CdrReportControllerTest {
    @Mock
    CdrReportService cdrReportService;

    @InjectMocks
    CdrReportController cdrReportController;

    @Test
    void handleGenerateReportBySubscriberForCertainPeriod_ReturnOk() {
        // given
        CdrReportController.ReportRequestBody reportRequestBody = new CdrReportController.ReportRequestBody("79533663258", "2025-01-11", "2025-01-22");
        try {
            doReturn(UUID.fromString("e118cb90-1cc7-4931-ad1c-f733da5b7558")).when(this.cdrReportService).generateCdrReport("79533663258", "2025-01-11", "2025-01-22");
        } catch (IOException ignored) { }

        // when
        var response = this.cdrReportController.generateReportBySubscriberForCertainPeriod(reportRequestBody);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), "The report was created successfully. UUID: e118cb90-1cc7-4931-ad1c-f733da5b7558");
    }

    @Test
    void handleGenerateReportBySubscriberForCertainPeriod_ReturnErrorAllParametersRequired() {
        // given
        CdrReportController.ReportRequestBody reportRequestBody = new CdrReportController.ReportRequestBody(null, "2025-01-11", "2025-01-22");

        // when
        var response = this.cdrReportController.generateReportBySubscriberForCertainPeriod(reportRequestBody);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(response.getBody(), "ERROR: All parameters are required.");
    }

    @Test
    void handleGenerateReportBySubscriberForCertainPeriod_ReturnErrorInvalidDateFormat() {
        // given
        CdrReportController.ReportRequestBody reportRequestBody = new CdrReportController.ReportRequestBody("79533663258", "2025-01", "2025-01-22");
        try {
            doThrow(new IllegalArgumentException("Invalid input parameters.")).when(this.cdrReportService).generateCdrReport("79533663258", "2025-01", "2025-01-22");
        } catch (IOException ignored) { }

        // when
        var response = this.cdrReportController.generateReportBySubscriberForCertainPeriod(reportRequestBody);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ERROR: Invalid input parameters.", response.getBody());
    }

    @Test
    void handleGenerateReportBySubscriberForCertainPeriod_ReturnErrorNotExistsSubscriber() {
        // given
        CdrReportController.ReportRequestBody reportRequestBody = new CdrReportController.ReportRequestBody("79533663521", "2025-01-11", "2025-01-22");
        try {
            doThrow(new IllegalArgumentException("The subscriber does not exist")).when(this.cdrReportService).generateCdrReport("79533663521", "2025-01-11", "2025-01-22");
        } catch (IOException ignored) { }

        // when
        var response = this.cdrReportController.generateReportBySubscriberForCertainPeriod(reportRequestBody);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ERROR: The subscriber does not exist", response.getBody());
    }

    @Test
    void handleGenerateReportBySubscriberForCertainPeriod_ThrowIOException() {
        // given
        CdrReportController.ReportRequestBody reportRequestBody = new CdrReportController.ReportRequestBody("79533663258", "2025-01", "2025-01-22");
        try {
            doThrow(new IOException("Writing a report.")).when(this.cdrReportService).generateCdrReport("79533663258", "2025-01", "2025-01-22");
        } catch (IOException ignored) { }

        // when
        var response = this.cdrReportController.generateReportBySubscriberForCertainPeriod(reportRequestBody);

        // then
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ERROR: Writing a report.", response.getBody());
    }
}
