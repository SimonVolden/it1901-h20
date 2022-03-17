package treningsapp.ui;

import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;

public interface WorkoutModelAccess {
    void deleteWorkoutProgram();

    WorkoutProgram getWorkoutProgram();
    
    void putWorkoutProgram(Workout workout);
}