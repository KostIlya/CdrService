package ru.projects.CdrService.model;

import ru.projects.CdrService.util.SpecialTime;
import java.util.Objects;

/**
 * Класс модели UDR-записи
 * Представляет собой запись о номере абонента (msisdn), длительности входящего звонка (incomingCall), длительности исходящего
 * звонка (outgoingCall)
 */
public class Udr {

    private String msisdn;
    private CallDuration incomingCall;
    private CallDuration outgoingCall;

    public Udr() {
    }

    public Udr(String msisdn, CallDuration incomingCall, CallDuration outgoingCall) {
        this.msisdn = msisdn;
        this.incomingCall = incomingCall;
        this.outgoingCall = outgoingCall;
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

    public CallDuration getOutgoingCall() {
        return outgoingCall;
    }

    public void setOutgoingCall(CallDuration outgoingCall) {
        this.outgoingCall = outgoingCall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Udr udr = (Udr) o;
        return Objects.equals(msisdn, udr.msisdn) &&
                Objects.equals(incomingCall, udr.incomingCall) &&
                Objects.equals(outgoingCall, udr.outgoingCall);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msisdn, incomingCall, outgoingCall);
    }

    /**
     * Внутренний класс, представляющий длительность звонков
     * @see SpecialTime
     */
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
