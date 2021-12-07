import ag2411.mapalgebra.*;
import javax.swing.*;

import GUI.components.MapPanel;

import java.awt.*;

public class test {
    public static void main(String[] args) {
        Layer testLayer = new Layer("testar","./data/test/raster3x4.txt");
        Layer zoneLayer = new Layer("zones","./data/test/testZones1.txt");

        Layer zoneSumLayer = testLayer.zonalMin(zoneLayer, "zoneSum");

        testLayer.print();
        zoneSumLayer.print();

        JFrame appFrame= new JFrame();
        int scale = 100;
        //double[] voi = {10,20};
        MapPanel map = new MapPanel(zoneSumLayer.toImage(),scale);

        Dimension dimension= new Dimension(scale * zoneSumLayer.nCols , scale * zoneSumLayer.nRows);
        map.setPreferredSize(dimension);

        appFrame.add(map);
        appFrame.pack();
        appFrame.setVisible(true);

        testLayer.save("./data/output/test.txt");
    }
    
}
