package ag2411.mapalgebra;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // class to handle errors
import java.io.IOException;

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
            Pattern pattern = Pattern.compile("[A-Za-z_]*");

            while(in.hasNextLine() && in.hasNext(pattern) ){
                String data = in.nextLine();
                String[] splited = data.split("\\s+");
                switch(splited[0]){
                    case "ncols":
                        try{
                            nCols = Integer.parseInt(splited[1]);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                            System.out.println("ERROR: Wrong format in metadata: "+splited[0]);
                            System.exit(0);
                        }
                    case "nrows":
                        try{
                            nRows = Integer.parseInt(splited[1]);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                            System.out.println("ERROR: Wrong format in metadata: "+splited[0]);
                            System.exit(0);
                        }
                    case "xllcorner":
                        try{
                            origin[0] = Double.parseDouble(splited[1]);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                            System.out.println("ERROR: Wrong format in metadata: "+splited[0]);
                            System.exit(0);
                        }
                    case "yllcorner":
                        try{
                            origin[1] = Double.parseDouble(splited[1]);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                            System.out.println("ERROR: Wrong format in metadata: "+splited[0]);
                            System.exit(0);
                        }
                    case "cellsize":
                        try{
                            resolution = Double.parseDouble(splited[1]);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                            System.out.println("ERROR: Wrong format in metadata: "+splited[0]);
                            System.exit(0);
                        }
                    case "NODATA_value":
                        try{
                            nullValue = Double.parseDouble(splited[1]);
                        }
                        catch (NumberFormatException ex){
                            ex.printStackTrace();
                            System.out.println("ERROR: Wrong format in metadata: "+splited[0]);
                            System.exit(0);
                        }
                    }           
            }
            
            values = new double[nRows][nCols];
            int row = 0;
            while(in.hasNextLine()){
                //Test number of columns in row
                String data = in.nextLine();
                data = data.replace(',','.');
                String[] splited = data.split("\\s+");
                if(splited.length != nCols){
                    System.out.println("ERROR: Metadata does not match data.");
                    System.out.println("\tRow "+row+" has "+splited.length+" columns, "+nCols+" was expected.");
                    System.exit(0);
                }
                int col = 0;
                for(String i: splited){
                    try{
                        values[row][col] = Double.parseDouble(i);
                        col = col +1;
                    } 
                    catch(ArrayIndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                    catch(NumberFormatException e){
                        System.out.println("ERROR: Wrong format in data: "+i);
                        System.exit(0);
                    }                            

                }
                row = row +1;
            }
            if(row != nRows){
                System.out.println("ERROR: Metadata does not match data.");
                System.out.println("\tdata has " +row+" rows, "+nRows+" was expected.");
                System.exit(0);
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
        try {
            File writeFile = new File(location);
            if (writeFile.createNewFile()) {
              System.out.println("File created: " + writeFile.getName());
            } else {
              System.out.println("File already exists. Owerwriting existing file");
            }
            FileWriter writer = new FileWriter(location);
            writer.write("ncols         "+nCols+"\n");
            writer.write("nrows         "+nRows+"\n");
            writer.write("xllcorner     "+origin[0]+"\n");
            writer.write("yllcorner     "+origin[1]+"\n");
            writer.write("cellsize      "+resolution+"\n");
            writer.write("NODATA_value  "+nullValue+"\n");
            for(double[] i: values){
                for(double j: i){
                    writer.write(j+" ");
                }
                writer.write("\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}
