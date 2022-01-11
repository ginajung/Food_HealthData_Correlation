// DESCRIPTION: County class to hold county specific information from the USDA workbook (rows).

import java.util.HashMap;
import java.util.Map;

//The county class is intended to hold county specific information and 
public class County {
	String State;
	String FIPS; 
	String County; 
	Map<String, Double> stats;

//Files will need to be iterated through to add each county. 
	public County(String FIPS, String State, String County) {
		this.State = State;
		this.FIPS = FIPS; 
		this.County = County;
		this.stats = new HashMap<>();
	}
}
