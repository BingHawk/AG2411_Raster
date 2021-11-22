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
