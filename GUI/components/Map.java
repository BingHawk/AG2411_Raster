package GUI.components;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Map extends JPanel{
    private static final Color BG = new Color(200,200,210);
    public MapPanel mPanel;
    
    public Map(){
        super();
        setBackground(BG);
    }

    public void render(BufferedImage image, int scale){
        System.out.println("render triggered");
        mPanel = new MapPanel(image, scale);

        add(mPanel);
        revalidate();
    }
}
