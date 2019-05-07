import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;

public class Comparison {

	WorkbookClass myworkbook;

	ArrayList<Sheet> dataSheets;
	Map<String, County> counties;
	ArrayList<String> statesNames;
	Map<String, State> states;

	/**
	 * Constructor with Filename Constructor with no argument
	 * 
	 * @param filename
	 */

	public Comparison(String filename) {

		try {
			myworkbook = new WorkbookClass(filename);
			dataSheets = myworkbook.listDataSheets();
			counties = myworkbook.countyCreator();
			statesNames = myworkbook.listStates(counties);
			states = myworkbook.stateCreator(counties);
		} catch (EncryptedDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Comparison() {

	}

	/**
	 * Extract HashMap key: State, value: mean value of input variable Sorted by key
	 * (state) in alphabetical order
	 * 
	 * @param variable
	 * @return
	 */

	public HashMap<String, Double> getValuesForVariable(String variable) {

		HashMap<String, Double> temp_state_variable = new HashMap<>();
		HashMap<String, Double> state_variable = new HashMap<>();

		for (String stateName : states.keySet()) {

			Double valueOfVariable = states.get(stateName).stats.get(variable).getMean();

			temp_state_variable.put(stateName, valueOfVariable);

			// sort by key in alphabetically
			state_variable = temp_state_variable.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(
					Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		}
		return state_variable;
	}

	/**
	 * This method extract values in Array
	 * 
	 * @param variable
	 * @return
	 */
	public double[] getValueArrayforVariable(String variable) {

		double[] values = new double[getValuesForVariable(variable).keySet().size()];
		int i = 0;
		for (String key : getValuesForVariable(variable).keySet()) {

			values[i] = (double) (getValuesForVariable(variable).get(key));
			i++;
		}

		return values;
	}



	/**
	 * This method sorts Top 10 ranked states in particular variable
	 * 
	 * @param inputMap
	 * @return
	 */
	public HashMap<String, Double> topRankedState(HashMap<String, Double> inputMap) {
		// sort by value
		HashMap<String, Double> sortedInput = new HashMap<>();
		sortedInput = inputMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.limit(10)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		return sortedInput;
	}

	/**
	 * This method sorts Bottom 10 ranked states in particular variable
	 * 
	 * @param inputMap
	 * @return
	 */
	public HashMap<String, Double> bottomRankedState(HashMap<String, Double> inputMap) {

		// sort by value

		HashMap<String, Double> sortedInput = new HashMap<>();
		sortedInput = inputMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(10)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		return sortedInput;
	}

	/**
	 * This method return commonTopRankedState 
	 * @param variable1
	 * @param variable2
	 * @return
	 */
	public ArrayList<String> commonTopRankedState(String variable1, String variable2) {
		
		HashMap<String, Double> oneGroup=topRankedState(getValuesForVariable(variable1));
		HashMap<String, Double> anotherGroup=topRankedState(getValuesForVariable(variable2));
	
			
		// Array top 10 numbered state in one (Health) group and another Group
		ArrayList<String> commonState = new ArrayList<>();
		for (String key : oneGroup.keySet()) {
			if (anotherGroup.keySet().contains(key)) {
				commonState.add(key);
			}
		}
		return commonState;
	}
	
	
	
	
	/**
	 * This method computes the difference of mean value between variable 1 and
	 * variable 2 Generate HashMap < stateName, difference of value>
	 * 
	 * @param year1_variable
	 * @param year2_variable
	 * @return
	 */
	public HashMap<String, Double> computeDifference(String year1_variable, String year2_variable) {

		HashMap<String, Double> changeOfValue = new HashMap<>();

		for (String key : getValuesForVariable(year1_variable).keySet()) {

			double change = getValuesForVariable(year1_variable).get(key)
					- getValuesForVariable(year2_variable).get(key);

			changeOfValue.put(key, change);
		}
		return changeOfValue;
	}
}
