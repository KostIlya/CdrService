# Тестовое задание СDR Service

CDR Service - это микросервис для генерации CDR записей, получения отчетов о звонках абонентов за определенный промежуток времени.

## Условия задания
Все звонки, совершенные абонентом сотового оператора, собираются на коммутаторах и фиксируются в CDR формате. Когда абонент находится в роуминге за процесс сбора его данных отвечает обслуживающая сеть абонента. Для стандартизации данных между разными операторами, международная ассоциация GSMA ввела стандарт BCE. Согласно ему, данные с CDR должны агрегировать в единый отчет UDR, который впоследствии передается оператору, обслуживающему абонента в домашней сети. На основе этого отчета, домашний оператор выставляет абоненту счет.

Цель задания – смоделировать описанный процесс в упрощенном виде.

Целевой микросервис будет генерировать CDR записи, сохранять их в базу данных и предоставлять Rest-API для получения UDR отчетов и генерации сводного отчета с CDR записями по абоненту.

CDR-запись включает в себя следующие данные:
- тип вызова (01 - исходящие, 02 - входящие);
- номер абонента, инициирующего звонок;
- номер абонента, принимающего звонок;
- дата и время начала звонка (ISO 8601);
- дата и время окончания звонка (ISO 8601);

CDR-отчет представляет из себя набор CDR-записей.
- разделитель данных – запятая;
- разделитель записей – перенос строки;
- данные обязательно формируются в хронологическом порядке;
- в рамках задания CDR-отчет может быть обычным txt\csv;

Вот пример фрагмента CDR-отчета:

    02,79876543221, 79123456789, 2025-02-10T14:56:12, 2025-02-10T14:58:20
    01,79996667755, 79876543221, 2025-02-10T10:12:25, 2025-02-10T10:12:57

UDR представляет из себя объект в формате JSON, который включает в себя номер абонента и сумму длительности его звонков.
Пример UDR объекта

    {
        "msisdn": "79992221122",
        "incomingCall": {
            "totalTime": "02:12:13"
        },
        "outcomingCall": {
        "totalTime": "00:02:50"
        }
    }

## Запуск проекта
### Через JAR-файл:
1. Установите CdrService-0.0.1-SNAPSHOT.jar в разделе [CdrService v1.0.0](https://github.com/KostIlya/CdrService/releases/tag/v1.0.0) 
2. Убедитесь, что установлена версия JDK 17 или выше


         java -version
3. Выполните команду в директории с установленным Jar-файлом


         java -jar CdrService-0.0.1-SNAPSHOT.jar
### Через исходный код:

1. Склонируйте репозиторий:


        git clone https://github.com/KostIlya/CdrService.git 


2. Выполните команды


         mvn clean install

         mvn spring-boot:run

## Эндопоинты Rest API

1. <b>GET "/api/v1/udr-service/udr/{msisdn}/{month}"</b><br/><br/>
   <b>Описание</b>: Возвращает UDR-запись по одному абоненту за запрошенный месяц или за год. Если абонента с msisdn не существует возвращается NULL.<br/><br/>
   <b>Параметры</b>:
      - msisdn - номер абонента в формате "79999999999";
      - month - запрашиваемый месяц, если значение параметра равно от 1 до 12. Если значение параметра меньше 1 или больше 12, то UDR-запись возвращается за весь год.

2. GET "/api/v1/udr-service/all-udr/{month}"<br/><br/>
   <b>Описание</b>: Возвращает список UDR записей, содержащий всех абонентов за определенный месяц<br/><br/>
   <b>Параметр</b>:
      - month - Запрашиваемый месяц, указываемый числом. Если значение параметра меньше 1 или больше 12, то выбрасывается исключение со статусом ответа Bad request.

3. <b>Post "/api/v1/cdr-report-service/report-cdr"</b><br/><br/>
   <b>Описание</b>: Генерирует и сохраняет CDR-отчет для конкретного пользователя и за определенный период в формате csv.<br/><br/>
   <b>Параметр</b> request - представляет из себя тело post-запроса, которое состоит из следующих параметров:
      - msisdn - номер абонента в формате "79999999999";
      - startDate - дата начала периода в формате "yyyy-mm-dd";
      - endDate - дата конца периода в формате "yyyy-mm-dd".

## Технологии
- OpenJDK 17
- maven
- Spring Boot
- H2 Database

## Дополнительные библиотеки
- com.fasterxml.jackson.core:jackson-databind - используется в проекте для сериализации и десериализации кастомного класса SpecialTime

### По умолчанию
Проект запускается с портом 8088.