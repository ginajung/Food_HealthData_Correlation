
import java.io.FileNotFoundException;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;


public class CorrelationPearson {
	
	Comparison ctr;
	
	public CorrelationPearson(String filename) {
		ctr = new Comparison(filename);
	}
	

	public double calculatePearson (String variable1, String variable2) {
		
		double [] values1 = ctr.getValueArrayforVariable(variable1);
		double [] values2 = ctr.getValueArrayforVariable(variable2);
		
		double cor=0;
		try{
			if(values1.length==values2.length) {
	
			 PearsonsCorrelation pCorrelation=new PearsonsCorrelation();
			 cor=pCorrelation.correlation(values1, values2);
			 
		}
		}
		catch(Exception e) {
		System.out.println("size is different");
		}
		return cor;
	}
	
	


	
public static void main(String[] args) {
		
		
		CorrelationPearson test= new CorrelationPearson("DataDownload.xls");
		
		System.out.println(test.ctr.getValuesForVariable("PCT_OBESE_ADULTS08"));
		System.out.println(test.ctr.getValuesForVariable("PCT_DIABETES_ADULTS08"));
		System.out.println(test.calculatePearson("PCT_OBESE_ADULTS08", "PCT_DIABETES_ADULTS08"));
	
//		DeathCodeReader dcr = null;
//		try {
//			dcr=new DeathCodeReader("NCHS_Causes_of_Death.csv");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	
//		PearsonsCorrelation pCorrelation=new PearsonsCorrelation();
//		
//		double[] x=dcr.getValueArrayforVariable(dcr.computeAvgDeath("2008", "Heart Disease"));
//		double[] y=test.ctr.getValueArrayforVariable("PCT_OBESE_ADULTS08");
//		 double cp=pCorrelation.correlation(x, y);
//		 
//		 System.out.println(cp);
}
}
