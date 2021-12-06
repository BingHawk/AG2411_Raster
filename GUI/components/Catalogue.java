package GUI.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import ag2411.mapalgebra.*;
import GUI.*;

public class Catalogue extends JToolBar{
    static final public Color BG = new Color(210,210,210);  
    static final public Color BG2 = new Color(10,10,210);                    


    public Catalogue(){
        super();

        setBackground(BG);
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        JLabel header = new JLabel("Catalogue");
        header.setFont(App.H1);
        add(header);

        //updateCatalogue();

    }

    public void updateCatalogue(){
        //ArrayList<JLabel> labels = new ArrayList<JLabel>();
        System.out.println("Updating catalogue");
        for(Layer layer: App.dispLayers){
            JLabel l = new JLabel(layer.name);
            
            add(l);
            revalidate();
            System.out.println("Adding "+layer.name);
        }
    }
    
}
