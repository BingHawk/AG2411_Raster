package ag2411.mapalgebra;

public class Layer {
    public String Name;
    public int nRows; 
    public int nCols;
    public double[] origin = new double[2];
    public double resolution;
    public double[] values[];
    public double nullValue = -9999; 

    public Layer(String name, String path){

    };
    public void print(){

    };
    public void save(String location){

    };
}
