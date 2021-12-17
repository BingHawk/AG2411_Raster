# AG2411_Raster
 Repo for Ex01 in AG2411, 

## Structure: 
ag2411/mapalgebra contains the Layer calss and its methods. 
All mapalgebra function should be saved in this package

package data contains test and production data in ASCII format
test contains small test data and prod contains the data given in the task. 

Task can be found here: Ex01.pdf

Ex01.java contains code to run and test the Layer class. 

## Assumptions: 

Location and reference system does not matter. 
All input files are of exactly the same study area. 

## Testrun: 
1. compile the constructor in terminal with: 
$ javac ag2411/mapalgebra/Layer.java
Powershell: $ javac ag2411\mapalgebra\Layer.java
2. compile testcode with
$ javac test.java
3. run with 
$ java test.java

Test will use raster3x4.txt.

## Project:
To test the program, compile all and run the App.java program. 

### To run the Raster.Engine_0.1.jar file
1. Open a command prompt
2. Navigate to the folder where you have saved the jar file in the file explorer/file system in the command prompt
3. Type the following command: java -jar --enable-preview Raster.Engine_0.1.jar


### Documents:
Project Notes: https://hexmet-my.sharepoint.com/:w:/r/personal/balint_reger_leica-geosystems_com/_layouts/15/Doc.aspx?sourcedoc=%7Bfbaeac4d-65b8-4504-b8f4-e88d51059665%7D&action=default&cid=15be940c-6a68-40f3-87d2-353b0fafdff3

### Sources: 
ToolBar: https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ToolBarDemoProject/src/components/ToolBarDemo.java