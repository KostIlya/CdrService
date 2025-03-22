package ru.projects.CdrService.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

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
    @Column(name = "beginning_call")
    private LocalDateTime beginningCall;
    @Column(name = "termination_call")
    private LocalDateTime terminationCall;

    public Cdr() {
    }

    public Cdr(Long id, String callType, String callerNumber, String receiverNumber, LocalDateTime beginningCall, LocalDateTime terminationCalling) {
        this.id = id;
        this.callType = callType;
        this.callerNumber = callerNumber;
        this.receiverNumber = receiverNumber;
        this.beginningCall = beginningCall;
        this.terminationCall = terminationCalling;
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

    public LocalDateTime getBeginningCall() {
        return beginningCall;
    }

    public void setBeginningCall(LocalDateTime beginningCall) {
        this.beginningCall = beginningCall;
    }

    public LocalDateTime getTerminationCall() {
        return terminationCall;
    }

    public void setTerminationCall(LocalDateTime terminationCalling) {
        this.terminationCall = terminationCalling;
    }
}
