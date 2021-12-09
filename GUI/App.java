package GUI;
import ag2411.mapalgebra.*;
import ag2411.network.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

import GUI.components.Catalogue;
import GUI.components.MenuBar;
import GUI.components.ToolBox;
import GUI.components.MapPanel;

import java.awt.*;
import java.util.ArrayList;


public class App extends JFrame{
    public static ArrayList<Layer> dispLayers;
    public static ArrayList<Network> dispNetworks;
    public static Catalogue catalogue;
    public static MapPanel mPanel;
    public static final Font H1 = new Font("Sans", Font.BOLD, 16);
    public static final Font H2 = new Font("Sans", Font.BOLD, 14);
    public static App app;

    public App(){
        super();

        dispLayers  = new ArrayList<Layer>();
        dispNetworks = new ArrayList<Network>();

        Layer testLayer1 = new Layer("Elevation","/Users/leonard/Documents/GitHub/AG2411_Raster/data/prod/elevation.txt");
        Layer testLayer2 = new Layer("Development","/Users/leonard/Documents/GitHub/AG2411_Raster/data/prod/development.txt");

        dispLayers.add(testLayer1);
        dispLayers.add(testLayer2);
        
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

    //Renders an image in APP:
    public static void render(BufferedImage image, int scale){
        System.out.println("render triggered");
        mPanel = new MapPanel(image, scale);

        app.add(mPanel);
        app.revalidate();
    }
    

    public static void main(String[] args){
        app = new App();

        app.pack();

        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        app.setVisible(true);

        //Produce a window. Wait for user action
    }
}

