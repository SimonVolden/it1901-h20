package treningsapp.ui;

import java.util.ArrayList;
import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;


public class DirectModelAccess implements WorkoutModelAccess {

    private WorkoutProgram workoutProgram = new WorkoutProgram();

    public DirectModelAccess() {
    }

    public void deleteWorkoutProgram() {
        ArrayList<Workout> workouts = workoutProgram.getWorkouts();
        workoutProgram.removeWorkout(workouts.get(workouts.size() - 1));
    }

    public WorkoutProgram getWorkoutProgram() {
        return workoutProgram.copy();
    }

    public void putWorkoutProgram(Workout workout) {
        workoutProgram.addWorkouts(workout);
    }
}