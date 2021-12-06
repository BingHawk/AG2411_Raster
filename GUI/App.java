package GUI;
import ag2411.mapalgebra.*;
import ag2411.network.*;
import javax.swing.*;

import GUI.components.Catalogue;
import GUI.components.MenuBar;
import GUI.components.ToolBox;

import java.awt.*;
import java.util.ArrayList;


public class App extends JFrame{
    public static ArrayList<Layer> dispLayers = new ArrayList<Layer>();
    public static ArrayList<Network> dispNetworks;
    public static Catalogue catalogue;
    public static final Font H1 = new Font("Sans", Font.BOLD, 16);
    public static final Font H2 = new Font("Sans", Font.BOLD, 14);
    
    public App(){
        super();

        //dispLayers = 
        dispNetworks = new ArrayList<Network>();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight(); 
        Dimension dimension= new Dimension(screenWidth - 100,screenHeight - 100);
        setPreferredSize(dimension);

        JPanel menuBar = new MenuBar();
        add(menuBar,BorderLayout.NORTH);

        JToolBar toolbox = new ToolBox();
        add(toolbox, BorderLayout.LINE_END);

        catalogue = new Catalogue();
        add(catalogue, BorderLayout.LINE_START);

    }

    public static void main(String[] args){
        App app = new App();

        app.pack();

        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        app.setVisible(true);

        //Produce a window. Wait for user action
    }
}

