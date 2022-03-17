package treningsapp.core;

import java.util.ArrayList;


/**
 * Class representing a program of workouts that the user may follow. 
 * a workoutProgram contains a name and an array of workouts.
 * 
 * @author simonevo
 */
public class WorkoutProgram {
    private String name;
    private ArrayList<Workout> workouts = new ArrayList<>();


    /**
     * Initialize a WorkoutProgram with provided name.
     * 
     * @param name String of the name.
     */
    public WorkoutProgram(String name) {
        this.name = name;
    }

    /**
     * Initialize an empty WorkoutProgram.
     */
    public WorkoutProgram() {
    }

    /**
     * a simple toString that contains the workoutprograms name. 
     * and the different workouts in the array workouts.
     * 
     * @return a readable string of the workoutprograms.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Programname: ");
        stringBuilder.append(getName());
        stringBuilder.append(", Workouts: ");
        int i = 0;
        for (Workout workout : workouts) {
            ++i;
            stringBuilder.append("workout");
            stringBuilder.append(i);
            stringBuilder.append(": ");
            stringBuilder.append(workout.toString());
            stringBuilder.append("\n");
        }
        String string;
        string = stringBuilder.toString();
        return string;

    }


    /**
     * Returns the name of the workoutProgram.
     * 
     * @return String of the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * set the name of the WorkoutProgram.
     * 
     * @param name String of the name.
     */
    public void setName(String name) {
        this.name = name;
    } 

    /**
     * adds the given workout to the array workouts.
     * 
     * @param workout Workout that will be added to workouts.
     */
    public void addWorkouts(Workout workout) {
        this.workouts.add(workout);
    }

    
    public void addWorkouts(ArrayList<Workout> workouts) {
        this.workouts.addAll(workouts);
    }

    /**
     * removes the given workout from the array workouts.
     * 
     * @param workout Workout that will be removed from workouts.
     */
    public void removeWorkout(Workout workout) {
        this.workouts.remove(workout);
    }

    public void removeAllWorkouts() {
        this.workouts.clear();
    }


    public ArrayList<Workout> getWorkouts() {
        return this.workouts;
    }

    /**
     * Make a copy workoutProgram, so that removeAllWorkouts does 
     * not also remove workouts from any earlier returned WorkoutProgram.
     * 
     * @return a copy.
     */
    public WorkoutProgram copy() {
        WorkoutProgram copy = new WorkoutProgram();
        copy.name = name;
        copy.workouts = new ArrayList<Workout>(workouts);
        return copy;
    }
}