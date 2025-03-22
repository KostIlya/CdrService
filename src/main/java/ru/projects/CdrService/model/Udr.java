package ru.projects.CdrService.model;


import java.time.LocalTime;

public class Udr {

    private String msisdn;
    private CallDuration incomingCall;
    private CallDuration outcomingCall;

    public Udr() {
    }

    public Udr(String msisdn, CallDuration incomingCall, CallDuration outcomingCall) {
        this.msisdn = msisdn;
        this.incomingCall = incomingCall;
        this.outcomingCall = outcomingCall;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public CallDuration getIncomingCall() {
        return incomingCall;
    }

    public void setIncomingCall(CallDuration incomingCall) {
        this.incomingCall = incomingCall;
    }

    public CallDuration getOutcomingCall() {
        return outcomingCall;
    }

    public void setOutcomingCall(CallDuration outcomingCall) {
        this.outcomingCall = outcomingCall;
    }

    public static class CallDuration {
        private LocalTime totalTime;
        public CallDuration() {
            this.totalTime = LocalTime.of(0,0,0);
        }
        public CallDuration(LocalTime totalTime) {
            this.totalTime = totalTime;
        }

        public LocalTime getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(LocalTime totalTime) {
            this.totalTime = totalTime;
        }
    }
}
