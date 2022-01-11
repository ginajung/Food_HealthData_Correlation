# Foodies
Foodies is a user-friendly interface allowing the user to explore health outcomes based on food environments and socio-economic factors collected by the FDA on a state level.  

## Data Sources 

USDA Food Atlas: https://catalog.data.gov/dataset/food-environment-atlas-f4a22  
NCHS Leading Cases of Death: https://catalog.data.gov/dataset/age-adjusted-death-rates-for-the-top-10-leading-causes-of-death-united-states-2013  

Unfortunately, neither of these data sources have functional APIs.  
Data was downloaded and is included in this repository. (Folder: Foodies > data)

* DataDownload.xls
* NCHS_Causes_of_Death.csv

## Installation

### Additional Components Required

Included: 
The Apache Commons Mathematics Library (Folder: Foodies > math)  
Apache Commons Lang (Folder: Foodies > commons-lang3-3.9)  
Apache POI (Folder: Foods > poi-4.0.1)  

If any components of either of these libraries cannot be found, start by updating the paths in your IDE.  
An example of how to update these file paths in Eclipse can be found here:  
https://www.tutorialspoint.com/eclipse/eclipse_java_build_path.htm

Installation Required:  
JavaFX  

Eclipse installation guide for JavaFX is available here:   
https://www.eclipse.org/efxclipse/install.html  

## Running the GUI  
Run CompareJavaFXRunner.java within the Foodies repository. 

### Details on the dialog box

First Dropdown Box: All variables present in the USDA 

### Example Screenshots

#### Step 1: Change default values as you desire.
![Step 1: Default values.](/images/ScreenShot1.JPG)
#### Step 2: Hit submit and results box will populate.
![Step 2: Example after pressing submit.](/images/ScreenShot2.JPG)
#### OPTIONAL: You can change state to further explore details. 
![Step 3: You can press submit after changing state.](/images/ScreenShot3.JPG)
#### OPTIONAL: You can change values as much as you want in the same instance.
![Step 4: You can press submit again after changing other variables.](/images/ScreenShot4.JPG)

## Additional Methods Outside of GUI
Not all methods could be incorporated in the GUI. Other manual functions avaible include the following: 

#### Comparison Class
* commonTopRankedState - Returns array of states which overlap between top ten states with highest values of one USDA variable and top ten states with highest values of one NCHS variable. See CompareManualRunner.java for manual example.
#### DeathCodeReader Class
* computeAvgDeath - Returns are hashmap with the average percent of excess death within a category (unqiue combination of year and cause of death). See FactorOfDeathRunner.java for manual run example.
