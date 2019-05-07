import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DeathCodeReader {
	/**
	 * This class read FiveLeadingCasuseOfDeath data and organize the statistic
	 * values in each State.
	 * 
	 * @param filename
	 * @return
	 * @throws FileNotFoundException
	 */
	ArrayList<DeathCode> deathCodes;
	
	public  DeathCodeReader(String filename) throws FileNotFoundException {

		 deathCodes = new ArrayList<>();

		File file = new File(filename);
		// read 'NCHS' file

		Scanner scanner = new Scanner(file);
		scanner.nextLine();
		// skip the first header

		while (scanner.hasNextLine()) {

			String deathRow = scanner.nextLine();
			String[] deathRowData = deathRow.split(",");
		
			/*
			 * Take the value corresponding to each Datum and save as Array In this step,
			 * all data called as String to handle empty one.
			 */
			try
			{
				String year = deathRowData[0];
				String causeOfDeath = deathRowData[1];
				String stateCode = deathRowData[3];
				String locality = deathRowData[7];
				String percentExcessDeath = deathRowData[12];

				DeathCode deathCode = new DeathCode(year, causeOfDeath, stateCode, locality, percentExcessDeath);

				deathCodes.add(deathCode);
			}catch(Exception e)
			{
			
			}
		}

		
	}

	/**
	 * This method generate HashMap, key=stateCode, value=average of percentage of
	 * excess death in category [ year, cause of death]
	 * 
	 * @param yr
	 * @param cause_death
	 * @return
	 */
	
	public HashMap<String, Double> computeAvgDeath(String yr, String cause_death) {
	
		//	1. YEAR ; 2005- 2015
		//	2. Cause of Death  ; Cancer, Stroke,Unintentional Injury,Chronic Lower Respiratory Disease,Heart Disease
	
		HashMap<String, Double> averageDeath = new HashMap<>();
		String year = yr;
		String causeOfDeath = cause_death;
		Double sum = 0.0;
		Double keyNum = 0.0;

		for (DeathCode index : deathCodes) {

			// Get required columns: year, stateCode, casueOfDeath, locality,
			// percentExcessDeath

			String stateCode = index.getStateCode();
			String locality = index.getLocality();
			String percentExcessDeath = index.getPercentExcessDeath();

			// Exclude empty stateCode and stateCode=0, include only locality=all, year=yr,
			// cause_death
			// To update existing Key, and compute the number of the same stateCode

			// HashMap with stateCode: average of PED

			if (!stateCode.equals("0") && !stateCode.equals("") && index.getYear().equals(year)
					&& index.getCauseOfDeath().equals(causeOfDeath) && locality.equals("All")
					&& !percentExcessDeath.equals("")) {
				if (averageDeath.containsKey(stateCode)) {
					keyNum = keyNum + 1;
					sum = averageDeath.get(stateCode) + (Double.parseDouble(percentExcessDeath));
					averageDeath.put(stateCode, sum / keyNum);
				} else {
					keyNum = keyNum + 1;
					averageDeath.put(stateCode, (Double.parseDouble(percentExcessDeath)) / keyNum);
				}
			}
		}
		return averageDeath;
	}

public ArrayList<String> commonTopRankedState_Death( String variable, String yr, String causeOfDeath) {
		
		Comparison ctr=new Comparison();
		
		HashMap<String, Double> one=ctr.topRankedState(ctr.getValuesForVariable(variable));
		HashMap<String, Double> another=computeAvgDeath(yr, causeOfDeath);
		ArrayList<String> commonState = new ArrayList<>();
		for (String key : one.keySet()) {
			if (another.keySet().contains(key)) {
				commonState.add(key);
			}
		}
		return commonState;
	}
	


public double[] getValueArrayforVariable(HashMap<String, Double> inputMap) {

	double[] values = new double[inputMap.keySet().size()];
	int i = 0;
	for (String key : inputMap.keySet()) {

		values[i] = (double) (inputMap.get(key));
		i++;
	}

	return values;
}	
	
}
