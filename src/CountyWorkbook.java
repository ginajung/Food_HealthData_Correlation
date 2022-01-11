// DESCRIPTION: Import USDA data workbook into state and county classes.
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.poi.EncryptedDocumentException;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CountyWorkbook {
	Workbook myWorkbook;
	
	//METHOD: Open excel file as Workbook class.
	public CountyWorkbook(String filePath) throws EncryptedDocumentException, IOException {
		myWorkbook = WorkbookFactory.create(new File(filePath));
	}
   
	//METHOD: Extract human readable variable names from the variable list sheet.
	public Map<String, String> extractHumanReadableVariableNames(){
		Sheet sheet1 = myWorkbook.getSheet("Variable List");
		Map<String, String> variableNamesMaps = new HashMap<String, String>();
		//For each row, get key(computer readable names consistent in worksheets) and value (human readable names only present in variable list).
		sheet1.forEach( row -> {
		    if (row.getRowNum() != 0){
				String key = row.getCell(4).getStringCellValue();
				String value = row.getCell(3).getStringCellValue();
				variableNamesMaps.put(key, value);
		    }
		});
		return variableNamesMaps;
	}
	
    // METHOD: Print each cell in an sheet from a workbook object.  
    public void printSheet(Sheet sheet){
        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();
        //Print out rows and columns using forEach.
        sheet.forEach(row -> {
            row.forEach(cell -> {
                String cellValue = dataFormatter.formatCellValue(cell);
                System.out.print(cellValue + "\t");
            });
            System.out.println();
        });
    }

    //METHOD: Print out sheet names of workbook.
    public void printSheetNames(){
    	myWorkbook.forEach(sheet -> {
            System.out.println("=> " + sheet.getSheetName());
        });
    }
    
    //METHOD: Extracts rows as counties from all data sheets.
    public Map<String, County> countyCreator() {
    	Map<String, County> counties = new HashMap<String, County>();
    	myWorkbook.forEach(sheet -> {
    		if (isDataSheet(sheet)) {
    			ArrayList<String> columnNames = new ArrayList<>();
    			sheet.forEach(row -> {
    				//Get names of columns as an ArrayList.
				    if (row.getRowNum() == 0){
				    	for (int i=0; i<row.getPhysicalNumberOfCells(); i++) {
		    				String columnName = row.getCell(i).getStringCellValue();
		    				columnNames.add(columnName);
	    				}
				    //If not first row, add to county stats Map.
					} else {
						//Create and populate county.
	    				County tempCounty = new County(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue(), row.getCell(2).getStringCellValue());
	    				for (int i=3; i<row.getPhysicalNumberOfCells(); i++) {
		    				Double number = row.getCell(i).getNumericCellValue();
		    				tempCounty.stats.put(columnNames.get(i), number);
	    				}
	    				//If county already exists, add every map value to existing county. 
	    				if (counties.containsKey(tempCounty.FIPS)) {
	    					County existingCounty = counties.get(tempCounty.FIPS);
							for (Map.Entry<String, Double> item : tempCounty.stats.entrySet()) {
							    String key = item.getKey();
							    Double value = item.getValue();
							    existingCounty.stats.put(key, value);
							}
						//If county does NOT already exist, store new county.
	    				} else {
	    					counties.put(tempCounty.FIPS, tempCounty);
	    				}
					}
    			});
    		}
    	});
     return counties;
    }
    
    //METHOD: Aggregates states together 
    public Map<String, State> stateCreator(Map<String,County> counties){
    	ArrayList<String> statesNames = listStates(counties);
    	Map<String, State> states = new HashMap<String, State>();
    	//Initialize relevant variables.
    	String[] statistics = counties.entrySet().stream().findAny().get().getValue().stats.keySet().toArray(new String[counties.entrySet().stream().findAny().get().getValue().stats.size()]);
    	//For each statistic, create empty descriptive statistics object.
    	State tempState;
    	for(String state : statesNames) {
    		tempState = new State(state);
    		states.put(state, tempState);
    		for(String stat :statistics) {
				DescriptiveStatistics tempDStats = new DescriptiveStatistics();
	    		//Create new state class object.
	    		counties.entrySet().stream().forEach(county -> {
	    			if (county.getValue().State.toString().equalsIgnoreCase(state)) {
	    				Double tempVal = county.getValue().stats.get(stat).doubleValue();
	    				tempDStats.addValue(tempVal);
	    			}
	    		
	    		});
	    		tempState.stats.put(stat, tempDStats);
	    	}
    	}
    	return states;
    }
    
    //METHOD: Get list of states from county classes.
    public ArrayList<String> listStates(Map<String,County> counties){
    	ArrayList<String> stateNameList = new ArrayList<String>();
    	// For each county, add state is state not already present.
    	counties.entrySet().stream().forEach(county -> {
    		String tempState = county.getValue().State.toString();
    		if (!stateNameList.contains(tempState)) {
    			stateNameList.add(tempState);
    			}
    		}
    	);
    	return stateNameList;
    }

    // Determine if sheet is descriptive or holds data. 
    // Data sheets always start with the same three columns.
    public Boolean isDataSheet(Sheet sheet){
    	return (sheet.getRow(0).getCell(0).getStringCellValue().equals("FIPS") && 
        		sheet.getRow(0).getCell(1).getStringCellValue().equals("State") &&
        		sheet.getRow(0).getCell(2).getStringCellValue().equals("County"));
    }
   
    //List all data sheets.
    public ArrayList<Sheet> listDataSheets(){
    	ArrayList<Sheet> dataSheets = new ArrayList<>();
    	myWorkbook.forEach( sheet -> {
    		//System.out.println(sheet.getRow(0).getCell(0).getStringCellValue().equals("FIPS"));
        	if (sheet.getRow(0).getCell(0).getStringCellValue().equals("FIPS") && 
        		sheet.getRow(0).getCell(1).getStringCellValue().equals("State") &&
        		sheet.getRow(0).getCell(2).getStringCellValue().equals("County")) {
        		dataSheets.add(sheet);
	    	}
	    });
    	return dataSheets;
    }
}
