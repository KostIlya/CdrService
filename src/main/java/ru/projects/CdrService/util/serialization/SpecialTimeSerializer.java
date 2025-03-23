package ru.projects.CdrService.util.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import ru.projects.CdrService.util.SpecialTime;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

/**
 * Сериализует объект {@link SpecialTime} в JSON
 * @see SpecialTime
 * @see JsonSerializer
 */
public class SpecialTimeSerializer extends JsonSerializer<SpecialTime> {
    @Override
    public void serialize(SpecialTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.toString());
    }
}
