package GUI;
import javax.swing.*;  
import java.awt.*;

public class GUIDemo {
    public static void main(String[] args) {// Create a JFrame, which will be the main window of this demo application
        JFrame appFrame= new JFrame();
        // Create GUI components
        JButton northButton= new JButton("North");
        JButton southButton= new JButton("South");
        JLabel eastLabel= new JLabel("East");
        JLabel westLabel= new JLabel("West");
        JTextArea centerTextArea= new JTextArea("Center");
        // Set centerTextArea’s background colorto green
        centerTextArea.setBackground(Color.GREEN);
        // Set centerTextArea’spreferred Size to 300 x 200
        Dimension dimension= new Dimension(300 , 200);
        centerTextArea.setPreferredSize(dimension);
        // Make centerTextArea wrap text lines
        centerTextArea.setLineWrap(true);
        // Add the GUI components to app Frame
        appFrame.add(northButton, BorderLayout.PAGE_START);
        appFrame.add(southButton, BorderLayout.PAGE_END);
        appFrame.add(westLabel, BorderLayout.LINE_START);
        appFrame.add(eastLabel, BorderLayout.LINE_END);
        appFrame.add(centerTextArea, BorderLayout.CENTER);
        // Optimize the arrangement the components contained by app Frame
        appFrame.pack();
        // make appFrame visible
        appFrame.setVisible(true);
    }
}
