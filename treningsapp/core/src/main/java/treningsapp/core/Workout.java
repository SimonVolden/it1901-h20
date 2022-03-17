package treningsapp.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;


/**
 * Class representing a workout with a name and an array containing different exercises.
 * 
 * @author simonevo
 */
public class Workout {
    private String name;
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private Boolean isSaved;


    /** 
     * Initialize a Workout with provided.
     * 
     * @param name String of the workout name.
     */
    public Workout(String name) {
        this.name = name;
        this.isSaved = false;
    }
    /**
     * Initialize a workout without provided name.
     * 
     */

    public Workout() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        Date date = new Date();  
        this.name = formatter.format(date);
        this.isSaved = false;  
    }

    /**
     * a simple toString() that contains the workout name and its sets.
     * 
     * @return a readable string of the workout.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("workoutname: ");
        stringBuilder.append(getName());
        stringBuilder.append(", isSaved: ");
        stringBuilder.append(getSaved());
        stringBuilder.append(", Exercise ");
        int i = 0;
        for (Exercise exercise : exercises) {
            ++i;
            stringBuilder.append("Exercise ");
            stringBuilder.append(i);
            stringBuilder.append(" ");
            stringBuilder.append(exercise.toString());
        }
        String string;
        string = stringBuilder.toString();
        return string;
    }

    /**
     * Add a exercise method.
     * 
     * @param exercise a exercise to be added to list 
     */

    public void addExercise(Exercise exercise) {
        boolean notMultiple = true;
        Iterator<Exercise> exerciseIterator = this.exercises.iterator();
        while (exerciseIterator.hasNext()) {
            if (exerciseIterator.next().getName().equals(exercise.getName()))  {
                notMultiple = false;
            }
        }
        if (notMultiple == true) {
            this.exercises.add(exercise);
        }
        
    }

    public void removeExercise() {
        this.exercises.remove(this.exercises.size() - 1);
    }

    public ArrayList<Exercise> getExercises() {
        return this.exercises;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Exercise getExercise(int pos) {
        return this.exercises.get(pos);
    }

    public void setSaved(Boolean isSaved) {
        this.isSaved = isSaved;
    }
    
    public Boolean getSaved() {
        return this.isSaved;
    }

}