package game;

import appearance.MessageDisplayer;
import base.Game;
import base.MyWorld;
import gameXML.GameXML;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameSaver {
    private Game game;
    private String name;
    private String description;
    
    public GameSaver(Game gameToSave) {
        game = gameToSave;
        
    }
    
    public void autoSave() {
        name = game.name;
        description = game.description;
        if(name != null & game.autoSave) {
            if(!nameIsValid()) {
                showNameError();
            } else save();
        }
    }
    
    public void saveGame() {
        askForSaveInfo();
        if(name != null) {
            if(!nameIsValid()) {
                showNameError();
            } else if(nameAlreadyExists()) {
                if(confirm()) save();
            } else save();
        }
    };
    
    private void askForSaveInfo() {
        NamePanel namePanel = new NamePanel();
        int nameEntered = JOptionPane.showConfirmDialog(null, namePanel, "Give your game a name and a description", JOptionPane.OK_CANCEL_OPTION);
        if(nameEntered == JOptionPane.OK_OPTION) {
            name = namePanel.name();
            description = namePanel.description();
            if(namePanel.autoSaveChecked()) {
                game.name = name;
                game.description = description;
                game.autoSave = true;
            }
        }
    
    }
    
    private boolean nameIsValid() {
        return !(name == null || name.isEmpty() || name.matches("\\s+"));
    
    }
    
    private void showNameError() {
        JOptionPane.showMessageDialog(null, "You can't save a game if it has no name");
        
    }
    
    private boolean nameAlreadyExists() {
        return new File("Games/"+name+".xml").exists();
    
    }
    
    private boolean confirm() {
        int answer = JOptionPane.showConfirmDialog(
                        null, "Do you want to replace the existing game '" + name + "' with this one?", 
                        "Replacing an existing game", JOptionPane.YES_NO_OPTION);
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
            MessageDisplayer.showMessage("the Game could not be saved : " + ex);
            
        }
    
    }
    
    private void saveXML() throws Exception{
        GameXML xml = new GameXML(game);
        xml.write(name, description);
    
    }
    
    private void saveThumbnail() throws Exception {
        BufferedImage gameImage = MyWorld.theWorld.getBackground().getAwtImage();
        File out = new File(new File("Games").getAbsolutePath() + "/" + name + ".png");

        ImageIO.write(gameImage, "PNG", out);
        System.out.println("thumbnail saved");
    
    }
    
    private static class NamePanel extends JPanel {
        JTextField nameField = new JTextField(15);
        JTextField descriptionField = new JTextField(65);
        JCheckBox autoSave = new JCheckBox("Auto save ?");
        
        NamePanel() {
            this.add(new JLabel("Name : "));
            this.add(nameField);
            this.add(Box.createHorizontalStrut(20));
            this.add(new JLabel("Description : "));
            this.add(descriptionField);
            this.add(autoSave);
        }
        
        String name() {
            return nameField.getText();
        
        }
        
        String description() {
            return descriptionField.getText();
        
        }
        
        boolean autoSaveChecked() {
            return autoSave.isSelected();
            
        }
    
    }
}
