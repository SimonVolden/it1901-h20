package treningsapp.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetTest {
	
	private Sets set;
	
	@BeforeEach
	public void setup() {
		set = new Sets(5, 2.5);
		
	}

	@Test
	public void setRepetitonTest() {
		set.setRepetitions(6);
		int expected = 6;
		assertEquals(expected, set.getRepetions());
	}
	
	@Test
	public void setWeight() {
		set.setWeight(5.5);
		double expected = 5.5;
		assertEquals(expected, set.getWeight());
	}

}