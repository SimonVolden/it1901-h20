package treningsapp.restapi;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;


@Path(WorkoutProgramService.WORKOUT_PROGRAM_SERVICE_PATH)
public class WorkoutProgramService {

    public static final String 
        WORKOUT_PROGRAM_SERVICE_PATH = "workoutProgram"; //Sjekk om dette stemmer.

    public static final Logger LOG = LoggerFactory.getLogger(WorkoutProgramService.class);

    @Inject
    private WorkoutProgram workoutProgram;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WorkoutProgram getWorkoutProgram() {
        System.out.println(this);
        return workoutProgram;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void putWorkoutProgram(Workout workout) {
        //System.out.println(this);
        this.workoutProgram.addWorkouts(workout);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteWorkoutProgram() {
        this.workoutProgram.removeAllWorkouts();
    }
    
}