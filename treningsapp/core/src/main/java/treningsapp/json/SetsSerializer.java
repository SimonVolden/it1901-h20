package treningsapp.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import treningsapp.core.Sets;

public class SetsSerializer extends JsonSerializer<Sets> {

    @Override
    public void serialize(Sets sets, JsonGenerator jsonGen, SerializerProvider serializerProvider)
            throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeNumberField("repetition", sets.getRepetions());
        jsonGen.writeNumberField("weight", sets.getWeight());
        jsonGen.writeEndObject();
    }
}