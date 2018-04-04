package game;

import appearance.MessageDisplayer;
import base.Game;
import base.MyWorld;
import gameXML.GameXML;
import input.ChoiceInput;
import input.Form;
import input.FormAction;
import input.Input;
import input.TextInput;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * This Class contains instructions to save a Game.
 * 
 */
public class GameSaver{
    private final Game game;
    private String name;
    private String description;
    
    private FormAction saveInfoFormAction;
    
    /**
     * Creates a GameSaver object that can save a Game.
     * @param gameToSave The Game to save.
     */
    public GameSaver(Game gameToSave) {
        game = gameToSave;
        defineSaveInfoFormAction();
        
    }
    
    /**
     * Saves the Game automatically.
     */
    public void autoSave() {
        name = game.name;
        description = game.description;
        if(name != null & game.autoSave) {
            if(!nameIsValid()) {
                showNameError();
            } else save();
        }
    }
    
    /**
     * Saves the Game manually.
     */
    private void saveGame() {
        if(name != null) {
            if(!nameIsValid()) {
                showNameError();
            } else if(nameAlreadyExists()) {
                confirm();
            } else save();
        }
    }
    
    /**
     * Shows a Form to obtains informations about the name and 
     * the description of the Game.
     */
    public void askForSaveInfo() {
        Form saveInfoForm = new Form();
        Input nameInput = new TextInput("Give your Game a name.");
        Input descriptionInput = new TextInput("Give your Game a description.");
        Input autoSaveInput = new ChoiceInput("Do you want your Game to be saved automatically?", "Yes", "No");
        saveInfoForm.addInput("name", nameInput, false);
        saveInfoForm.addInput("description", descriptionInput, true);
        saveInfoForm.addInput("autoSave", autoSaveInput, true);
        saveInfoForm.submitAction = saveInfoFormAction;
        
        saveInfoForm.addToWorld();
        
    }
    
    /**
     * Defines the saveInfoFormAction
     * it gets the name and description from the form. 
     * and if autoSave is true, it saves the description and name in the Game
     */
    private void defineSaveInfoFormAction() {
        saveInfoFormAction = (inputs)-> {
            name = inputs.get("name");
            description = inputs.get("description");
            saveGame();
            switch(inputs.get("autoSave")) {
                case "" : break;
                case "Yes" : 
                    game.autoSave = true;
                    game.name = name;
                    game.description = description;
                    break;
                case "No" : game.autoSave = false; break;
            
            }
        };
    
    }
    
    /**
     * Checks the validity of the name given to the Game. A name is invalid if it's null, an empty
     * String or a String full of whitespace characters.
     * @return A boolean representation of the validity of the name.
     */
    private boolean nameIsValid() {
        return !(name == null || name.isEmpty() || name.matches("\\s+"));
    
    }
    
    /**
     * Shows an error if the name isn't valid.
     */
    private void showNameError() {
        MessageDisplayer.showMessage("You can't save a game if it has no name");
        
    }
    
    /**
     * Checks if the chosen name already exists.
     * @return A boolean representation of the existence of a File with 
     *         the same name.
     */
    private boolean nameAlreadyExists() {
        return new File("Games/"+name+".xml").exists();
    
    }
    
    /**
     * Asks the User if he wants to replace an existing Game.
     */
    private void confirm() {
        Form.confirmInput("Do you want to replace the existing game '" + name + "' with this one?", (input)-> {
            if(input.get("confirmation").equals("Yes")) {
                save();
            }
        });
    }
    
    /**
     * Tries to save a Game.
     */
    private void save() {
        try {
            saveXML();
            try {
                saveThumbnail();
            } catch (Exception ex) {
                MessageDisplayer.showException(new Exception("The thumbnail couldn't be saved.", ex));
            }
            
        } catch (Exception ex) {
            MessageDisplayer.showException(new Exception("The Game could not be saved.", ex));
            
        }
    
    }
    
    /**
     * Tries to save a XML representation of the Game.
     * @throws An Exception if the Game couldn't be saved for any reason.
     */
    private void saveXML() throws Exception{
        GameXML xml = new GameXML(game);
        xml.write(name, description);
    
    }
    
    /**
     * Tries to save a thumbnail for the Game.
     * @throws An Exception if the thumbnail couldn't be saved for any reason.
     */
    private void saveThumbnail() throws Exception {
        BufferedImage gameImage = MyWorld.theWorld.getBackground().getAwtImage();
        File out = new File(new File("Games").getAbsolutePath() + "/" + name + ".png");

        ImageIO.write(gameImage, "PNG", out);
    
    }
    
}
