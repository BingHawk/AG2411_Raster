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
import java.util.ArrayList;
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
    
    public Layer(String name, int nRows, int nCols, double[] origin, double resolution, double nullValue) {
        // construct a new layer by assigning a value to each of its attributes
        this.name= name;// on the left hand side are the attributes of
        this.nRows= nRows;// the new layer;
        this.nCols= nCols;
        this.origin= origin;
        this.resolution= resolution;
        this.nullValue=nullValue;
        this.values = new double [nRows][nCols];
		// on the right hand side are the parameters.// to be continued...}
	}

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

    public Layer localSum(Layer inLayer, String outLayerName){
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin,
        resolution, nullValue);

        // Check that resolution are the same.
        if(nRows != inLayer.nRows || nCols != inLayer.nCols || resolution != inLayer.resolution){
            System.out.println("Columns, Rows or resolution does not match");
            System.exit(0);
        }

        for (int i = 0; i < nRows; i++) { 
            for (int j = 0; j < nCols; j++) {
                outLayer.values[i][j] = values[i][j] + inLayer.values[i][j];
                
                // set cell in outlayer to nullvalue if any of the inlayers include nullvalue.
                if (values[i][j] == nullValue || inLayer.values[i][j] == nullValue)
                    outLayer.values[i][j] = nullValue;
            }
        }
        return outLayer;
    }

    public Layer localDifference(Layer inLayer, String outLayerName){
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin,
        resolution, nullValue);

        // Check that resolution are the same.
        if(nRows != inLayer.nRows || nCols != inLayer.nCols || resolution != inLayer.resolution){
            System.out.println("Columns, Rows or resolution does not match");
            System.exit(0);
        }

        for (int i = 0; i < nRows; i++) { 
            for (int j = 0; j < nCols; j++) {
                outLayer.values[i][j] = values[i][j] - inLayer.values[i][j];
                
                // set cell in outlayer to nullvalue if any of the inlayers include nullvalue.
                if (values[i][j] == nullValue || inLayer.values[i][j] == nullValue)
                    outLayer.values[i][j] = nullValue;
            }
        }
        return outLayer;
    }

    public Layer localProduct(Layer inLayer, String outLayerName){
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin,
        resolution, nullValue);

        // Check that resolution are the same.
        if(nRows != inLayer.nRows || nCols != inLayer.nCols || resolution != inLayer.resolution){
            System.out.println("Columns, Rows or resolution does not match");
            System.exit(0);
        }

        for (int i = 0; i < nRows; i++) { 
            for (int j = 0; j < nCols; j++) {
                outLayer.values[i][j] = values[i][j] * inLayer.values[i][j];
                
                // set cell in outlayer to nullvalue if any of the inlayers include nullvalue.
                if (values[i][j] == nullValue || inLayer.values[i][j] == nullValue)
                    outLayer.values[i][j] = nullValue;
            }
        }
        return outLayer;
    }

    public Layer localDivision(Layer inLayer, String outLayerName){
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin,
        resolution, nullValue);

        // Check that resolution are the same.
        if(nRows != inLayer.nRows || nCols != inLayer.nCols || resolution != inLayer.resolution){
            System.out.println("Columns, Rows or resolution does not match");
            System.exit(0);
        }

        for (int i = 0; i < nRows; i++) { 
            for (int j = 0; j < nCols; j++) {
                outLayer.values[i][j] = values[i][j] / inLayer.values[i][j];
                
                // set cell in outlayer to nullvalue if any of the inlayers include nullvalue.
                if (values[i][j] == nullValue || inLayer.values[i][j] == nullValue)
                    outLayer.values[i][j] = nullValue;
            }
        }
        return outLayer;
    }

    // Ska va private efter testning
    private int[][] getNeighborhood(int i, int j, int r, boolean isSquare) {


        ArrayList<int[]> nbh = new ArrayList<int[]>();

        // Ta fram kvadrat. ---> Lägg till undantagsfall: EDGES <---

        for (int k = i-r; k <= i+r; k++) { 
            for (int l = j-r; l <= j+r; l++) {

                if (k>=0 && l>=0 && k<nRows && l<nCols) {
                // Om vi vill ha cirkel tar vi bort hörnen.
                    if (isSquare == false) {
                        if ((k-i)*(k-i) + (l-j)*(l-j) <= r*r) {
                            int[] indexPair = new int[2]; 
                            indexPair[0] = k;
                            indexPair[1] = l;
                            nbh.add(indexPair);
                        }
                    }
                    else {
                        int[] indexPair = new int[2]; 
                        indexPair[0] = k;
                        indexPair[1] = l;
                        nbh.add(indexPair);
                    }
                }
            }
        }

        int n = nbh.size();
        int [][] nbh2= new int [n][2];

        for (int index =0; index <n; index++) { 
            nbh2 [index] = nbh.get(index);
        }

        return nbh2;
    }

    public Layer zonalMinimum(Layer zoneLayer, String outLayerName) {
    //Test that dimensions match
        if(nRows != zoneLayer.nRows || nCols != zoneLayer.nCols || resolution != zoneLayer.resolution){
            System.out.println("Columns, Rows or resolution does not match");
            System.exit(0);
        } 

        //Creating and Pupulating a hashmap with lowest value for each zone. Key: zone, Value: lowest value in zone
        //if a there is a nullValue in the zone, it will return the lowest value of the other values in same zone
        HashMap<Double, Double> smallest = new HashMap<Double, Double>();
        for(int i = 0; i< nRows; i++){
            for (int j = 0;j<nCols; j++){
                if ((values[i][j] != nullValue) && (zoneLayer.values[i][j] != zoneLayer.nullValue)){
                    //System.out.println(zoneLayer.values[i][j]+" "+zoneLayer.nullValue);

                    if(smallest.containsKey(zoneLayer.values[i][j])){
                        if(values[i][j] < smallest.get(zoneLayer.values[i][j])){
                            smallest.put(zoneLayer.values[i][j], values[i][j]);
                        }
                    } else {
                        smallest.put(zoneLayer.values[i][j], values[i][j]);
                    }
                } else if(zoneLayer.values[i][j] == zoneLayer.nullValue){
                    System.out.println("nullValue detected");
                    smallest.put(nullValue,nullValue); //adding NullValue to pixels that has no zone
                }
            }

        }

        //writing values to new layer
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);
        System.out.println(smallest);
        for(int i = 0; i< nRows; i++){
            for (int j = 0;j<nCols; j++){
                //System.out.println(zoneLayer.values[i][j]);
                outLayer.values[i][j] = smallest.get((double) zoneLayer.values[i][j]);
            }
        }
        return outLayer;

    }

    // FOCAL VARIETY - Returns number indicating variety in neighborhood of specified size
    public Layer focalVariety (int r, boolean IsSquare, String outLayerName) {
        
        Layer outLayer = new Layer(outLayerName, nRows, nCols, origin, resolution, nullValue);

        for (int i = 0; i < nRows; i++) { 
            for (int j = 0; j < nCols; j++) {
                int [][] nbh = getNeighborhood (i, j, r, IsSquare);
                HashMap<Integer, Double> unik = new HashMap<Integer, Double>();
                int len = nbh.length;
                //System.out.println("length: "+len);
                int counter = 0;
                for (int k = 0; k < len; k++) {	// 9 should be len, but not working
                    int a=nbh[k][0];
                    int b=nbh[k][1];
                    

                    if (!unik.containsValue(values[a][b])) {
                        counter = counter + 1;
                        unik.put(counter, values[a][b]);
                        //System.out.println("COUNT: "+counter);
                        outLayer.values[i][j]=counter;
                    }
                    
                    
                }
                
            }
        }
        return outLayer;
    }
}


