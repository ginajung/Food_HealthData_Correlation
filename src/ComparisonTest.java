// DESCRIPTION: Test of comparison methods.
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.junit.jupiter.api.Test;

class ComparisonTest {

	@Test
	void testStateValuesMapForVariable() throws EncryptedDocumentException, IOException {
		Comparison cpr=new Comparison("test_data/comparison_test.xls");
		Map<String, Double> state_values =cpr.stateValuesMapForVariable("PCT_DIABETES_ADULTS08");
		Double stateAverage = state_values.get("CA");
		assertEquals(7.61, stateAverage, 0.1);
	}

	@Test
	void testGetValuesArrayforVariableString() throws EncryptedDocumentException, IOException {
		Comparison cpr=new Comparison("test_data/comparison_test.xls");
		double[] values =cpr.getValuesArrayforVariable(cpr.stateValuesMapForVariable("PCT_DIABETES_ADULTS08"));
		assertEquals(7.61, values[5], 0.1);
	}

	@Test
	void testTopRankedState() throws EncryptedDocumentException, IOException {
		Comparison cpr=new Comparison("test_data/comparison_test.xls");
		Map<String, Double> top_values =cpr.topRankedState(cpr.stateValuesMapForVariable("PCT_DIABETES_ADULTS08"));
		assertEquals(10, top_values.size());
		assertEquals(12.98, top_values.get("AL"),0.1);
	}


	@Test
	void testCommonTopRankedStateStringString() throws EncryptedDocumentException, IOException {
		Comparison cpr=new Comparison("test_data/comparison_test.xls");
		ArrayList<String> common_states=cpr.commonTopRankedState("PCT_DIABETES_ADULTS08","PCT_DIABETES_ADULTS13");
		assertEquals( "AL", common_states.get(0));
	}

	@Test
	void testCalculatePearsonStringString() throws EncryptedDocumentException, IOException  {
		Comparison cpr=new Comparison("test_data/comparison_test.xls");
		double cp=cpr.calculatePearson("PCT_DIABETES_ADULTS08", "PCT_DIABETES_ADULTS08");
		double cp2=cpr.calculatePearson("PCT_DIABETES_ADULTS08", "PCT_DIABETES_ADULTS13");
		assertEquals( 1, cp);
		assertEquals( 0.98, cp2, 0.1);
	}
}
