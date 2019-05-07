import java.util.ArrayList;

import java.util.HashMap;

public class CompareRunner {
	
	public static void main(String[] args) {
			
		//file in DataDownload
		Comparison  ctr=new Comparison("DataDownload.xls");
		
		System.out.println("Common States for varialbe1 and variable2 are  " + ctr.commonTopRankedState("PCT_DIABETES_ADULTS13", "PCT_DIABETES_ADULTS08"));
		
		
		// between variable vs. causeOfDeath percent
		
		DeathCodeReader dcr = null;
		try {
		
			dcr=new DeathCodeReader("NCHS_Causes_of_Death.csv");
		
		
//			1. YEAR ; 2005- 2015
//	2. Cause of Death  ; Cancer, Stroke,Unintentional Injury,Chronic Lower Respiratory Disease,Heart Disease
		
		
} catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();	
}

		System.out.println(dcr.computeAvgDeath("2008", "Cancer"));
		
		System.out.println(dcr.commonTopRankedState_Death("PCT_DIABETES_ADULTS13", "2013", "Heart Disease"));
		}
}
