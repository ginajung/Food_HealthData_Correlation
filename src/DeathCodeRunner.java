import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class DeathCodeRunner {
		
	public static void main(String[] args)  {
			DeathCodeReader dcr=new DeathCodeReader();
			Comparison ctr= new Comparison();
		
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
				
				
			HashMap<String, Double>  map1= ctr.topRankedState(cancer2008_2015);
			HashMap<String, Double>  map2= ctr.topRankedState(heartDisease2008_2015);
			
System.out.println(map1);

System.out.println(map2);
System.out.println(ctr.commonTopRankedState(map1, map2));
		
			
			
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();	
	}
	}
}