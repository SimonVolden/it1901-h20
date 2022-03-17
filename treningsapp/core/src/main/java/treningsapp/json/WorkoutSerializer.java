package treningsapp.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import treningsapp.core.Exercise;
import treningsapp.core.Workout;

public class WorkoutSerializer extends JsonSerializer<Workout> {

    @Override
    public void serialize(Workout workout, JsonGenerator jsonGen,
            SerializerProvider serializerProvider) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeStringField("name", workout.getName());
        jsonGen.writeBooleanField("isSaved", workout.getSaved());
        jsonGen.writeArrayFieldStart("exercises");
        for (Exercise exercise : workout.getExercises()) {
            jsonGen.writeObject(exercise);
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();

    }
}