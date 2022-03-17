package treningsapp.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import treningsapp.core.Exercise;
import treningsapp.core.Sets;


public class ExerciseSerializer extends JsonSerializer<Exercise> {

    @Override
    public void serialize(Exercise exercise, JsonGenerator jsonGen,
            SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("name", exercise.getName());
        jsonGen.writeArrayFieldStart("sets");
        for (Sets set : exercise.getSets()) {
            jsonGen.writeObject(set);
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();
    }
}