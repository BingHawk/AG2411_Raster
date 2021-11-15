import ag2411.mapalgebra.*;
import javax.swing.*;
import java.awt.*;

public class test {
    public static void main(String[] args) {
        Layer testLayer = new Layer("testar","./data/test/raster3x4.txt");
        testLayer.print();

        JFrame appFrame= new JFrame();
        int scale = 100;
        double[] voi = {10,20};
        MapPanel map = new MapPanel(testLayer.toImage(voi),scale);

        Dimension dimension= new Dimension(scale * testLayer.nCols , scale * testLayer.nRows);
        map.setPreferredSize(dimension);

        appFrame.add(map);
        appFrame.pack();
        appFrame.setVisible(true);

        testLayer.save("./data/output/test.txt");
    }
    
}
