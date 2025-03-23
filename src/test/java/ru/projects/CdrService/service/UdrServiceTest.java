package ru.projects.CdrService.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.projects.CdrService.model.Cdr;
import ru.projects.CdrService.model.Udr;
import ru.projects.CdrService.repository.CdrRepository;
import ru.projects.CdrService.repository.SubscriberRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class UdrServiceTest {
    @Mock
    private CdrRepository cdrRepository;

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private UdrService udrService;

    private String msisdn;
    private int month;
    private List<Cdr> cdrList;
    private Udr udr;

    @BeforeEach
    void setUp() {
        msisdn = "79821206840";
        month = 2;

        cdrList = List.of(
                new Cdr(1L, "01", msisdn, "79536214258", LocalDateTime.now().minusMinutes(2), LocalDateTime.now()),
                new Cdr(2L, "02", "79536521258", msisdn, LocalDateTime.now().minusMinutes(3), LocalDateTime.now()),
                new Cdr(3L, "01", msisdn, "79536521258", LocalDateTime.now().minusMinutes(3), LocalDateTime.now())
        );

        udr = new Udr(msisdn, new Udr.CallDuration(), new Udr.CallDuration());
    }


    @Test
    void handleGetUdrByOneSubscriber_ReturnUdr() {
        // given
        lenient().doReturn(cdrList).when(this.cdrRepository).findAllByCallerNumberOrReceiverNumber(msisdn);
        lenient().doReturn(true).when(this.subscriberRepository).existsByMsisdn(msisdn);

        // when
        var response = this.udrService.getUdrByOneSubscriber(msisdn, month);

        // then
        assertNotNull(response);
        assertEquals(udr, response);
    }
}
