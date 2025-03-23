package ru.projects.CdrService.util.serialization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.projects.CdrService.util.SpecialTime;

import java.io.IOException;

/**
 * Десериализует JSON в объект {@link SpecialTime}
 * @see SpecialTime
 * @see JsonDeserializer
 */
public class SpecialTimeDeserializer  extends JsonDeserializer<SpecialTime> {
    @Override
    public SpecialTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String timeString = jsonParser.getText();

        String[] timeStringSplit = timeString.split(":");

        if (timeStringSplit.length != 3) {
            throw new IOException("Invalid input json.");
        }

        long hours = Long.parseLong(timeStringSplit[0]);
        int minutes = Integer.parseInt(timeStringSplit[1]);
        int seconds = Integer.parseInt(timeStringSplit[2]);

        return SpecialTime.of(hours, minutes, seconds);
    }
}
