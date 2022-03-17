package treningsapp.core;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkoutProgramTest {
	
	
	private WorkoutProgram workoutProgram;
	private ArrayList<Workout> workoutList;
	private Workout workout1;
	private Workout workout2;
	

	@BeforeEach
	public void setup() {
		workoutProgram = new WorkoutProgram();
		workoutList = new ArrayList<>();
		workoutList.add(workout2);
		workoutList.add(workout1);
		
	}
	
	@Test
	public void initialWorkoutListtest() {
        assertEquals(0, workoutProgram.getWorkouts().size());
        
		
	}
	
	@Test
	public void addSingleWorkoutTest() {
		workoutProgram.addWorkouts(workout1);
		assertEquals(workout1, workoutProgram.getWorkouts().get(0));
		assertEquals(1, workoutProgram.getWorkouts().size());
		
		workoutProgram.addWorkouts(workout2);
		assertEquals(workout2, workoutProgram.getWorkouts().get(1));
		assertEquals(2, workoutProgram.getWorkouts().size());
	}
	
	@Test
	public void addFullWorkoutListTest() {
		workoutProgram.addWorkouts(workoutList);
		assertEquals(workout2, workoutProgram.getWorkouts().get(0));
		assertEquals(workout1, workoutProgram.getWorkouts().get(1));
	}
	
	@Test
	public void removeSingleWorkoutTest() {
		workoutProgram.addWorkouts(workoutList);
		assertEquals(2, workoutProgram.getWorkouts().size());
		workoutProgram.removeWorkout(workout1);
		assertEquals(workout2, workoutProgram.getWorkouts().get(0));
	}
	
	@Test
	public void setNametest() {
		workoutProgram.setName("Navn");
		String expected = "Navn";
		assertEquals(expected, workoutProgram.getName());
	}

}