package GUI.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import ag2411.mapalgebra.*;
import GUI.*;

public class Catalogue extends JToolBar{
    static final public Color BG = new Color(210,210,210);                    


    public Catalogue(){
        super();

        setBackground(BG);
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        updateCatalogue();

    }

    public void updateCatalogue(){

        for(Layer layer: App.dispLayers){
            
        }

    }
    
}
