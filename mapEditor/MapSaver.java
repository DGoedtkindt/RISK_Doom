package mapEditor;

import appearance.MessageDisplayer;
import base.Map;
import base.MyWorld;
import input.Form;
import input.FormAction;
import input.Input;
import input.TextInput;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import mapXML.MapXML;

/**
 * This Class contains method that are used to save the map in a File.
 * 
 */
public class MapSaver {
    
    private final Map MAP;
    private String name;
    private String description;
    
    private FormAction saveInfoFormAction;
    
    /**
     * Creates a MapSaver.
     * @param mapToSave The Map that must be saved.
     */
    public MapSaver(Map mapToSave) {
        MAP = mapToSave;
        defineSaveInfoFormAction();
    
    }
    
    /**
     * Saves a Map with a name and a description.
     */
    private void saveMap() {
        if(name != null){
            if(!nameIsValid()) {
                showNameError();
            } else if(nameAlreadyExists()) {
                confirm();
            } else save();
        }
    }
    
    /**
     * Asks the User to enter a name and a description for this Map.
     */
    public void askForNameAndDescription() {
        Form saveInfoForm = new Form();
        Input nameInput = new TextInput("Give your Map a name.");
        Input descriptionInput = new TextInput("Give your Map a description.");
        saveInfoForm.addInput("name", nameInput, false);
        saveInfoForm.addInput("description", descriptionInput, true);
        saveInfoForm.submitAction = saveInfoFormAction;
        saveInfoForm.addToWorld();
    
    }
    
    /**
     * Defines the saveInfoFormAction
     * it gets the name and description from the form
     * and then saves.
     */
    private void defineSaveInfoFormAction() {
        saveInfoFormAction = (inputs)-> {
            name = inputs.get("name");
            description = inputs.get("description");
            saveMap();
            
        };
    
    }
    
    /**
     * Checks if the name is valid, that is, if it isn't null, empty, full of whitespace characters or 'New Map'.
     */
    private boolean nameIsValid() {
        return !(name == null || name.isEmpty() || name.equals("New Map") || name.matches("\\s+"));
    
    }
    
    /**
     * Shows an Error if the Name of this Map is not a valid one.
     */
    private void showNameError() {
        MessageDisplayer.showMessage("You can't save a map if it has no name or if its name is 'New Map'");
        
    }
    
    /**
     * Checks if the name of this Map already exists.
     * @return A boolean representation of the existence of a File with the same name.
     */
    private boolean nameAlreadyExists() {
        return new File("Maps/"+name+".xml").exists();
    
    }
    
    /**
     * Asks the User if he wants to replace an existing Map with a new one.
     */
    private void confirm() {
        Form.confirmInput("Do you want to replace the existing Map '" + name + "' with this one?", (input)-> {
            if(input.get("confirmamtion").equals("Yes")) {
                save();
            }
        });
    
    }
    
    /**
     * Tries to save the Map.
     */
    private void save() {
        try {
            saveXML();
            try {
                saveThumbnail();
            } catch (Exception ex) {
                String message = "thumbnail couldn't be saved";
                MessageDisplayer.showException(new Exception(message,ex));
            }
            
        } catch (Exception ex) {
            String message = "The Map could'nt be saved.";
            MessageDisplayer.showException(new Exception(message , ex));
        }
    
    }
    
    /**
     * Saves the Map.
     */
    private void saveXML() throws Exception{
        MapXML xml = new MapXML(MAP);
        xml.write(name, description);
    
    }
    
    /**
     * Saves the thumbnail of the Map.
     * @throws Exception If the thumbnail couldn't be saved.
     */
    private void saveThumbnail() throws Exception {
        BufferedImage mapImage = MyWorld.theWorld.getBackground().getAwtImage();
        File out = new File(new File("Maps").getAbsolutePath() + "/" + name + ".png");

        ImageIO.write(mapImage, "PNG", out);
        
    }
    
}
