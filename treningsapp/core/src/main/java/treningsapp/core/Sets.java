package treningsapp.core;

/**
 * Represents a Set that will be used in the class Exercise. 
 * A set contains how many repetitions and how much weight an exercise has.
 * 
 * @author simonevo
 * 
 */
public class Sets {
    int repetitions;
    Double weight;

    /**
     * Initialize a Set with provided repetitions and weight.
     * 
     * @param repetitions int that contains the given repetitions.
     * @param weight double that contains the weight
     */
    public Sets(int repetitions, Double weight) {
        this.repetitions = repetitions;
        this.weight = weight;
    }
    
    /**
     * a simple toString().
     * 
     * @return returns a readable string of the set.
     */
    @Override
    public String toString() {
        return String.format("[Set repetition=%d weight=%s]", 
            getRepetions(), Double.toString(getWeight()));
    }

    /**
     * Gets the sets repetition.
     * 
     * @return returns the int weight.
     */
    public int getRepetions() {
        return this.repetitions;
    }

    /**
     * sets Set.repetitions to the given int.
     * 
     * @param repetitions int of repetitions
     */
    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    /**
     * Gets the weight of the set.
     * 
     * @return returns the Double weight.
     */
    public Double getWeight() {
        return this.weight;
    }

    /**
     * Set the weight of the set to the given input.
     * 
     * @param weight Double of the weight.
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }
}

