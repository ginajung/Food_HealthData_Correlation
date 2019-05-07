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

	public ArrayList<DeathCode> readDeath(String filename) throws FileNotFoundException {

		ArrayList<DeathCode> deathCodes = new ArrayList<>();

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
//				System.out.println("Problematic line");
//				System.out.println(deathRow);
			}
		}
//		System.out.println("size of deathCodes");
//		System.out.println(deathCodes.size());

		return deathCodes;
	}

	/**
	 * This method generate HashMap, key=stateCode, value=average of percentage of
	 * excess death in category [ year, cause of death]
	 * 
	 * @param deathCodes
	 * @param yr
	 * @param cause_death
	 * @return
	 */
	public HashMap<String, Double> computeAvgDeath(ArrayList<DeathCode> deathCodes, String yr, String cause_death) {

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

	/**
	 * This method compute the difference of value between two HashMaps.
	 * [year_recent] -[ year_old]
	 * 
	 * @param year_recent_death
	 * @param year_old_death
	 * @return
	 */
	public HashMap<String, Double> computeDifference(HashMap<String, Double> year_recent_death,
			HashMap<String, Double> year_old_death) {
		HashMap<String, Double> change_death = new HashMap<>();
		for (String key : year_recent_death.keySet()) {
			double change = year_recent_death.get(key) - year_old_death.get(key);
			change_death.put(key, change);
		}
		return change_death;
	}

}
