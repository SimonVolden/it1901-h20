package treningsapp.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;

public class WorkoutProgramSerializer extends JsonSerializer<WorkoutProgram> {

    @Override
    public void serialize(WorkoutProgram workoutProgram, JsonGenerator jsonGen,
            SerializerProvider serializerProvider) throws IOException {
    
        jsonGen.writeStartObject();
        jsonGen.writeStringField("name", workoutProgram.getName());
        jsonGen.writeArrayFieldStart("workouts");
        for (Workout workout : workoutProgram.getWorkouts()) {
            jsonGen.writeObject(workout);
        }
        jsonGen.writeEndArray();
        jsonGen.writeEndObject();

    }
}