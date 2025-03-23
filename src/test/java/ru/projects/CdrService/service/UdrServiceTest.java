package ru.projects.CdrService.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.projects.CdrService.model.Cdr;
import ru.projects.CdrService.model.Subscriber;
import ru.projects.CdrService.model.Udr;
import ru.projects.CdrService.repository.CdrRepository;
import ru.projects.CdrService.repository.SubscriberRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
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
    private List<Subscriber> subscriberList;
    private Udr udr;
    private List<Udr> udrList;

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

        udrList = List.of(udr);

        subscriberList = List.of(
                new Subscriber(1L, msisdn)
        );
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

    @Test
    void handleGetUdrByOneSubscriber_ReturnNull() {
        // given
        lenient().doReturn(false).when(this.subscriberRepository).existsByMsisdn(msisdn);

        // when
        var response = this.udrService.getUdrByOneSubscriber(msisdn, month);

        // then
        assertNull(response);
    }

    @Test
    void handleGetUdrByAllSubscribers_ReturnListUdr() {
        // given
        lenient().doReturn(cdrList).when(this.cdrRepository).findAllByCallerNumberOrReceiverNumber(msisdn);
        lenient().doReturn(true).when(this.subscriberRepository).existsByMsisdn(msisdn);
        //doReturn(udr).when(udrService).getUdrByOneSubscriber(msisdn, month);
        lenient().doReturn(subscriberList).when(this.subscriberRepository).findAll();
        // when
        var response = this.udrService.getUdrByAllSubscribers(month);

        // then
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(udrList, response);
    }
}
