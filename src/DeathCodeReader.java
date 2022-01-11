// DESCRIPTION: Read in file to the DeathCode class. Compute average deaths by state.
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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
	
    public ArrayList<String> listDeathYears(){
    	ArrayList<String> deathyears = new ArrayList<String>();
    	deathCodes.forEach(code -> {
    		String tempYear = code.getYear();
    		if (!deathyears.contains(tempYear)) {
    			deathyears.add(tempYear);
    			}
    		}
    	);
    	return deathyears;
    }

    public ArrayList<String> listCauseDeath(){
    	ArrayList<String> causedeath = new ArrayList<String>();
    	deathCodes.forEach(code -> {
    		String tempCause = code.getCauseOfDeath();
    		if (!causedeath.contains(tempCause)) {
    			causedeath.add(tempCause);
    			}
    		}
    	);
    	return causedeath;
    }
	
	@SuppressWarnings("resource")
	public DeathCodeReader(String filename) throws FileNotFoundException, Exception {

		deathCodes = new ArrayList<>();

		File file= new File(filename);
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
			} catch(Exception e){
				
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
	
		HashMap<String, Double> temp_averageDeath = new HashMap<>();
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
			if (!stateCode.equals("0") && !stateCode.equals("") && index.getYear().equals(year)
					&& index.getCauseOfDeath().equals(causeOfDeath) && locality.equals("All")
					&& !percentExcessDeath.equals("")) {
				if (temp_averageDeath.containsKey(stateCode)) {
					keyNum = keyNum + 1;
					sum = temp_averageDeath.get(stateCode) + (Double.parseDouble(percentExcessDeath));
					temp_averageDeath.put(stateCode, sum / keyNum);
				} else {
					keyNum = keyNum + 1;
					temp_averageDeath.put(stateCode, (Double.parseDouble(percentExcessDeath)) / keyNum);
				}
			}
		}
		// sort by key in alphabetically
		averageDeath = temp_averageDeath.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(
							Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));	
		return averageDeath;
	}
	
}
