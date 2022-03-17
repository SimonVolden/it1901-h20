package treningsapp.restapi;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import treningsapp.core.WorkoutProgram;

public class TreningsappResource {

    private static final Logger LOG = LoggerFactory.getLogger(TreningsappResource.class);

    private final WorkoutProgram workoutProgram;
    private final String name;

    /**
     * Initializes this WorkoutProgramResource with a workoutProgram and its name.
     * Each method will check and use what it needs.
     * 
     * @param workoutProgram The workoutProgram, 
     * @param name The workoutProgram name.
     */
    public TreningsappResource(WorkoutProgram workoutProgram, String name) {
        this.workoutProgram = workoutProgram;
        this.name = name;
    }

    /**
     * Checking is the workoutprogram exists.
     */
    private void checkWorkoutProgram() {
        if (this.workoutProgram == null) {
            throw new IllegalArgumentException("No Workoutprogram named \"" + name + "\"");
        }
    }
  
    /**
     * Gets the WorkoutProgram.
     * @return the WorkoutProgram
     */
    @Path("{workoutProgram}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WorkoutProgram getWorkoutProgram() {
        checkWorkoutProgram();
        LOG.debug("getWorkoutProgram({})", name);
        return workoutProgram;
    }

    /**
     * Replaces or adds a workoutProgram.
     * 
     * @param workoutProgramArg the workoutProgram to add
     * @return true if it was added, false if it was replaced
     */
    @Path("{workoutProgram}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean putWorkoutProgram(WorkoutProgram workoutProgramArg) {
        LOG.debug("putWorkoutProgram({})", workoutProgramArg);
        return this.workoutProgram.getName().equals(workoutProgramArg.getName());
    }


}