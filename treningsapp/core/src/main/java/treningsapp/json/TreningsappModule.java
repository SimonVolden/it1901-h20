package treningsapp.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import treningsapp.core.Exercise;
import treningsapp.core.Sets;
import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;

@SuppressWarnings("serial")
public class TreningsappModule extends SimpleModule {

    private static final String NAME = "TreningsappModule";
    

    /**
     * constructor for treningsappModule.
     */
    public TreningsappModule() {

        super(NAME, Version.unknownVersion());
        addSerializer(Sets.class, new SetsSerializer());
        addSerializer(Exercise.class, new ExerciseSerializer());
        addSerializer(Workout.class, new WorkoutSerializer());
        addSerializer(WorkoutProgram.class, new WorkoutProgramSerializer());
        addDeserializer(Sets.class, new SetsDeserializer());
        addDeserializer(Exercise.class, new ExerciseDeserializer());
        addDeserializer(Workout.class, new WorkoutDeserializer());
        addDeserializer(WorkoutProgram.class, new WorkoutProgramDeserializer());
    }


    /**
     * Main method. 
     * 
     * @param args takes in args
     * @throws JsonProcessingException exception for processing
     */
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new TreningsappModule());
        Sets set1 = new Sets(1, 1.5);
        Sets set2 = new Sets(2, 2.0);
        Exercise exercise1 = new Exercise("Benkpress");
        exercise1.addSet(set1);
        exercise1.addSet(set2);
        Exercise exercise2 = new Exercise("Knebøy");
        exercise2.addSet(set2);
        exercise2.addSet(set1);
        Workout workout1 = new Workout("Trening1");
        workout1.addExercise(exercise1);
        workout1.setSaved(true);
        Workout workout2 = new Workout("Trening2");
        workout2.addExercise(exercise2);
        WorkoutProgram workoutProgram = new WorkoutProgram("Program1");
        workoutProgram.addWorkouts(workout1);
        workoutProgram.addWorkouts(workout2);
        
        try {
            System.out.println(mapper.writeValueAsString(workoutProgram));
            System.out.println("now, we deserialize \n \n");
            String json = mapper.writeValueAsString(workoutProgram);
            System.out.println("mapped");
            WorkoutProgram workoutProgram2 = mapper.readValue(json, WorkoutProgram.class);
            System.out.println(workoutProgram2.toString());
            
        } catch (Exception e) {
            System.err.println("Did not work");
            e.printStackTrace();
        }

    }
}
