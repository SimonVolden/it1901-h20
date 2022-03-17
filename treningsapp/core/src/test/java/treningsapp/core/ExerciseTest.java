package treningsapp.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciseTest {
    private Exercise exercise1;
	private Exercise exercise2;
	private Sets set1;

	@BeforeEach
	public void setup() {
		exercise1 = new Exercise("Launch");
		exercise2 = new Exercise("Pullups");
		set1 = new Sets(3, 6.5);
		exercise2.addSet(5, 4.5);
		exercise2.addSet(2, 1.5);
		exercise2.addSet(set1);
		
	}
	@Test
	public void addSettest() {
		assertEquals(0,  exercise1.getSets().size());
		exercise1.addSet(3, 5.5);
		assertEquals(1, exercise1.getSets().size());
		exercise1.addSet(4, 7.5);
		assertEquals(2,  exercise1.getSets().size());
	}
	
	
	@Test
	public void removeSetTest() {
		assertEquals(3, exercise2.getSets().size());
		exercise2.removeSet(set1);
		assertEquals(2, exercise2.getSets().size());
    }
    
    @Test
    public void setNameTest() {
        exercise1.setName("Cardio");
        String expected = "Cardio";
        assertEquals(expected, exercise1.getName());
    }
}
