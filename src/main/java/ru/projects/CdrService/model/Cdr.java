package ru.projects.CdrService.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс модели CDR-записи
 * Представляет собой запись с уникальным идентификатором(id), типом вызова(callType), номером абонента, иницирующего
 * звонок(callerNumber), номером абонента, принимающего звонок(receiverNumber), датой и временем начала звонка(startCall),
 * датой и временем конца звонка(endCall)
 */
@Entity
public class Cdr {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "call_type")
    private String callType;
    @Column(name = "caller_number")
    private String callerNumber;
    @Column(name = "receiver_number")
    private String receiverNumber;
    @Column(name = "start_call")
    private LocalDateTime startCall;
    @Column(name = "end_call")
    private LocalDateTime endCall;

    public Cdr() {
    }

    public Cdr(Long id, String callType, String callerNumber, String receiverNumber, LocalDateTime startCall, LocalDateTime endCall) {
        this.id = id;
        this.callType = callType;
        this.callerNumber = callerNumber;
        this.receiverNumber = receiverNumber;
        this.startCall = startCall;
        this.endCall = endCall;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallerNumber() {
        return callerNumber;
    }

    public void setCallerNumber(String callerNumber) {
        this.callerNumber = callerNumber;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public LocalDateTime getStartCall() {
        return startCall;
    }

    public void setStartCall(LocalDateTime startCall) {
        this.startCall = startCall;
    }

    public LocalDateTime getEndCall() {
        return endCall;
    }

    public void setEndCall(LocalDateTime endCall) {
        this.endCall = endCall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cdr cdr = (Cdr) o;
        return Objects.equals(id, cdr.id) && Objects.equals(callType, cdr.callType) && Objects.equals(callerNumber, cdr.callerNumber) && Objects.equals(receiverNumber, cdr.receiverNumber) && Objects.equals(startCall, cdr.startCall) && Objects.equals(endCall, cdr.endCall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, callType, callerNumber, receiverNumber, startCall, endCall);
    }
}
