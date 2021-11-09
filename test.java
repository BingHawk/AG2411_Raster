import ag2411.mapalgebra.Layer;
public class test {
    public static void main(String[] args) {
        Layer testLayer = new Layer("testar","./data/test/raster3x4.txt");
        testLayer.print();
        testLayer.save("./data/output/test.txt");
    }
    
}
