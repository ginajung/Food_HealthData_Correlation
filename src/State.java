//This will hold all the related counties.
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class State {
	String State; 
	Map<String, DescriptiveStatistics> stats;
	
	public State(String State) {
		this.State = State;
		//State specific states will need to be aggregated using HashMap key from all county stats. 
		this.stats = new HashMap<>();
	}
}
