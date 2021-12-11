package GUI.components;

import ag2411.mapalgebra.*;

import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.App;

public class ToolDialog extends JFrame
                        implements ActionListener {
    private static final String OK = "ok";
    private static final String CANCEL = "cancel";
    private static final String CHOOSE_FILE1 = "choose1";
    private static final String CHOOSE_FILE2 = "choose2";
    private static final String SAVE_LOC = "save";
    private final String OPERATION;
    private JLabel input1;
    private JLabel input2;
    private JLabel outputLoc;
    private JPanel panel;
    private Layer inLayer1;
    private Layer inLayer2;
    private File saveFile;

    public ToolDialog(String operation){
        super();

        OPERATION = operation;
        setTitle(OPERATION);

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        JLabel message = new JLabel("Choose input file(s) for "+operation);
        message.setFont(App.H2);
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(message,c);

        input1 = new JLabel("\nInput file 1: No file chosen");
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(input1,c);

        JButton fileButton1 = new JButton("Choose input file...");
        fileButton1.addActionListener(this);
        fileButton1.setActionCommand(CHOOSE_FILE1);

        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(fileButton1,c);

        if(!OPERATION.equals("slope")){
            input2 = new JLabel("\nInput file 2: No file chosen");

            JButton fileButton2 = new JButton("Choose input file...");
            fileButton2.addActionListener(this);
            fileButton2.setActionCommand(CHOOSE_FILE2);
    
            c.gridwidth = 2;
            c.gridx = 0;
            c.gridy = 2;
            panel.add(input2,c);
            c.gridwidth = 1;
            c.gridx = 2;
            c.gridy = 2;
            panel.add(fileButton2,c);
            
        }

        outputLoc = new JLabel("Output file name: Null, using default");
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(outputLoc,c);

        JButton NameButton = new JButton("Choose output location...");
        NameButton.addActionListener(this);
        NameButton.setActionCommand(SAVE_LOC);
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 3;
        panel.add(NameButton,c);


        JButton okButton = new JButton("OK");
        okButton.addActionListener(this);
        okButton.setActionCommand(OK);

        c.gridwidth = 1;
        //c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 4;
        panel.add(okButton,c);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        cancelButton.setActionCommand(CANCEL);

        c.gridwidth = 1;
        //c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 4;
        panel.add(cancelButton,c);

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
                        //App.dispLayers.add(outLayer);

                        if(saveFile == null){
                            outLayer.save("data/output/"+outName+".txt");
                        } else {
                            outLayer.save(saveFile.getAbsolutePath());
                        }
                    }
                case "zonal min":
                    if(inLayer1 != null && inLayer2 != null){
                        outLayer = inLayer1.zonalMin(inLayer2, outName);
                        //App.dispLayers.add(outLayer);

                        if(saveFile == null){
                            outLayer.save("data/output/"+outName+".txt");
                        } else {
                            outLayer.save(saveFile.getAbsolutePath());
                        }                    }
                }
            
            //App.catalogue.updateCatalogue();

            JFrame window = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, panel);
            window.dispose();

        } else if (cmd.equals(CANCEL)){
            JFrame window = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, panel);
            window.dispose();
        }
        else if(cmd.equals(CHOOSE_FILE1)){
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "ASCII Text raster files only", "txt");
            fc.setFileFilter(filter);
            File currentDir = new File(System.getProperty("user.dir"));
            fc.setCurrentDirectory(currentDir);
    
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
            File currentDir = new File(System.getProperty("user.dir"));
            fc.setCurrentDirectory(currentDir);
    
            int returnVal = fc.showOpenDialog(ToolDialog.this);
            System.out.println(returnVal);

    
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                System.out.println("Opening: " + file.getName() + ".");
    
                String[] name = file.getName().split("[.]", 0);
                inLayer2 = new Layer(name[0],file);

                input2.setText("\nInput file: "+file.getName());

            }
        } else if(cmd.equals(SAVE_LOC)){
            JFileChooser fc = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "ASCII Text raster files only", "txt");
            fc.setFileFilter(filter);
            File currentDir = new File(System.getProperty("user.dir"));
            fc.setCurrentDirectory(currentDir);
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
            int returnVal = fc.showSaveDialog(ToolDialog.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                saveFile = fc.getSelectedFile();
                System.out.println("Saving at: " + saveFile.getName() + ".");

                outputLoc.setText("\nOutput file name: "+saveFile.getName());
            }
        }
    }
}