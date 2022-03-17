package treningsapp.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class WorkoutTest {

	
	
	private Workout workout;
	private Exercise exercise1;
	private Exercise exercise2;
	private Exercise exercise3;
	
	@BeforeEach
	public void setup() {
		workout = new Workout();
		exercise1 = new Exercise("hopp");
		exercise2 = new Exercise("launch");
		exercise3 = new Exercise("hopp");
	}
	
	
	
	@Test
	public void addSingleExerciseTest() {
		assertEquals(0, workout.getExercises().size());
		workout.addExercise(exercise1);
		assertEquals(exercise1, workout.getExercises().get(0));
		assertTrue(workout.getExercises().size() == 1);
		
		workout.addExercise(exercise2);
		assertEquals(exercise2, workout.getExercises().get(1));
		
		workout.addExercise(exercise3);
		//like navn skal ikke legges til.
		assertEquals(2, workout.getExercises().size());
		assertFalse(workout.getExercises().get(workout.getExercises().size()-1).getName().equals(exercise3.getName()));
			
	}
	
	@Test
	public void removeSingelExerciseTest() {
		workout.addExercise(exercise2);
		workout.addExercise(exercise1);
		assertEquals(2, workout.getExercises().size());
		workout.removeExercise();
		assertEquals(exercise2, workout.getExercises().get(0));
		assertEquals(1, workout.getExercises().size());
	}

    @Test
    public void setNametest() {
        Workout workout = new Workout("Mandag");
        assertEquals("Mandag", workout.getName());
        workout.setName("Tirsdag");
        String expected = "Tirsdag";
        assertEquals(expected, workout.getName());
    }
}