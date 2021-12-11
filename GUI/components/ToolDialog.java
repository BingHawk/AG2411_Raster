package GUI.components;

import ag2411.mapalgebra.*;

import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.BoxLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.App;

public class ToolDialog extends JFrame
                        implements ActionListener {
    private static final String OK = "ok";
    private static final String CHOOSE_FILE1 = "choose1";
    private static final String CHOOSE_FILE2 = "choose2";
    private final String OPERATION;
    private JLabel input1;
    private JLabel input2;
    private JPanel panel;
    private Layer inLayer1;
    private Layer inLayer2;

    public ToolDialog(String operation){
        super();

        OPERATION = operation;
        setTitle(OPERATION);

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel message = new JLabel("Choose input file(s) for "+operation);
        message.setFont(App.H2);

        input1 = new JLabel("\nInput file 1: No file chosen");

        JButton fileButton1 = new JButton("Choose input file...");
        fileButton1.addActionListener(this);
        fileButton1.setActionCommand(CHOOSE_FILE1);

        panel.add(input1);
        panel.add(fileButton1);

        if(!OPERATION.equals("slope")){
            input2 = new JLabel("\nInput file 2: No file chosen");

            JButton fileButton2 = new JButton("Choose input file...");
            fileButton2.addActionListener(this);
            fileButton2.setActionCommand(CHOOSE_FILE2);
    
            panel.add(input2);
            panel.add(fileButton2);
        }

        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(this);
        okButton.setActionCommand(OK);

        panel.add(message);

        panel.add(okButton);

        add(panel);
        pack();

        


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        
        if(cmd.equals(OK)){
            System.out.println("ok is pressed");

            String outName = inLayer1.name + "_" + OPERATION;
            Layer outLayer;
            switch(OPERATION){
                case "slope": 
                    if(inLayer1 != null){
                        outLayer = inLayer1.focalSlope(outName);
                        App.dispLayers.add(outLayer);
                        App.catalogue.updateCatalogue();
                        outLayer.save("data/output/testoutput.txt");
                    }
                case "zonal min":
                    if(inLayer1 != null && inLayer2 != null){
                        outLayer = inLayer1.zonalMin(inLayer2, outName);
                        App.dispLayers.add(outLayer);
                        App.catalogue.updateCatalogue();
                        outLayer.save("data/output/testoutput.txt");
                    }
                }


        } else if(cmd.equals(CHOOSE_FILE1)){
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "ASCII Text raster files only", "txt");
            fc.setFileFilter(filter);
    
            int returnVal = fc.showOpenDialog(ToolDialog.this);
            System.out.println(returnVal);

    
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                System.out.println("Opening: " + file.getName() + ".");
    
                String[] name = file.getName().split("[.]", 0);
                inLayer1 = new Layer(name[0],file);

                input1.setText("\nInput file: "+file.getName());

            }
        } else if(cmd.equals(CHOOSE_FILE2)){
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "ASCII Text raster files only", "txt");
            fc.setFileFilter(filter);
    
            int returnVal = fc.showOpenDialog(ToolDialog.this);
            System.out.println(returnVal);

    
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                System.out.println("Opening: " + file.getName() + ".");
    
                String[] name = file.getName().split("[.]", 0);
                inLayer2 = new Layer(name[0],file);

                input2.setText("\nInput file: "+file.getName());

            }
        }
    }
}