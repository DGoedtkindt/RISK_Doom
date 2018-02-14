package mapEditor;


import appearance.MessageDisplayer;
import base.Map;
import base.MyWorld;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import mapXML.MapXML;

public class MapSaver {
    
    private Map map;
    private String name;
    private String description;
    
    public MapSaver(Map mapToSave) {
        map = mapToSave;
    
    }
    
    public void saveMap() {
        askForNameAndDescription();
        if(name != null) {
            if(!nameIsValid()) {
                showNameError();
            } else if(nameAlreadyExists()) {
                if(confirm()) save();
            } else save();
        }
    };
    
    private void askForNameAndDescription() {
        NamePanel namePanel = new NamePanel();
        int nameEntered = JOptionPane.showConfirmDialog(null, namePanel, "Give your map a name and a description", JOptionPane.OK_CANCEL_OPTION);
        if(nameEntered == JOptionPane.OK_OPTION) {
            name = namePanel.name();
            description = namePanel.description();
        };
    
    }
    
    private boolean nameIsValid() {
        return !(name == null || name.isEmpty() || name == "New Map");
    
    }
    
    private void showNameError() {
        JOptionPane.showMessageDialog(null, "You can't save a map if it has no name or if its name is 'New Map'");
        
    }
    
    private boolean nameAlreadyExists() {
        return new File("Maps/"+name+".xml").exists();
    
    }
    
    private boolean confirm() {
        int answer = JOptionPane.showConfirmDialog(
                        null, "Do you want to replace the existing map '" + name + "' with this one?", 
                        "Replacing an existing map", JOptionPane.YES_NO_OPTION);
        return answer == JOptionPane.YES_OPTION;
    
    }
    
    private void save() {
        try {
            saveXML();
            try {
                saveThumbnail();
            } catch (Exception ex) {
                MessageDisplayer.showMessage("thumbnail couldn't be saved : " + ex);
            }
            
        } catch (Exception ex) {
            MessageDisplayer.showMessage("The Map could'nt be saved : " + ex);
            JOptionPane.showMessageDialog(null, "the Map could not be saved : " + ex);
        }
    
    }
    
    private void saveXML() throws Exception{
        MapXML xml = new MapXML(map);
        xml.write(name, description);
    
    }
    
    private void saveThumbnail() throws Exception {
        BufferedImage mapImage = MyWorld.theWorld.getBackground().getAwtImage();
        File out = new File(new File("Maps").getAbsolutePath() + "/" + name + ".png");

        ImageIO.write(mapImage, "PNG", out);
        System.out.println("thumbnail saved");
    
    }
    
    private static class NamePanel extends JPanel {
        JTextField nameField = new JTextField(15);
        JTextField descriptionField = new JTextField(65);
        
        NamePanel() {
            this.add(new JLabel("Name : "));
            this.add(nameField);
            this.add(Box.createHorizontalStrut(20));
            this.add(new JLabel("Description : "));
            this.add(descriptionField);
        
        }
        
        String name() {
            return nameField.getText();
        
        }
        
        String description() {
            return descriptionField.getText();
        
        }
    
    }
}
