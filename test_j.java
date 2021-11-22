import ag2411.mapalgebra.Layer;





public class test_j {
    public static void main(String[] args) {
        Layer testLayer = new Layer("testar","./data/test/raster3x4.txt");

        //double[] origin = {0,0};
        //Layer constructlayer = new Layer("testlayer", 10, 9, origin, 1.0, -9999);
        //constructlayer.print();
        testLayer.print();
        
        // TEST Get nbh
        int i = 0;
        int j = 0;
        int r = 1;
        boolean isSquare = false;
        
        String outLayerName = "Utlager";
        Layer outLayer = testLayer.focalVariety(r, isSquare, outLayerName);
        outLayer.print();



    }
}
