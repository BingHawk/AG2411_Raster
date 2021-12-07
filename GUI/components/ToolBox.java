package GUI.components;

import javax.swing.*;

import GUI.App;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//HOWTO add a new tool to the toolbox:
//1: Add 'static final private String TOOLHANDLE = "toolhandle";' to the proerties of this clas. 
//      note that the variable name TOOLHANDLE should be in caps and the value should be a minor case string. 
//2: Create a button for the tool by writing 'JButton toolName = makeToolButton(TOOLHANDLE, "Tool Name");'
//      "Tool Name" is the string that is displayed on the button. toolName is the variable containing the button
//3: Display the new button by writing 'add(toolName);' under the correct headline. 

public class ToolBox extends JToolBar
                     implements ActionListener{
    static final public Color BG = new Color(210,210,210);                    
    static final private String TOOL1 = "tool1";
    static final private String TOOL2 = "tool2";
    static final private String ZONALTOOL = "zonal1";


    public ToolBox(){
        super();

        setBackground(BG);
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));

        //Create buttons for the tool here
        JButton tool1 = makeToolButton(TOOL1, "Tool 1");
        JButton tool2 = makeToolButton(TOOL2, "Tool 2: longer name");
        JButton tool3 = makeToolButton(ZONALTOOL, "Some Zonal operation");

        JLabel header = new JLabel("Tool box");
        header.setFont(App.H1);
        JLabel headerZonal = new JLabel("==Zonal Operations==");
        headerZonal.setFont(App.H2);
        JLabel headerFocal = new JLabel("==Focal Operations==");
        headerFocal.setFont(App.H2);
        JLabel headerLocal = new JLabel("==Local Operations==");
        headerLocal.setFont(App.H2);
        JLabel headerNetwork = new JLabel("==Network Operations==");
        headerNetwork.setFont(App.H2);


        add(header);
        add(headerZonal);
        //Add zonal tools here
        add(tool1);
        add(tool2);
        add(headerFocal);
        //Add Focal tools here
        add(tool3);
        add(headerLocal);
        //Add Local tools here
        add(headerNetwork);
        //Add network tools here
    }

    protected JButton makeToolButton(String actionCommand, String name) {

        //Create and initialize the button.
        JButton button = new JButton(name);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);

        return button;
}
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        System.out.println("Tool button pressed: "+cmd);
    }
    
}
