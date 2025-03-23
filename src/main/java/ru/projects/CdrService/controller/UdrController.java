package ru.projects.CdrService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;
import ru.projects.CdrService.model.Udr;
import ru.projects.CdrService.service.UdrService;

import java.util.List;

/**
 * Rest Контроллер для работы с запросами о получении UDR записей
 * @see UdrService
 */
@RestController
@RequestMapping(path = "/api/v1/udr-service")
public class UdrController {

    @Autowired
    private UdrService udrService;

    /**
     * Возвращает UDR запись о конкретном абоненте и за определенный месяц или за весь год
     * @param msisdn Номер абонента
     * @param month Запршиваемый месяц, указываемый числом. Если значение параметра меньше 1 или больше 12, то
     *              UDR-запись составляется за весь год
     * @return Возвращает объект Udr
     * @see ru.projects.CdrService.model.Udr
     */
    @GetMapping(path = "/udr/{msisdn}/{month}")
    public Udr getUdrByOneSubscriberForCertainMonthOrYear(@PathVariable String msisdn, @PathVariable Integer month) {
        return udrService.getUdrByOneSubscriber(msisdn, month);
    }

    /**
     * Возвращает список UDR записей, содержащий всех абонентов за определенный месяц
     * @param month Запршиваемый месяц, указываемый числом.
     * @return Возвращает список объектов (@link Udr):
     *      - {@code 200 OK} с со списком, содержащим записи UDR всех абонентов за определенный месяц
     *      - {@code 400 Bad Request} и null
     * @see ru.projects.CdrService.model.Udr
     */
    @GetMapping(path = "/all-udr/{month}")
    public ResponseEntity<List<Udr>> getUdrByAllSubscribersForCertainMonth(@PathVariable Integer month) {
        if (month < 1 || month > 12) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(udrService.getUdrByAllSubscribers(month));
    }
}
