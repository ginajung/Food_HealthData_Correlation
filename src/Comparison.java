// DESCRIPTION: Variety of methods of comparing variables with health outcomes.

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.poi.EncryptedDocumentException;

public class Comparison {
	Map<String, County> counties;
	Map<String, State> states;
	Map<String, String> variableNames;

	/**
	 * Constructor with Filename Constructor with no argument
	 * 
	 * @param filename
	 * @throws IOException 
	 * @throws EncryptedDocumentException 
	 */

	public Comparison(String filename) throws EncryptedDocumentException, IOException {
			CountyWorkbook myworkbook = new CountyWorkbook(filename);
			counties = myworkbook.countyCreator();
			states = myworkbook.stateCreator(counties);
			variableNames = myworkbook.extractHumanReadableVariableNames();
	}

	/**
	 * This method extracts [key: State, value: mean value of input variable Sorted by key]
	 * (state) in alphabetical order
	 * 
	 * @param 
	 * @return
	 */

	public HashMap<String, Double> stateValuesMapForVariable(String variable) {

		HashMap<String, Double> temp_states_values = new HashMap<>();
		HashMap<String, Double> states_values = new HashMap<>();

		for (String stateName : states.keySet()) {

			Double valueOfVariable = states.get(stateName).stats.get(variable).getMean();

			temp_states_values.put(stateName, valueOfVariable);

			// sort by key in alphabetically
			states_values = temp_states_values.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(
					Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		}
		return states_values;
	}

	// METHOD : get different type of parameter : String or HashMap
	/**
	 * This method extract only values in Array from HashMap [key: State, value: mean value ]
	 * 
	 * @param variable
	 * @return
	 */
	public double[] getValuesArrayforVariable(String variable) {

		double[] values = new double[stateValuesMapForVariable(variable).keySet().size()];
		int i = 0;
		for (String key : stateValuesMapForVariable(variable).keySet()) {

			values[i] = (double) (stateValuesMapForVariable(variable).get(key));
			i++;
		}

		return values;
	}

	
	/**
	 * The same method with getValueArrayForVariable with Map input
	 * @param inputMap
	 * @return
	 */
	public double[] getValuesArrayforVariable(HashMap<String, Double> inputMap) {
		double[] values = new double[inputMap.keySet().size()];
		int i = 0;
		for (String key : inputMap.keySet()) {

			values[i] = (double) (inputMap.get(key));
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
	public HashMap<String, Double> topRankedState(HashMap<String, Double> inputMap){
		// sort by value
		HashMap<String, Double> sortedInput = new HashMap<>();
		sortedInput = inputMap.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
					.limit(10)
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		return sortedInput;
	}

	/**
	 * This method sorts bottom 10 ranked states in particular variable
	 * 
	 * @param inputMap
	 * @return
	 */
	public HashMap<String, Double> bottomRankedState(HashMap<String, Double> inputMap){
		// sort by value
		HashMap<String, Double> sortedInput = new HashMap<>();
		sortedInput = inputMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
					.limit(10)
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		return sortedInput;
	}

	/**
	 * This method return commonTopRankedState 
	 * @param variable1
	 * @param variable2
	 * @return
	 */
	public ArrayList<String> commonTopRankedState(String variable1, String variable2){
		HashMap<String, Double> oneGroup=topRankedState(stateValuesMapForVariable(variable1));
		HashMap<String, Double> anotherGroup=topRankedState(stateValuesMapForVariable(variable2));
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
	 * 
	 * @param oneGroup
	 * @param anotherGroup
	 * @return
	 */
	public ArrayList <String> commonTopRankedState (HashMap<String, Double> one, HashMap<String, Double> another){
		ArrayList<String> commonState1 = new ArrayList<>();
		for (String key: one.keySet()) {
			if(another.keySet().contains(key)){
				commonState1.add(key);
			}	
		}
		return commonState1;
	}
	
	public double calculatePearson(double[] values1, double[] values2){
		double cor = 0;
		if(values1.length == values2.length) {
			PearsonsCorrelation pCorrelation=new PearsonsCorrelation();
			cor=pCorrelation.correlation(values1, values2);
		} else {
			System.out.println("Their sizes are different");
		}
		return cor;
	}

	public double calculatePearson(String variable1, String variable2){
		double[] values1 = getValuesArrayforVariable(variable1);
		double[] values2 = getValuesArrayforVariable(variable2);
		return calculatePearson(values1, values2);
	}
	
	public double calculatePearson(HashMap<String, Double> one, HashMap<String, Double> another){
		double [] values1 = getValuesArrayforVariable(one);
		double [] values2 = getValuesArrayforVariable(another);
		return calculatePearson(values1, values2);
	}
	
	public String stateStringStats(String statekey, String variablekey){
		DescriptiveStatistics workingstats = states.get(statekey).stats.get(variablekey);
		String out;
		out = "Number of Counties: " + String.valueOf(workingstats.getN()) + "\n"+
			  "Maximum: " + String.valueOf(workingstats.getMax()) + "\n"+
			  "Minimum: " + String.valueOf(workingstats.getMin()) + "\n"+
			  "Average: " + String.valueOf(workingstats.getMean()) + "\n"+
			  "Standard Deviation: " + String.valueOf(workingstats.getStandardDeviation());
	 return out;	
	}
}
