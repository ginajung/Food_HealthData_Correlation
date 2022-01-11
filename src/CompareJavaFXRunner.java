// DESCRIPTION: Creates JavaFX based GUI to display Pearson Correlation results for specific variables on a state level.  

import java.util.Arrays;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import javafx.application.Application; 
import javafx.scene.Scene; 
import javafx.scene.control.*; 
import javafx.scene.layout.*; 
import javafx.event.ActionEvent; 
import javafx.event.EventHandler;
import javafx.collections.*; 
import javafx.stage.Stage; 

public class CompareJavaFXRunner extends Application { 
	
	//Create a 2D array matrix for use with the Pearson Correlation class.
	public RealMatrix arrayToMatrix(double[] x, double[] y){
		RealMatrix matrixOut =  new Array2DRowRealMatrix(x.length, 2);
		for (int i = 0; i < x.length; ++i) {
			matrixOut.setEntry(i, 0, x[i]);
			matrixOut.setEntry(i, 1, y[i]);
		}
		return matrixOut;
	}
	
	// Launch the application 
	public void start(Stage stage) throws Exception { 
		// Set title for the stage 
		stage.setTitle("Food Data Correlation with Health Outcome Explorer"); 

		//Create comparison table.
		Comparison test = new Comparison("data/DataDownload.xls");
		DeathCodeReader dcr = new DeathCodeReader("data/NCHS_Causes_of_Death.csv");
		
		// Get list of food variable names values.
		String foodVariables[] = test.variableNames.values().toArray(new String[test.variableNames.size()]);
		Arrays.sort(foodVariables);
		
		// Create a combo box for food variables.
		ComboBox<String> combo_box_foodVariables = new ComboBox<String>(FXCollections.observableArrayList(foodVariables));
		combo_box_foodVariables.getSelectionModel().selectFirst();
		
		// Get list of state variable names values.
		String stateNames[] = test.states.keySet().toArray(new String[test.states.size()]);
		Arrays.sort(stateNames);
		
		// Create a combo box for state variables.
		ComboBox<String> combo_box_states = new ComboBox<String>(FXCollections.observableArrayList(stateNames));
		combo_box_states.getSelectionModel().selectFirst();

		// Get list of death years.
		String deathYears[] = dcr.listDeathYears().toArray(new String[dcr.listDeathYears().size()]);
		Arrays.sort(deathYears);
		
		// Create a combo box for death years.
		ComboBox<String> combo_box_deathYears = new ComboBox<String>(FXCollections.observableArrayList(deathYears));
		combo_box_deathYears.getSelectionModel().selectFirst();
		
		// Get list of death types.
		String deathTypes[] = dcr.listCauseDeath().toArray(new String[dcr.listCauseDeath().size()]);
		Arrays.sort(deathTypes);

		// Create a combo box for death types.
		ComboBox<String> combo_box_deathTypes = new ComboBox<String>(FXCollections.observableArrayList(deathTypes));
		combo_box_deathTypes.getSelectionModel().selectFirst();

		// Create label space holder for answer test box.
		Label answer = new Label("Pearson's correlation:\nP Value:\nNumber of States:\nTop States:\nBottom States:\nState details for\n");
		answer.setStyle("-fx-border-color: #000; -fx-padding: 5px;");
			
		// Create action event to run once submit button is pressed. 
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>(){ 
			public void handle(ActionEvent e) { 
				// Get value for statistic of interest from USDA dataset.
				String selectedFoodValue = combo_box_foodVariables.getValue().toString();
				String selectedFoodKey = "Key Not Found";
				// Look up key from selected human readable variable.
				for (String key : test.variableNames.keySet()){
				    if (selectedFoodValue.equals(test.variableNames.get(key))){
				    	selectedFoodKey = key;
				    }
				}
				
				// Get values based off of user inputs.
				double[] food_variable_value = test.getValuesArrayforVariable(test.stateValuesMapForVariable(selectedFoodKey));
				double[] death_variable_value = test.getValuesArrayforVariable(dcr.computeAvgDeath(combo_box_deathYears.getValue().toString(), combo_box_deathTypes.getValue().toString()));
				
				// Calculate correlations.
				RealMatrix testMatrix = arrayToMatrix(food_variable_value, death_variable_value);
				PearsonsCorrelation correlation = new PearsonsCorrelation(testMatrix);
				
				// Formulate string to display results to user.
				answer.setText("Pearson's correlation: "+ correlation.getCorrelationMatrix().getEntry(0, 1)+"\n"+
								"P Value: " + correlation.getCorrelationPValues().getEntry(0, 1)+"\n"+
								"Number of States: " + food_variable_value.length+"\n\n"+
							    "Top States: " + test.topRankedState(test.stateValuesMapForVariable(selectedFoodKey)).keySet().toString()+"\n"+
							    "Bottom States: " + test.bottomRankedState(test.stateValuesMapForVariable(selectedFoodKey)).keySet().toString()+"\n\n"+
							    "State ("+combo_box_states.getValue().toString()+") details for " + selectedFoodValue+"\n"+
								test.stateStringStats(combo_box_states.getValue().toString(), selectedFoodKey));
			} 
		};

		//Create submit button
		Button submit = new Button("Submit");
		submit.setOnAction(event);

		// Create a tile pane 
		FlowPane tile_pane = new FlowPane(combo_box_foodVariables,
										  combo_box_states,
										  combo_box_deathTypes,
										  combo_box_deathYears, 
										  submit, 
										  answer);
		tile_pane.setHgap(20);
		tile_pane.setVgap(20);
		
		// Create a scene 
		Scene scene = new Scene(tile_pane, 1000, 750);

		// Set the scene 
		stage.setScene(scene); 
		stage.show(); 
	} 

	// Start running JavaFX GUI.
	public static void main(String args[]) 
	{ 
		// Launch the application 
		launch(args); 
	} 
} 
