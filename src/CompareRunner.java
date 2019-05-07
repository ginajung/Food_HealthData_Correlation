import java.util.ArrayList;

import java.util.HashMap;

public class CompareRunner {
	
	public static void main(String[] args) {
			
		CompareTopRanked ctr=new CompareTopRanked("DataDownload.xls");
		
		
		//hashMap diabetes difference between 2013-2008
		HashMap <String, Double> diabetesRateChange= ctr.computeDifference("PCT_DIABETES_ADULTS13", "PCT_DIABETES_ADULTS08");
		HashMap <String, Double> obeseRateChange=ctr.computeDifference("PCT_OBESE_ADULTS13", "PCT_OBESE_ADULTS08");
		HashMap <String, Double> farmersRateChange =ctr.getValuesForVariable("PCH_FMRKTPTH_09_16");
		
		HashMap<String, Double>  map1= ctr.topRankedState(diabetesRateChange);
		HashMap<String, Double>  map2= ctr.topRankedState(obeseRateChange);
		HashMap<String, Double>  map3= ctr.topRankedState(farmersRateChange);		
		
		System.out.println("Common States for topRanked diabetes and obesity  :"+ ctr.commonTopRankedState(map1, map2));
		System.out.println("Common States for topRanked obesity and farmersMarketSale   :"+ ctr.commonTopRankedState(map2, map3));
		System.out.println("Common States for topRanked diabetes and farmersMarketSale   :"+ ctr.commonTopRankedState(map1, map3));
		
		
		
		
		DeathCodeReader dcr=new DeathCodeReader();
		CompareTopRanked ctr2= new CompareTopRanked();
	
		ArrayList<DeathCode> result;
		try {
			result= dcr.readDeath("NCHS_Causes_of_Death.csv");
		
			HashMap<String, Double> avg_2008_Cancer=dcr.computeAvgDeath(result, "2008", "Cancer");
			System.out.println(avg_2008_Cancer);
			
			HashMap<String, Double> avg_2015_Cancer=dcr.computeAvgDeath(result, "2015", "Cancer");
			HashMap<String, Double> cancer2008_2015=dcr.computeDifference(avg_2015_Cancer, avg_2008_Cancer);
			
			
			HashMap<String, Double> avg_2008_HeartDisease=dcr.computeAvgDeath(result, "2008", "Heart Disease");
			HashMap<String, Double> avg_2015_HeartDisease=dcr.computeAvgDeath(result, "2015", "Heart Disease");
			
			HashMap<String, Double> heartDisease2008_2015=dcr.computeDifference (avg_2015_HeartDisease, avg_2008_HeartDisease);
			
			System.out.println(cancer2008_2015);
			System.out.println(heartDisease2008_2015);
			
			
		HashMap<String, Double>  m1= ctr2.topRankedState(cancer2008_2015);
		HashMap<String, Double>  m2= ctr2.topRankedState(heartDisease2008_2015);
		
System.out.println(m1);

System.out.println(m2);
System.out.println(ctr2.commonTopRankedState(m1, m2));
	
		
		
} catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();	
}
}
}
