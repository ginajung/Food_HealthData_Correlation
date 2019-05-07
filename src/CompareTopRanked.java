import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;

public class CompareTopRanked {
	
	WorkbookClass myworkbook;
	
	ArrayList<Sheet> dataSheets ;
	Map<String, County> counties ;
	ArrayList<String> statesNames;
	Map<String, State> states;
	
	// read the variables from workbookClass- counties- states
	
	public CompareTopRanked(String filename) {
	
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
	
	public CompareTopRanked() {
		
	}
	
	/**
	 * Extract HashMap key: Statename, value: mean value of input variable 
	 * @param variable
	 * @return
	 */
	public HashMap<String, Double> getValuesForVariable (String variable){
		

		HashMap<String, Double> state_variable=new HashMap<>();
		
		for (String stateName: states.keySet()) {
		
		Double valueOfVariable=states.get(stateName).stats.get(variable).getMean();
		
		state_variable.put(stateName, valueOfVariable);
		}
		return state_variable;
		
		
	}
	
	/**
	 * compute the difference of mean value between variable 1 and variable 2
	 * Generate HashMap < stateName, diff of value>
	 * 
	 * @param year1_variable
	 * @param year2_variable
	 * @return
	 */
	public HashMap<String, Double> computeDifference (String year1_variable, String year2_variable) {
		HashMap<String, Double> changeOfValue=new HashMap<>();
		
		for (String key : getValuesForVariable(year1_variable).keySet()) {
		
			double change=getValuesForVariable(year1_variable).get(key)-getValuesForVariable(year2_variable).get(key);
			
			changeOfValue.put(key, change);	
		}
		return changeOfValue;	
	}	
	
	
	
	public HashMap<String, Double> topRankedState (HashMap<String, Double> inputMap) {
		// sort by value  
		HashMap<String, Double> sortedInput =new HashMap<>();
		sortedInput = inputMap.entrySet().stream()
	    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
	    .limit(10)
	    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
	                              (e1, e2) -> e1, LinkedHashMap::new));
		

		return sortedInput;
	}
	
	
	// sort Bottom 10 ranked states
	public HashMap<String, Double> bottomRankedState (HashMap<String, Double> inputMap) {
		
		// sort by value  
			
		HashMap<String, Double> sortedInput =new HashMap<>();
		sortedInput = inputMap.entrySet().stream()
	    .sorted(Map.Entry.comparingByValue())
	    .limit(10)
	    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
	                              (e1, e2) -> e1, LinkedHashMap::new));
		

		return sortedInput;
	}
	/**
	 * 
	 * @param oneGroup
	 * @param anotherGroup
	 * @return
	 */
	public ArrayList <String> commonTopRankedState (HashMap<String, Double> oneGroup, HashMap<String, Double> anotherGroup) {
		// Array top 10 numbered state in one (Health) group and another Group
		ArrayList<String> commonState= new ArrayList<>();
		for (String key: oneGroup.keySet()) {
			if(anotherGroup.keySet().contains(key)){
				commonState.add(key);
			}	
		}
		return commonState;
	}
}
