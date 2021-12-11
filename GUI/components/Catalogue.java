package GUI.components;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ag2411.mapalgebra.*;
import GUI.*;

public class Catalogue extends JToolBar
                       implements ActionListener{
    static final public Color BG = new Color(210,210,210);  
    //static final public Color BG2 = new Color(10,10,210);  
    static public ButtonGroup bGroup;                  


    public Catalogue(){
        super();

        setBackground(BG);
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        bGroup = new ButtonGroup();

        JLabel header = new JLabel("Catalogue");
        header.setFont(App.H1);
        add(header);
        
        updateCatalogue();

    }

    public void updateCatalogue(){
        for(Layer layer: App.dispLayers){

            if(!buttonInGroup(layer.name, bGroup)){
                JRadioButton rb = new JRadioButton(layer.name);
                rb.setActionCommand(layer.name);
                rb.addActionListener(this);
                bGroup.add(rb);
                add(rb);
            }
            revalidate();

        }
    }

    private boolean buttonInGroup(String ButtonText, ButtonGroup bg){
        boolean out = false;
        for (Enumeration<AbstractButton> buttons = bGroup.getElements(); buttons.hasMoreElements();){
            AbstractButton button = buttons.nextElement();

            if(button.getText().equals(ButtonText)){
                out = true;
            }
        }
        return out;
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        for(Layer l: App.dispLayers){
            if(cmd.equals(l.name)){
                //TODO: Dynamically set scale to make map fill screen
                App.render(l.toImage(),4);
                App.mPanel.setVisible(true);
            }
        }
        
        
    }
    
}
