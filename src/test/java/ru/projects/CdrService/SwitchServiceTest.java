package ru.projects.CdrService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.projects.CdrService.repository.CdrRepository;
import ru.projects.CdrService.repository.SubscriberRepository;
import ru.projects.CdrService.service.SwitchService;

@ExtendWith(MockitoExtension.class)
public class SwitchServiceTest {
    @Mock
    CdrRepository cdrRepository;

    @Mock
    SubscriberRepository subscriberRepository;

    @InjectMocks
    SwitchService switchService;


}
