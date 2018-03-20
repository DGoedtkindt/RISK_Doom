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
    private final Manager MANAGER;
    
    private final Action UPDATE_THIS = () -> {
                modeChanged(Mode.mode());
            };
    
    private final ModeButton CREATE_TERRITORY       = new ModeButton(new GreenfootImage("createNewTerritory.png"),    Mode.CREATE_TERRITORY,      Selector.IS_BLANKHEX);
    private final ModeButton CREATE_CONTINENT       = new ModeButton(new GreenfootImage("addNewContinent.png"),       Mode.CREATE_CONTINENT,      Selector.IS_TERRITORY_NOT_IN_CONTINENT);
    private final ModeButton EDIT_CONTINENT_BONUS   = new ModeButton(new GreenfootImage("editContinentBonus.png"),    Mode.EDIT_CONTINENT_BONUS,  Selector.IS_CONTINENT);
    private final ModeButton EDIT_CONTINENT_COLOR   = new ModeButton(new GreenfootImage("editContinentColor.png"),    Mode.EDIT_CONTINENT_COLOR,  Selector.IS_CONTINENT);
    private final ModeButton EDIT_TERRITORY_BONUS   = new ModeButton(new GreenfootImage("editTerritoryBonus.png"),    Mode.EDIT_TERRITORY_BONUS,  Selector.IS_TERRITORY);
    private final ModeButton CREATE_LINK            = new ModeButton(new GreenfootImage("newLink.png"),               Mode.SET_LINK,              Selector.IS_TERRITORY);
    private final ModeButton DELETE_TERRITORY       = new ModeButton(new GreenfootImage("deleteTerritory.png"),       Mode.DELETE_TERRITORY,      Selector.IS_TERRITORY);
    private final ModeButton DELETE_CONTINENT       = new ModeButton(new GreenfootImage("deleteContinent.png"),       Mode.DELETE_CONTINENT,      Selector.IS_CONTINENT);
    private final OKButton OK_BUTTON                = new OKButton();
    private NButton makeXMLButton;
    
    //to easlily modify all buttons
    private final ArrayList<Button> ALL_BUTTONS = new ArrayList<>();
    
    /**
     * Creates a ControlPanel.
     * @param manager The mapEditor.Manager with which it works.
     */
    protected ControlPanel(Manager manager) {
        MANAGER = manager;
        makeXMLButton = new NButton(() -> {
                                        MapSaver ms = new MapSaver(manager.map());
                                        ms.askForNameAndDescription();
                                        makeXMLButton.toggleUnusable();}, 
                                 new GreenfootImage("MakeXML.png"));
        ALL_BUTTONS.add(CREATE_TERRITORY);
        ALL_BUTTONS.add(CREATE_LINK);
        ALL_BUTTONS.add(EDIT_TERRITORY_BONUS);
        ALL_BUTTONS.add(DELETE_TERRITORY);
        ALL_BUTTONS.add(CREATE_CONTINENT);
        ALL_BUTTONS.add(EDIT_CONTINENT_BONUS);
        ALL_BUTTONS.add(EDIT_CONTINENT_COLOR);
        ALL_BUTTONS.add(DELETE_CONTINENT);
        ALL_BUTTONS.add(OK_BUTTON);
        ALL_BUTTONS.add(makeXMLButton);
    
    }
    
    /**
     * Adds the ControlPanel to the World at given coordinates.
     * @param xPos The x coordinate of this Panel.
     * @param yPos The y coordinate of this Panel.
     */
    public void addToWorld(int xPos, int yPos) {
        world().addObject(CREATE_TERRITORY, xPos, 100);
        world().addObject(CREATE_LINK, xPos + 30, 160);
        world().addObject(EDIT_TERRITORY_BONUS, xPos-30, 160);
        world().addObject(DELETE_TERRITORY, xPos, 220);
        world().addObject(CREATE_CONTINENT, xPos, 300);
        world().addObject(EDIT_CONTINENT_BONUS, xPos + 30, 360);
        world().addObject(EDIT_CONTINENT_COLOR, xPos - 30, 360);
        world().addObject(DELETE_CONTINENT, xPos, 420);
        world().addObject(OK_BUTTON, xPos, 510);
        world().addObject(makeXMLButton, xPos, 600);
        Mode.addModeChangeListener(UPDATE_THIS);
        
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
            case DEFAULT:
                Collection<Territory> allTerritories = MANAGER.map().territories;
                int unoccupiedTerritoriesNumber = 0;
                for(Territory t : allTerritories){
                    
                    if(t != null){
                        if(t.continent() == null){
                            unoccupiedTerritoriesNumber++;
                        }
                    }
                }   CREATE_TERRITORY.toggleUsable();
                if(unoccupiedTerritoriesNumber > 0){
                    CREATE_CONTINENT.toggleUsable();
                }if(!MANAGER.map().continents.isEmpty()){
                    EDIT_CONTINENT_BONUS.toggleUsable();
                    EDIT_CONTINENT_COLOR.toggleUsable();
                    DELETE_CONTINENT.toggleUsable();
                }if(!allTerritories.isEmpty()){
                    EDIT_TERRITORY_BONUS.toggleUsable();
                    DELETE_TERRITORY.toggleUsable();
                }if(allTerritories.size() > 1){
                    CREATE_LINK.toggleUsable();
                }   
                makeXMLButton.toggleUsable();
                break;
            
            case ACTION_ON_LINK : 
                break;
            
            default:
                OK_BUTTON.toggleUsable();
                break;
        }
        
    }
    
    /**
     * Changes the opacity of the ModeButtons that can be used.
     */
    private void makeCurrentModesButtonOpaque() {
        Mode mode = Mode.mode();
        ALL_BUTTONS.forEach((Button b) -> {
            if(b instanceof ModeButton) 
                if(((ModeButton)b).linkedMode == mode) 
                    b.toggleUsable();
        
        });
       
    
    }
    
    /**
     * Changes the opacity of every Button to transparent.
     */
    private void makeAllButtonsTransparent() {
        ALL_BUTTONS.forEach(Button::toggleUnusable);
        
    }
    
    /**
     * Removes this Panel from the World.
     */
    public void removeFromWorld() {
        ALL_BUTTONS.forEach(world()::removeObject);
        Mode.removeModeChangeListener(UPDATE_THIS);
        
    }
    
    
}
