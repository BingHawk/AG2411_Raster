package ag2411.mapalgebra;

public class Layer {
    public String Name;
    public int nRows; 
    public int nCols;
    public double[] origin = new double[2];
    public double resolution;
    public double[][] values;
    public double nullValue = -9999; 

    public Layer(String name, String path){
        // You may want to do some work before reading a file.
        
        try{
            // Exception may be thrown whilereading (and writing) a file.
            // Get access to the lines of Strings stored in the file
            // Read first line, which starts with "ncols"
            // Read second line, which starts with "nrows"
            // Read third line, which starts with "xllcorner"
            // Read forth line, which starts with "yllcorner"
            // Read fifth line, which starts with "cellsize"
            // Read sixth line, which starts with "NODATA_value"
            // Readeach of the remaining lines, which representsa row of raster
            // values
        } catch(Exception e) {
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
        for(int i = 0; i < nRows; i++) {
            for(int j = 0; j < nCols; j++) {
                System.out.print(values[i*nCols+j]+" ");
            }
            System.out.println();
        }
    };
    public void save(String location){

    };
}
