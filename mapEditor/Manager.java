package mapEditor;

//<editor-fold defaultstate="collapsed" desc="Imports">
import appearance.Theme;
import base.Action;
import base.Map;
import base.MyWorld;
import base.NButton;
import base.StateManager;
import mode.Mode;
import mode.ModeMessageDisplay;
import greenfoot.GreenfootImage;
import greenfoot.Actor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mainObjects.Continent;
import mainObjects.Links;
import mainObjects.Territory;
import mapXML.MapXML;
import selector.Selector;
//</editor-fold>

public class Manager extends StateManager{
    
    private Map loadedMap = new Map();
    private Map mapToLoad;
    
    private ControlPanel ctrlPanel;
    private ModeMessageDisplay modeDisp;
    private NButton options;
    
    public Manager(Map loadMap) {
        mapToLoad = loadMap;
        ctrlPanel = new ControlPanel();
        modeDisp = new ModeMessageDisplay();
        options = new NButton(loadOptionsMenu, "Options");
    
    }
    
    @Override
    public void setupScene() {
        Mode.setMode(Mode.MAP_EDITOR_DEFAULT);
        world().makeSureSceneIsClear();
        world().placeBlankHexs();
        ctrlPanel.addToWorld(world().getWidth()-100, 300);
        modeDisp.addToWorld(world().getWidth()-90, 850);
        world().addObject(Continent.display, 840, 960);
        world().addObject(options, world().getWidth()-120, 50);
        loadMap();

    }
    
    public void loadMap() {
        mapToLoad.territories.forEach(Territory::addToWorld);
        mapToLoad.continents.forEach(Continent::addToWorld);
        mapToLoad.links.forEach(Links::addToWorld);
        mapToLoad = null;
    
    }
    
    @Override
    public Map map() {return loadedMap;}

    @Override
    public void clearScene() {
        mapToLoad = loadedMap;
        loadedMap = new Map();
        
        ctrlPanel.removeFromWorld();
        modeDisp.removeFromWorld();
        
        world().removeObjects(world().getObjects(Actor.class));
        
    }
    
    @Override
    public void escape() {
        if(Mode.mode() == Mode.MAP_EDITOR_DEFAULT) {

            int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the main menu?", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                clearScene();
                world().load(new menu.Manager());

            } 
        
        } else {
            Selector.clear();

            if(Links.newLinks != null){
                Links.newLinks.destroy();
                Links.newLinks = null;
            }

            Mode.setMode(Mode.MAP_EDITOR_DEFAULT);
            
        }
        
    }
    
    //Action pour sauver le monde
    
    protected static Action saveFile = () -> {
        
        String mapName = "";
        String mapDescription = "";
        
        //créer la boite de dialogue
        JTextField nameField = new JTextField(15);
        JTextField descriptionField = new JTextField(65);
        JPanel questionPanel = new JPanel();
        questionPanel.add(new JLabel("Name : "));
        questionPanel.add(nameField);
        questionPanel.add(Box.createHorizontalStrut(20));
        questionPanel.add(new JLabel("Description : "));
        questionPanel.add(descriptionField);

        //get le résultat de la boite de dialogue
        int output = JOptionPane.showConfirmDialog(null, questionPanel, "Give your map a name and a description", JOptionPane.OK_CANCEL_OPTION);
        if (output == JOptionPane.OK_OPTION) {
           mapName = nameField.getText();
           mapDescription = descriptionField.getText();
        }

        //vérifier quelques conditions
        int writeQ = JOptionPane.YES_OPTION;

        if(mapName == null || mapName.isEmpty() || mapName == "New Map") {
            System.err.println("You can't save a map if it has no name or if its name is 'New Map'");
            writeQ = JOptionPane.NO_OPTION;

        } else if(new File("Maps/"+mapName+".xml").exists()){
                writeQ = JOptionPane.showConfirmDialog(
                        null, "Do you want to replace the existing map '" + mapName + "' with this one?", 
                        "Replacing an existing map", JOptionPane.YES_NO_OPTION);

        }

        //si les conditions sont accomplies: sauver
        if(writeQ == JOptionPane.YES_OPTION){
            try {
                MapXML xml = new MapXML(MyWorld.theWorld.stateManager.map());
                xml.write(mapName, mapDescription);
                
                //écrivre l'image du thumbnail (solution temporaire)
                try{
                    BufferedImage mapImage = MyWorld.theWorld.getBackground().getAwtImage();
                    File out = new File(new File("Maps").getAbsolutePath() + "/" + mapName + ".png");

                    ImageIO.write(mapImage, "PNG", out);
                    System.out.println("thumbnail saved");

                } catch(IOException ex) {
                    System.err.println("thumbnail couldn't be saved : " + ex);

                }

            } catch (Exception ex) {
                System.err.println("Map could not be saved : "+ ex);
            }

        }
    };
    
    /////Private Methods///////////////////////
    
    private static JPanel makeDialogBox() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void paintBackgroundDarker() {
        GreenfootImage bckGrd = world().getBackground();
        bckGrd.setColor(Theme.used.backgroundColor.darker());
        bckGrd.fill();
    
    }
    
    private Action loadOptionsMenu = () -> {
                clearScene();
                world().load(new userPreferences.Manager(this));};
    
    

}
