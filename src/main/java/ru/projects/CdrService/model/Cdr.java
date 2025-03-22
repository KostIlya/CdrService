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
}
