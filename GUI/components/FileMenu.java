package GUI.components;
import javax.swing.*;

public class FileMenu extends JPopupMenu{
    private JMenuItem loadRaster, loadNetwork, save;

    public FileMenu(){
        super();
        System.out.println("File Menu constructed");

        loadRaster = new JMenuItem("Load Raster");
        loadNetwork = new JMenuItem("Load Network");
        save = new JMenuItem("Save");

        setBackground(MenuBar.BG);
        loadRaster.setBackground(MenuBar.BG);
        loadNetwork.setBackground(MenuBar.BG);
        save.setBackground(MenuBar.BG);

        add(loadRaster);
        add(loadNetwork);
        add(save);
    }
    
}
