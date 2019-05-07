import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class descriptiveStatistics {
	
	public static void main(String[] args) throws EncryptedDocumentException, IOException {
		CompareTopRanked ctr=new CompareTopRanked("DataDownload.xls");
		
		WorkbookClass workbook = new WorkbookClass("DataDownload.xls");
		ArrayList<Sheet> dataSheets = workbook.listDataSheets();
////		dataSheets.forEach(sheet ->{
////		        System.out.println(sheet);
////		    }); 
		Map<String, County> counties = workbook.countyCreator();
//		//Example of getting list of variables for each county.
//		System.out.println(counties.get("56045").stats.keySet());
//		//Example of getting list of values for each county.
//		System.out.println(counties.get("56045").stats.values());
		ArrayList<String> statesNames = workbook.listStates(counties);
		System.out.println(statesNames.size());
		System.out.println(Arrays.toString(statesNames.toArray()));
		Map<String, State> states = workbook.stateCreator(counties);
		System.out.println(states.get("NV").stats.get("CONVS09").getN());
		System.out.println(states.get("NV").stats.get("CONVS09").getMean());
		System.out.println(states.get("NV").stats.get("CONVS09").getMax());
		
		
	}
}
