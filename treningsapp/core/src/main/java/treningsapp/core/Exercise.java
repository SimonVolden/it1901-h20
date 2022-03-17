package treningsapp.core;

import java.util.ArrayList;

/**
 * Class representing an exercise with a name and an array containing different sets.
 * 
 * @author simonevo
 */
public class Exercise {
    private String name;
    private ArrayList<Sets> sets = new ArrayList<>();



    /** 
     * Initialize an Exercise with provided name. 
     * 
     * @param name the String that contains the name of the exercise.
     */
    public Exercise(String name) {
        this.name = name;
    }


    /**
     * a simple toString() that contains the exercise name and its sets.
     * 
     * @return a readable string of the exercise
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("exercise name: ");
        stringBuilder.append(getName());
        stringBuilder.append(", Sets: ");
        int i = 0;
        for (Sets set : sets) {
            ++i;
            stringBuilder.append("set");
            stringBuilder.append(i);
            stringBuilder.append(" ");
            stringBuilder.append(set.toString());
            stringBuilder.append(", ");
        }
        String string;
        string = stringBuilder.toString();
        return string;
    }

    public ArrayList<Sets> getSets() {
        return this.sets;
    }

    /**
     * returns the name.
     * 
     * @return String of the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the exercise.
     * 
     * @param name as input
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Takes the input and makes a new Set which is added to the array sets.
     * 
     * @param repetitions , repetitions is an int that the set contains.
     * @param weight , weight is a Double that the set contains.
     */
    public void addSet(int repetitions, Double weight) {
        this.sets.add(new Sets(repetitions, weight));
    }

    /**
     * Adds a Set to the array sets.
     * 
     * @param set , a set that will be added to sets.
     */
    public void addSet(Sets set) {
        this.sets.add(set);
    }

    /**
     * removes the input Set from the array sets.
     * 
     * @param set the Set that will be removed.
     */
    public void removeSet(Sets set) {
        this.sets.remove(set);
    } 
    
    /**
     * removes the last Set from the array sets.
     */
    public void removeSet() {
        this.sets.remove(this.sets.size() - 1);
    }

}
