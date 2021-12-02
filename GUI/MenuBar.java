package GUI;
import javax.swing.*;
import java.awt.*;


public class MenuBar extends JPanel {
    public FlowLayout layout = new FlowLayout(FlowLayout.LEADING);
    public Color bg = new Color(129,129,129);


    public MenuBar(){
        super();

        setLayout(layout);

        setBackground(bg);

        JButton fileButton = createMenueButton("File...");
        JButton toolsButton = createMenueButton("Tools");
        JButton helpButton = createMenueButton("Help");

        add(fileButton);
        add(toolsButton);
        add(helpButton);
    }
    
    private JButton createMenueButton(String name){
        JButton b = new JButton(name);
        return b;
    }
}
