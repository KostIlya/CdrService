package ru.projects.CdrService.util;

import ru.projects.CdrService.util.serialization.SpecialTimeDeserializer;
import ru.projects.CdrService.util.serialization.SpecialTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Duration;
import java.util.Objects;

/**
 * Утильный класс для представления времени в формате часов, минут, секунд
 * Поддерживает сериализацию и десериализацию с использованием {@link SpecialTimeSerializer} и {@link SpecialTimeDeserializer}.
 * @see SpecialTimeSerializer
 * @see SpecialTimeDeserializer
 */
@JsonSerialize(using = SpecialTimeSerializer.class)
@JsonDeserialize(using = SpecialTimeDeserializer.class)
public class SpecialTime {
    private long hours;
    private int minutes;
    private int seconds;

    private SpecialTime(long hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        normalize();
    }

    /**
     * Инициализирует объект SpecialTime с нулевыми значениями
     * @return Возвращает объект SpecialTime с нулевыми значениями
     */
    public static SpecialTime init() {
        return new SpecialTime(0, 0, 0);
    }

    /**
     * Инициализирует объект SpecialTime с указанными значениями
     * @param hours Часы
     * @param minutes Минуты
     * @param seconds Секунды
     * @return Возвращает объект SpecialTime с нулевыми значениями
     */
    public static SpecialTime of(long hours, int minutes, int seconds) {
        return new SpecialTime(hours, minutes, seconds);
    }

    /**
     * Нормализует представление минут и секунд до диапазона от 0 до 60
     */
    private void normalize() {
        if (seconds >= 60) {
            minutes += seconds / 60;
            seconds %= 60;
        }
        if (minutes >= 60) {
            hours += minutes / 60;
            minutes %= 60;
        }
    }

    /**
     * Добавляет продолжительность {@param duration} к текущему времени
     * @param duration Добавляемая продолжительность
     * @return Возвращает объект SpecialTime с добавленной продолжительностью
     */
    public SpecialTime plus(Duration duration) {
        long durationSeconds = duration.getSeconds();

        hours += durationSeconds / 60 / 60;
        minutes += (int) (durationSeconds / 60 % 60);
        seconds += (int) durationSeconds % 60;
        normalize();

        return this;
    }

    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public long getSeconds() {
        return seconds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpecialTime that = (SpecialTime) o;
        return hours == that.hours && minutes == that.minutes && seconds == that.seconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes, seconds);
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
