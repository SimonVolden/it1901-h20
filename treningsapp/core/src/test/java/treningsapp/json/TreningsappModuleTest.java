package treningsapp.json;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import treningsapp.core.Exercise;
import treningsapp.core.Sets;
import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;

public class TreningsappModuleTest {

    private static ObjectMapper mapper;

    @BeforeAll
    public static void setup() {
        mapper = new ObjectMapper();
        mapper.registerModule(new TreningsappModule());
    }

    private final static String simpleWorkoutProgram = 
        "{\"name\":\"Program1\",\"workouts\":[{\"name\":\"Trening1\",\"isSaved\":true,\"exercises\":[{\"name\":\"Benkpress\",\"sets\":[{\"repetition\":1,\"weight\":1.5},{\"repetition\":2,\"weight\":2.0}]}]},{\"name\":\"Trening2\",\"isSaved\":false,\"exercises\":[{\"name\":\"Knebøy\",\"sets\":[{\"repetition\":2,\"weight\":2.0},{\"repetition\":1,\"weight\":1.5}]}]}]}";

    static void checkSet(Sets set, int repetitions, Double weight){
        assertEquals(repetitions, set.getRepetions());
        assertEquals(weight, set.getWeight());
    }

    static void checkExercise(Exercise exercise, String name, ArrayList<Sets> sets){
        assertEquals(name, exercise.getName());
        ArrayList<Sets> origin = exercise.getSets();
        if (origin.size() != sets.size()){
            fail();
        }
        for (int i = 0; i<origin.size(); i++ ){
            checkSet(origin.get(i), sets.get(i).getRepetions(),sets.get(i).getWeight());
        }
    } 

    static void checkWorkout(Workout workout, String name, ArrayList<Exercise> exercises){
        assertEquals(name, workout.getName());
        ArrayList<Exercise> origin = workout.getExercises();
        if (origin.size() != exercises.size()){
            fail();
        }
        for (int i = 0; i<origin.size(); i++ ){
            checkExercise(origin.get(i), exercises.get(i).getName(), exercises.get(i).getSets());
        }
    }

    static void checkWorkoutProgram(WorkoutProgram workoutProgram, String name, ArrayList<Workout> workouts){
        assertEquals(name, workoutProgram.getName());
        ArrayList<Workout> origin = workoutProgram.getWorkouts();
        if (origin.size() != workouts.size()){
            fail();
        }
        for (int i = 0; i<origin.size(); i++ ){
            checkWorkout(origin.get(i), workouts.get(i).getName(), workouts.get(i).getExercises());
        }

    }

    @Test
    public void testSerializersDeserializers() {
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
            assertEquals(simpleWorkoutProgram.replaceAll("\\s+", ""), mapper.writeValueAsString(workoutProgram));
            WorkoutProgram workoutProgram2 = mapper.readValue(simpleWorkoutProgram, WorkoutProgram.class);
            checkWorkoutProgram(workoutProgram2, workoutProgram.getName(), workoutProgram.getWorkouts());
        } catch (JsonProcessingException e) {
            fail();
        }     
    }

}
