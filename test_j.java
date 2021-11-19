import ag2411.mapalgebra.*;

public class test_j {
    public static void main(String[] args) {
        Layer testLayer = new Layer("testar","./data/test/raster3x4.txt");

        double[] origin = {0,0};
        Layer constructlayer = new Layer("testlayer", 10, 9, origin, 1.0, -9999);
        constructlayer.print();
        testLayer.print();
        
        // TEST Get nbh
        int i = 1;
        int j = 1;
        int r = 1;
        boolean isSquare = true;
        int[][] nbh  = testLayer.getNeighborhood( i, j, r, isSquare);
        
        int ii = nbh.length;
        int jj = nbh[0].length;
        for (int k = 0; k < ii; k++) {
            System.out.println(nbh[k][0] + " och " + nbh[k][0]);
        }
    }
}
