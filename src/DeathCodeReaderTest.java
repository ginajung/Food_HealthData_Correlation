// DESCRIPTION: Test DeathCodeReader methods.

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.junit.jupiter.api.Test;

class DeathCodeReaderTest {

	@Test
	void testComputeAvgDeathAL() throws FileNotFoundException, Exception {
		DeathCodeReader dcr = new DeathCodeReader("test_data/death_test.csv");
		Map<String, Double> averages = dcr.computeAvgDeath("2005", "Cancer");
		Double stateAverage = averages.get("AL");
		assertEquals(1.24, stateAverage, 0.1);
	}
	
	@Test
	void testComputeAvgDeathCA() throws FileNotFoundException, Exception {
		DeathCodeReader dcr = new DeathCodeReader("test_data/death_test.csv");
		Map<String, Double> averages = dcr.computeAvgDeath("2005", "Cancer");
		Double stateAverage = averages.get("CA");
		assertEquals(0.2534, stateAverage, 0.1);
	}
}
