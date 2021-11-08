package ag2411.mapalgebra;
import java.util.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // class to handle errors

public class Layer {
    public String name;
    public int nRows; 
    public int nCols;
    public double[] origin = new double[2];
    public double resolution;
    public double[][] values;
    public double nullValue; 

    public Layer(String layerName, String path){
        name = layerName;
        try{
            File asciiIn = new File(path);
            Scanner in = new Scanner(asciiIn);
            while(in.hasNextLine() && !in.hasNextDouble()){
                String data = in.nextLine();
                String[] splited = data.split("\\s+");
                switch(splited[0]){
                    case "ncols":
                        try{
                            nCols = Integer.parseInt(splited[1]);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                        }
                    case "nrows":
                        try{
                            nRows = Integer.parseInt(splited[1]);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                        }
                    case "xllcorner":
                        try{
                            origin[0] = Double.parseDouble(splited[1]);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                        }
                    case "yllcorner":
                        try{
                            origin[1] = Double.parseDouble(splited[1]);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                        }
                    case "cellsize":
                        try{
                            resolution = Double.parseDouble(splited[1]);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                        }
                    case "nodata_value":
                        try{
                            nullValue = Double.parseDouble(splited[1]);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                        }
                    }           
            }
            values = new double[nRows][nCols];
            int row = 0;
            while(in.hasNextDouble() && row < nRows){
                int col = 0;
                while(in.hasNextDouble() && col < nCols){
                    values[row][col] = in.nextDouble();
                    col = col+1;
                }
                row = row +1;
            }
            in.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    };
    public void print(){
        //Print this layer to console
        System.out.println("ncols         "+nCols);
        System.out.println("nrows         "+nRows);
        System.out.println("xllcorner     "+origin[0]);
        System.out.println("yllcorner     "+origin[1]);
        System.out.println("cellsize      "+resolution);
        System.out.println("NODATA_value  "+nullValue);
        for(double[] i: values){
            for(double j: i){
                System.out.print(j+" ");
            }
            System.out.println();
        }
    };
    public void save(String location){

    };
}
