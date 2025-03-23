package ru.projects.CdrService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.projects.CdrService.controller.UdrController;
import ru.projects.CdrService.model.Udr;
import ru.projects.CdrService.service.UdrService;
import ru.projects.CdrService.util.SpecialTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UdrControllerTest {
    @Mock
    UdrService udrService;

    @InjectMocks
    UdrController udrController;

//    private Udr udr;
    private Udr udr1;
    private Udr udr2;
    private List<Udr> udrList;

    @BeforeEach
    void setUp() {
        udr1 = new Udr("79821206840", new Udr.CallDuration(SpecialTime.of(0, 20, 0)), new Udr.CallDuration(SpecialTime.of(0, 40, 0)));
        udr2 = new Udr("79533663258", new Udr.CallDuration(SpecialTime.of(0, 10, 10)), new Udr.CallDuration(SpecialTime.of(0, 20, 0)));
        udrList = List.of(udr1, udr2);
    }

    @Test
    void handleGetUdrByOneSubscriberForCertainMonthOrYear_ReturnNotNull() {
        // given
        doReturn(udr1).when(this.udrService).getUdrByOneSubscriber("79821206840", 2);

        // when
        var response = this.udrController.getUdrByOneSubscriberForCertainMonthOrYear("79821206840", 2);

        // then
        assertNotNull(response);
        assertEquals(udr1, response);
    }

    @Test
    void handleGetUdrByOneSubscriberForCertainMonthOrYear_ReturnNull() {
        // given
        doReturn(null).when(this.udrService).getUdrByOneSubscriber("79821206820", 2);

        // when
        var response = this.udrController.getUdrByOneSubscriberForCertainMonthOrYear("79821206820", 2);

        // then
        assertNull(response);
    }

    @Test
    void handleGetUdrByAllSubscribersForCertainMonth_ReturnNotNull() {
        // given
        doReturn(udrList).when(this.udrService).getUdrByAllSubscribers(2);

        // when
        var response = this.udrController.getUdrByAllSubscribersForCertainMonth(2);

        // then
        assertNotNull(response);
        assertEquals(udrList, response);
    }

    @Test
    void handleGetUdrByAllSubscribersForCertainMonth_ReturnNull() {
        // given
        doReturn(null).when(this.udrService).getUdrByAllSubscribers(13);

        // when
        var response = this.udrController.getUdrByAllSubscribersForCertainMonth(13);

        // then
        assertNull(response);
    }
}
