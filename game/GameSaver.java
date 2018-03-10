package game;

import appearance.Appearance;
import appearance.InputPanel;
import appearance.MessageDisplayer;
import base.Game;
import base.InputPanelUser;
import base.MyWorld;
import gameXML.GameXML;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * This Class contains instructions to save a Game.
 * 
 */
public class GameSaver implements InputPanelUser{
    private Game game;
    private String name;
    private String description;
    
    private boolean descriptionEntered = false;
    
    /**
     * Creates a GameSaver object that can save a Game.
     * @param gameToSave The Game to save.
     */
    public GameSaver(Game gameToSave) {
        game = gameToSave;
        
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
     * Obtains informations about the name and the description of the Game.
     */
    public void askForSaveInfo() {
        
        InputPanel.showInsertionPanel("Give your Game a name.", 500, "name", this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 4);
        InputPanel.showInsertionPanel("Give your Game a description.", 1000, "description", this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        InputPanel.showConfirmPanel("Do you want your Game to be saved automatically?", 100, "autosave", this, Appearance.WORLD_WIDTH / 2, 3 * Appearance.WORLD_HEIGHT / 4);
    
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
        InputPanel.showConfirmPanel("Do you want to replace the existing game '" + name + "' with this one?", 
                                    100, "replacement", this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        
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
                MessageDisplayer.showMessage("thumbnail couldn't be saved : " + ex);
            }
            
        } catch (Exception ex) {
            MessageDisplayer.showMessage("the Game could not be saved : " + ex);
            
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
        System.out.println("thumbnail saved");
    
    }

    @Override
    public void useInformations(String information, String type) throws Exception {
        
        switch(type){
            
            case "name" : 
                ((GameSaver)this).name = information;
                
                if(((GameSaver)this).descriptionEntered){
                    ((GameSaver)this).saveGame();
                }
                
                break;
            
            case "description" : 
                ((GameSaver)this).description = information;
                descriptionEntered = true;
                
                if(((GameSaver)this).nameIsValid()){
                    ((GameSaver)this).saveGame();
                }
                
                break;
                
            case "replacement" : 
                
                if(information.equals(InputPanel.YES_OPTION)){
                    ((GameSaver)this).save();
                }
                
                break;
                
            case "autosave" : 
                
                ((GameSaver)this).game.name = ((GameSaver)this).name;
                ((GameSaver)this).game.description = ((GameSaver)this).description;
                ((GameSaver)this).game.autoSave = true;
                
                break;
                
        }
        
    }
    
}
