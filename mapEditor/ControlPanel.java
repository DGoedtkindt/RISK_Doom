package mapEditor;

import base.Action;
import base.Button;
import base.MyWorld;
import base.NButton;
import mode.Mode;
import mode.ModeButton;
import greenfoot.GreenfootImage;
import selector.Selector;
import java.util.ArrayList;
import java.util.Collection;
import mainObjects.Territory;

/**
 * A ControlPanel is a group of Actors that allows the User to choose
 * between multiple action modes.
 */
public class ControlPanel {
    
    public final int WIDHT = 60;
    public final int HEIGHT = 600;
    
    private MyWorld world() {return MyWorld.theWorld;}
    private Manager manager;
    
    private Action updateThis = () -> {
                modeChanged(Mode.mode());
            };
    
    private ModeButton createTerritory       = new ModeButton(new GreenfootImage("createNewTerritory.png"),    Mode.CREATE_TERRITORY,      Selector.IS_BLANKHEX);
    private ModeButton createContinent       = new ModeButton(new GreenfootImage("addNewContinent.png"),       Mode.CREATE_CONTINENT,      Selector.IS_TERRITORY_NOT_IN_CONTINENT);
    private ModeButton editContinentBonus    = new ModeButton(new GreenfootImage("editContinentBonus.png"),    Mode.EDIT_CONTINENT_BONUS,  Selector.IS_CONTINENT);
    private ModeButton editContinentColor    = new ModeButton(new GreenfootImage("editContinentColor.png"),    Mode.EDIT_CONTINENT_COLOR,  Selector.IS_CONTINENT);
    private ModeButton editTerritoryBonus    = new ModeButton(new GreenfootImage("editTerritoryBonus.png"),    Mode.EDIT_TERRITORY_BONUS,  Selector.IS_TERRITORY);
    private ModeButton createLink            = new ModeButton(new GreenfootImage("newLink.png"),               Mode.SET_LINK,              Selector.IS_TERRITORY);
    private ModeButton deleteTerritory       = new ModeButton(new GreenfootImage("deleteTerritory.png"),       Mode.DELETE_TERRITORY,      Selector.IS_TERRITORY);
    private ModeButton deleteContinent       = new ModeButton(new GreenfootImage("deleteContinent.png"),       Mode.DELETE_CONTINENT,      Selector.IS_CONTINENT);
    private OKButton okButton                = new OKButton();
    private NButton makeXMLButton;
    
    //to easlily modify all buttons
    private ArrayList<Button> allButtons = new ArrayList<>();
    
    /**
     * Creates a ControlPanel.
     * @param manager The mapEditor.Manager with which it works.
     */
    protected ControlPanel(Manager manager) {
        this.manager = manager;
        this.makeXMLButton = new NButton(() -> {
                                                MapSaver ms = new MapSaver(manager.map());
                                                ms.askForNameAndDescription();
                                                makeXMLButton.makeTransparent();}, 
                                         new GreenfootImage("MakeXML.png"));
        allButtons.add(createTerritory);
        allButtons.add(createLink);
        allButtons.add(editTerritoryBonus);
        allButtons.add(deleteTerritory);
        allButtons.add(createContinent);
        allButtons.add(editContinentBonus);
        allButtons.add(editContinentColor);
        allButtons.add(deleteContinent);
        allButtons.add(okButton);
        allButtons.add(makeXMLButton);
    
    }
    
    /**
     * Adds the ControlPanel to the World at given coordinates.
     * @param xPos The x coordinate of this Panel.
     * @param yPos The y coordinate of this Panel.
     */
    public void addToWorld(int xPos, int yPos) {
        world().addObject(createTerritory, xPos, 100);
        world().addObject(createLink, xPos + 30, 160);
        world().addObject(editTerritoryBonus, xPos-30, 160);
        world().addObject(deleteTerritory, xPos, 220);
        world().addObject(createContinent, xPos, 300);
        world().addObject(editContinentBonus, xPos + 30, 360);
        world().addObject(editContinentColor, xPos - 30, 360);
        world().addObject(deleteContinent, xPos, 420);
        world().addObject(okButton, xPos, 510);
        world().addObject(makeXMLButton, xPos, 600);
        Mode.addModeChangeListener(updateThis);
        
    }
    
    /**
     * Updates its Buttons when the Mode changes.
     */
    private void modeChanged(Mode newMode) {
        makeAllButtonsTransparent();
        makeValidButtonsOpaque(newMode);
        makeCurrentModesButtonOpaque();
    
    }
    
    /**
     * Changes the opacity of the Buttons that can be used.
     * @param mode The new Mode.
     */
    private void makeValidButtonsOpaque(Mode mode){
        
        switch (mode) {
            case MAP_EDITOR_DEFAULT:
                Collection<Territory> allTerritories = manager.map().territories;
                int unoccupiedTerritoriesNumber = 0;
                for(Territory t : allTerritories){
                    
                    if(t != null){
                        if(t.continent() == null){
                            unoccupiedTerritoriesNumber++;
                        }
                    }
                }   createTerritory.makeOpaque();
                if(unoccupiedTerritoriesNumber > 0){
                    createContinent.makeOpaque();
                }if(!manager.map().continents.isEmpty()){
                    editContinentBonus.makeOpaque();
                    editContinentColor.makeOpaque();
                    deleteContinent.makeOpaque();
                }if(!allTerritories.isEmpty()){
                    editTerritoryBonus.makeOpaque();
                    deleteTerritory.makeOpaque();
                }if(allTerritories.size() > 1){
                    createLink.makeOpaque();
                }   
                makeXMLButton.makeOpaque();
                break;
            
            case ACTION_ON_LINK : 
                break;
            
            default:
                okButton.makeOpaque();
                break;
        }
        
    }
    
    /**
     * Changes the opacity of the ModeButtons that can be used.
     */
    private void makeCurrentModesButtonOpaque() {
        Mode mode = Mode.mode();
        allButtons.forEach((Button b) -> {
            if(b instanceof ModeButton) 
                if(((ModeButton)b).linkedMode == mode) 
                    b.makeOpaque();
        
        });
       
    
    }
    
    /**
     * Changes the opacity of every Button to transparent.
     */
    private void makeAllButtonsTransparent() {
        allButtons.forEach(Button::makeTransparent);
        
    }
    
    /**
     * Removes this Panel from the World.
     */
    public void removeFromWorld() {
        allButtons.forEach(world()::removeObject);
        Mode.removeModeChangeListener(updateThis);
        
    }
    
    
}
