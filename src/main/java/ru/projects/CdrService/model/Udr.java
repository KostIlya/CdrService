package ru.projects.CdrService.model;

import ru.projects.CdrService.util.SpecialTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Udr udr = (Udr) o;
        return Objects.equals(msisdn, udr.msisdn) &&
                Objects.equals(incomingCall, udr.incomingCall) &&
                Objects.equals(outcomingCall, udr.outcomingCall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msisdn, incomingCall, outcomingCall);
    }

    public static class CallDuration {
        private SpecialTime totalTime;

        public CallDuration() {
            this.totalTime = SpecialTime.init();
        }

        public CallDuration(SpecialTime totalTime) {
            this.totalTime = totalTime;
        }

        public SpecialTime getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(SpecialTime totalTime) {
            this.totalTime = totalTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CallDuration that = (CallDuration) o;
            return Objects.equals(totalTime, that.totalTime);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(totalTime);
        }
    }
}
