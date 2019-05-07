# Foodies

## Project Idea:

Food environment is a major factor in public health. Characteristics of the food landscape, such as store/restaurant proximity, food prices, and food and nutrition assistance programs, are starting to be systematically captured. The USDA publishes a detailed dataset in Excel format called the Food Environmental Atlas. This project proposes exploring this data for trends between food environments, socio-economic factors and health outcomes.

## Project Goals:

Create a user-friendly model that will predict health outcomes based on food environments and socio-economic factors. The model will include an interactive map with data representing each state in the country. Variables include but are not limited to Socioeconomic characteristics, access proximity to grocery stores, food and nutrition assistance, health and physical activity, food insecurity, grocery store availability, food prices and taxes, prevalence of heart disease, diabetes and infant mortality rates.

## Proposed Classes (Associated Methods): 
County  
State  
Workbook (readWorkbook, printSheet, printSheetNames)   
DescriptiveStatistics (mean, median, standard deviation)   
Analysis (t-test, correlation)   
Visualization (map, corrlation chart)  

Please see CRCCards.docx for more details on brainstorming. 

## Notes On Current State

Note that the reading of the file into java was done two ways.

Circe implemented using the Apache POI library to read in the file. This approach seeks to decrease the amount of hard coding needed to read in files and should be able to capture the entire dataset. Code currently works for test cases, but needs to be improved to fully loop. Once looping is complete, it will be integrated with Gina's solution below. 

descriptiveStatistics.java
neighborhoodData.java

Gina implemented using the variable by variable read in method perviously demonstrated to the class. This code currently functions ok however is limited in scope and does not capture the entire dataset provided by the FDA. Related files: 

CompareRunner.java
CompareTopRanked.java
DeathCode.java	fixed
DeathCodeReader.java
DeathCodeRunner.javao
FoodIndex.java
FoodReader.java
HealthIndex.java
HealthReader.java
Health_Statistic.java
Sum_DeathCode.java

## Task Table:

| Task                                                                                                                                        | Due Date  | Blocker | Leader                    | 
|---------------------------------------------------------------------------------------------------------------------------------------------|-----------|---------|---------------------------| 
| Read in dataset from Excel file.                                                                                                            | 5-Apr-19  | Yes     | Circe                     | 
| Provide descriptive statistics for each variable in data.                                                                                   | 7-Apr-19  | Yes     |                           | 
| Output descriptive statistics in a user friendly format.                                                                                    | 10-Apr-19 | Yes     |                           | 
| Summarized table with key variables                                                                                                         | 12-Apr-19 | No      |                           | 
| Representative graphical comparison between key variables, then graph library will be used.                                                 | 12-Apr-19 | No      |                           | 
| Categorize the variables into group of indicators and create Index (if this makes easier to handle the complicated dataset, if not ignore). | 14-Apr-19 | No      |                           | 
| Create multivariate regression model to identify any factors which are strongly correlated.                                                 | 14-Apr-19 | No      |                           | 
| Design Milestone                                                                                                                            | 14-Apr-19 | No      | Individual Submission     | 
| Final Submission                                                                                                                            | 12-May-19 | No      | Individual Submission (?) | 


## Team Work Breakdown:

Circe - Create multivariate regression model to identify any factors which are strongly correlated. 

Gina -. Provide the two key factors that have the highest correlation with.  Build an algorithm “ commonality/ similarity between top 10 ranked group(county or state based) can be used such as word recommendation!   :

Leonard- Look at spatial factors to see if there are neighborhoods and region effects. Utilize ERS GIS Map Service API to create interactive map for data visualization.    Utilize CDC National Center for Chronic Disease Prevention & Health mapping data. Input data into algorithms to analyze correlation between food environment and health risk factors.

## Short Work Breakdown:

* Read in dataset from Excel file. 
* Provide descriptive statistics for each variable in data.
* Output descriptive statistics in a user friendly format.
  * option 1. Summarized table with key variables
  * option 2. Representative graphical comparison between key variables, then graph library will be used
* Categorize the variables into group of indicators and create Index (if this makes easier to handle the complicated dataset, if not ignore). 
* Create multivariate regression model to identify any factors which are strongly correlated. b 
  * option 1.  linear regression to find correlation: is this machine learning possible in advanced library? we can discuss how realistic the regression modeling is in final project.  
  * option 2. Provide the two key factors that have the highest correlation with.  Algorithm “ commonality/ similarity between * top 10 ranked group(county or state based) can be used such as word recommendation!: (one example for ‘who will do what’ : Gina for build algorithm to compare the two variable’s correlation
 
* Look at spatial factors to see if there are neighborhoods and region effects.
* Utilize ERS GIS Map Service API to create interactive map for data visualization.
* Utilize CDC National Center for Chronic Disease Prevention & Health mapping data
* Input data into algorithms to analyze correlation between food environment and health risk factors 
* Variables include but not limited to Socioeconomic characteristics, access proximity to grocery stores, food and nutrition assistance, health and physical activity, food insecurity
grocery store availability food prices and taxes, prevalence of heart disease, diabetes and infant mortality rates.  (Leonard will build map for model) 

## Stretch Goals: 
* Combine with Infant Death data to see if food environment can predict infant mortality after controlling for other demographic factors. 
* Or dataset related to five leading causes of death (ex. Heart disease) can be compared other than diabetes (metabolic disease) organized by the States and by changes (%) of certain period.  : (another example for ‘who will do what’ : Gina for implementing and analyzing Data by States, to see if there’s any correlation between mortality changes and food environment.)
* Allow users to make selections to explore data themselves (such as searching by certain number of state and show comparison). 
