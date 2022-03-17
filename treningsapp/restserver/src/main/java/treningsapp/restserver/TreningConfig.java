package treningsapp.restserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import treningsapp.core.Exercise;
import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;
import treningsapp.json.TreningsappModule;
import treningsapp.restapi.WorkoutProgramService;

public class TreningConfig extends ResourceConfig {

    private WorkoutProgram workoutModel;

    /**
     * Initialize this TreningConfig.
     * 
     * @param workoutModel workoutModel instance to serve
     */
    public TreningConfig(WorkoutProgram workoutModel) {
        setWorkoutModel(workoutModel);
        register(WorkoutProgramService.class);
        register(TreningModuleObjectMapperProvider.class);
        register(JacksonFeature.class);
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(TreningConfig.this.workoutModel);
            }
        });
    }

    public TreningConfig() {
        this(createDefaultWorkoutModel());
    }

    public WorkoutProgram getWorkoutModel() {
        return this.workoutModel;
    }

    public void setWorkoutModel(WorkoutProgram workoutModel) {
        this.workoutModel = workoutModel;
    }
    

    private static WorkoutProgram getDefaultWorkoutHistoryFile() throws IOException {

        WorkoutProgram wh = new WorkoutProgram();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new TreningsappModule());
        // Path fileName =
        // Path.of("treningsapp/src/main/resources/savefiles/saveFile.json");
        Path fileName = null;
        try {
            fileName = Paths.get(System.getProperty("user.home"), "workoutHistoryFile.json");
        } catch (Exception e) {
            System.out.println("Could not find a saved file, making new");

        }
        Stream<String> saveFiles;
        try {
            saveFiles = Files.lines(fileName);
            try {
                String saveFile = saveFiles.iterator().next();
                saveFiles.close();
                try {
                    wh = mapper.readValue(saveFile, WorkoutProgram.class);
                } catch (JsonMappingException e) {

                    e.printStackTrace();
                } catch (JsonProcessingException e) {

                    e.printStackTrace();
                }
            } catch (NoSuchElementException e2) {
                e2.printStackTrace();
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return wh;
    }

    private static WorkoutProgram createDefaultWorkoutModel() {

        try {
            return getDefaultWorkoutHistoryFile();
        } catch (IOException e) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new TreningsappModule());
            URL url = TreningConfig.class.getResource("default-workoutmodel.json");
            if (url != null) {
                try (Reader reader = 
                    new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)) {
                    return mapper.readValue(reader, WorkoutProgram.class);
                } catch (IOException e2) {
                    String couldnt = "Couldn't read default-todomodel.json,";
                    String rigging = " rigging workoutmodel manually (";
                    System.out.println(couldnt + rigging + e2 + ")");
                }
            }

            Exercise bench = new Exercise("Bench");
            bench.addSet(10, 20.0);
            bench.addSet(10, 20.0);
            bench.addSet(10, 20.0);

            Exercise squat = new Exercise("Squat");
            squat.addSet(10, 30.0);
            squat.addSet(10, 30.0);
            squat.addSet(10, 30.0);

            Exercise deadlift = new Exercise("Deadlift");
            deadlift.addSet(10, 40.0);
            deadlift.addSet(10, 40.0);
            deadlift.addSet(10, 40.0);

            Workout w1 = new Workout();
            w1.addExercise(bench);
            w1.addExercise(squat);
            w1.addExercise(deadlift);
            w1.setName("ExampleWorkout");

            WorkoutProgram workoutModel = new WorkoutProgram();
            workoutModel.addWorkouts(w1);

            return workoutModel;
        }
    }

}