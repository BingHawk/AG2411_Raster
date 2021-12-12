import ag2411.mapalgebra.*;
import java.awt.*;
import javax.swing.*;

import GUI.components.MapPanel;



public class Ex02 {

    // input: name path/to/data scale valuesOfInterest
    // example: elev ./data/prod/elevation.txt 4
    // example: dev ./data/prod/development.txt 4 1 2 3

    public static void main(String[] args){
        if(args.length > 2){
            //Instantiate alayer
            Layer layer = new Layer(args[0], args[1]);

            //Displaying layer in app 
            JFrame appFrame= new JFrame();
            int scale = Integer.parseInt(args[2]);
            MapPanel map;
            if(args.length >= 4){
                double[] voi = new double[args.length-3];
                for(int i = 3; i<args.length; i++){
                    voi[i - 3] = Double.parseDouble(args[i]);
                }
                map = new MapPanel(layer.toImage(voi),scale);
            } else {
                map = new MapPanel(layer.toImage(),scale);
            }
            Dimension dimension= new Dimension(scale * layer.nCols , scale * layer.nRows);
            map.setPreferredSize(dimension);
    
            appFrame.add(map);
            appFrame.pack();
            appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            appFrame.setVisible(true);


            //Saving it to the file output.txt
        }else{
            System.out.println("Wrong number of arguments");
        }
    }
}
