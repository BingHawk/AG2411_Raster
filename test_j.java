import ag2411.mapalgebra.Layer;





public class test_j {
    public static void main(String[] args) {
        Layer testLayer = new Layer("testar","./data/test/raster3x4.txt");

        //double[] origin = {0,0};
        //Layer constructlayer = new Layer("testlayer", 10, 9, origin, 1.0, -9999);
        //constructlayer.print();
        testLayer.print();
        
        // TEST Get nbh OBS, getNeighborhood has to be made public before testing
        int i = 1;
        int j = 1;
        int r = 1;
        boolean isSquare = false;
        //int[][] nbh = testLayer.getNeighborhood(i, j, r, isSquare);
        isSquare = false;
        //int[][] nbhCircle = testLayer.getNeighborhood(i, j, r, isSquare);

        for (int[] val:nbh){
            for (int index: val){
                System.out.print(index+", ");
            }
            System.out.println();
        }
        System.out.println();
        for (int[] val:nbhCircle){
            for (int index: val){
                System.out.print(index+", ");
            }
            System.out.println();
        }
        
        String outLayerName = "Utlager";
        Layer outLayer = testLayer.focalVariety(r, isSquare, outLayerName);
        outLayer.print();



    }
}
