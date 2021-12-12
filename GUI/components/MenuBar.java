package GUI.components;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuBar extends JPanel
                     implements ActionListener{
    static final private FlowLayout LAYOUT = new FlowLayout(FlowLayout.LEADING);
    static final public Color BG = new Color(129,129,129);
    static final private String FILE = "file";
    static final private String SHOW = "show";
    static final private String HELP = "help";
    static final private int HEIGHT = 39;

    public MenuBar(){
        super();

        setLayout(LAYOUT);

        setBackground(BG);

        JButton fileButton = createMenueButton("File...",FILE);
        JButton showButton = createMenueButton("Show",SHOW);
        JButton helpButton = createMenueButton("Help",HELP);

        add(fileButton);
        add(showButton);
        add(helpButton);
    }
    
    private JButton createMenueButton(String name, String action){
        JButton b = new JButton(name);
        b.setActionCommand(action);
        b.addActionListener(this);
        return b;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        JPopupMenu fileMenu = new FileMenu();
        JPopupMenu showMenu = new ShowMenu();
 
        // Handle each button.
        if (FILE.equals(cmd)) { //first button clicked
            add(fileMenu);
            fileMenu.show(this, 0, HEIGHT);

            //System.out.println("Action triggered: "+cmd);

        } else if (SHOW.equals(cmd)) { // second button clicked
            add(showMenu);
            showMenu.show(this, 90, HEIGHT);

            //System.out.println("Action triggered: "+cmd);

        } else if (HELP.equals(cmd)) { // third button clicked
            //System.out.println("Action triggered: "+cmd);

        }
 
    }
}
