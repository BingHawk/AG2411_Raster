package ag2411.mapalgebra;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // class to handle errors
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;
import java.lang.Math;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

public class Layer {
    public String name;
    public int nRows; 
    public int nCols;
    public double[] origin = new double[2];
    public double resolution;
    public double[][] values; //values [row][col]
    public double nullValue; 
    public double[] minMax = { Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY };

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
            while (in.hasNextLine()){
                //Test number of columns in row
                String data = in.nextLine();
                data = data.replace(',','.');
                String[] splited = data.split("\\s+");
                if (splited.length != nCols){
                    System.out.println("ERROR: Metadata does not match data.");
                    System.out.println("\tRow "+row+" has "+splited.length+" columns, "+nCols+" was expected.");
                    System.exit(0);
                }
                int col = 0;
                for (String i: splited){
                    try {
                        values[row][col] = Double.parseDouble(i);
                        if(values[row][col]>minMax[1]){
                            minMax[1] = values[row][col];
                        } else if(values[row][col]<minMax[0]) {
                            minMax[0] = values[row][col];
                        }
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
        for(int i = 0; i < nRows; i++){
            for(int j = 0; j<nCols; j++){
                System.out.print(values[i][j]+" ");
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

    //Greyscale
    public BufferedImage toImage(){
        // Thisobject represents a 24-bit RBG imagewith a widthof 20pixels
        // (corresponding to the number of columns)and a heightof30pixels
        // (corresponding to the number of rows).
        BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB);
        // The above image is empty. To colorthe image, you first need to get access to 
        // itsraster, which is represented by the following object.
        WritableRaster raster = image.getRaster();
        // These statementsmake a grayscale value and assign it to the pixelat the
        // top-left corner of the raster.
        
        double range = minMax[0] - minMax[1];
        if(range == 0){
            range = 1;
        }
        int[] color= new int[3];
        for(int i = 0; i<nRows; i++){
            for(int j = 0; j<nCols; j++){
                int grey = (int) (((255-0)/(minMax[1]-minMax[0]))*(values[i][j]-minMax[1])+255);
                color[0] = grey; // Red
                color[1] = grey; // Green
                color[2] = grey; // Blue
                raster.setPixel(j, i, color);
            }
        }
        return image;
    }
    public BufferedImage toImage(double[] voi) {
        // Thisobject represents a 24-bit RBG imagewith a widthof 20pixels
        // (corresponding to the number of columns)and a heightof30pixels
        // (corresponding to the number of rows).
        BufferedImage image = new BufferedImage(nCols, nRows, BufferedImage.TYPE_INT_RGB);
        // The above image is empty. To colorthe image, you first need to get access to 
        // itsraster, which is represented by the following object.
        WritableRaster raster = image.getRaster();
        // These statementsmake a grayscale value and assign it to the pixelat the
        // top-left corner of the raster.

        double range = minMax[1] - minMax[0];
        if(range == 0){
            range = 1;
        }
       
        //Build map with this runs random colours. 
        Map<Double,int[]> colormap = new HashMap<Double,int[]>();
        for(double i:voi ){
            colormap.put(i,randomColor());
            
        }

        int[] color= new int[3];
        for(int i = 0; i<nRows; i++){
            for(int j = 0; j<nCols; j++){
                if(inArr(voi,values[i][j])){
                    for(int k = 0;k<3;k++){
                        color[k] = colormap.get(values[i][j])[k];
                    } raster.setPixel(j, i, color);
                }
                
            }
        }
        return image;
    }
    private boolean inArr(double[] arr, double x){
        boolean out = false;
        for(double i : arr){
            if(i == x){
                out = true;
            }
        }
        return out;
    }
    private int[] randomColor(){
        int[] color = new int[3];
        for(int i = 0; i<3; i++){
            color[i] = (int) (Math.random() * 255);
            //System.out.println(color[i]);
        }
        return color;
    }
}

