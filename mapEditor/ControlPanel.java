package mapEditor;

import mode.Mode;
import mode.ModeButton;
import base.*;
import greenfoot.GreenfootImage;
import selector.Selector;
import java.util.ArrayList;
import java.util.Collection;
import mainObjects.Territory;

/**a ControlPanel is a group of actors that allows the user to choose
 * between multiple action modes.
 */
public class ControlPanel {
    
    public final int WIDHT = 60;
    public final int HEIGHT = 600;
    
    private MyWorld world() {return MyWorld.theWorld;}
    private StateManager manager() {return world().stateManager;}
    
    private ModeButton createTerritory       = new ModeButton("createNewTerritory.png",    Mode.CREATE_TERRITORY,      Selector.IS_BLANKHEX);
    private ModeButton createContinent       = new ModeButton("addNewContinent.png",       Mode.CREATE_CONTINENT,      Selector.IS_TERRITORY_NOT_IN_CONTINENT);
    private ModeButton editContinentBonus    = new ModeButton("editContinentBonus.png",    Mode.EDIT_CONTINENT_BONUS,  Selector.IS_CONTINENT);
    private ModeButton editContinentColor    = new ModeButton("editContinentColor.png",    Mode.EDIT_CONTINENT_COLOR,  Selector.IS_CONTINENT);
    private ModeButton editTerritoryBonus    = new ModeButton("editTerritoryBonus.png",    Mode.EDIT_TERRITORY_BONUS,  Selector.IS_TERRITORY);
    private ModeButton createLink            = new ModeButton("newLink.png",               Mode.SET_LINK,              Selector.IS_TERRITORY);
    private ModeButton deleteTerritory       = new ModeButton("deleteTerritory.png",       Mode.DELETE_TERRITORY,      Selector.IS_TERRITORY);
    private ModeButton deleteContinent       = new ModeButton("deleteContinent.png",       Mode.DELETE_CONTINENT,      Selector.IS_CONTINENT);
    private OKButton okButton                = new OKButton();
    private NButton makeXMLButton            = new NButton(NButton.saveFile, new GreenfootImage("MakeXML.png"));
    
    //to easlily modify all buttons
    private ArrayList<Button> allButtons = new ArrayList<>();
    
    protected ControlPanel() {
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
    
    protected void addToWorld(int xPos, int yPos) {
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
    }
    
    protected void modeChanged(Mode newMode) {
        makeAllButtonsTransparent();
        makeValidButtonsOpaque(newMode);
    
    }
    
    private void makeValidButtonsOpaque(Mode mode){
        
        switch (Mode.mode()) {
            case MAP_EDITOR_DEFAULT:
                Collection<Territory> allTerritories = manager().map().territories;
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
                }if(!manager().map().continents.isEmpty()){
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
            default:
                okButton.makeOpaque();
                break;
        }
        
    }
    
    private void makeAllButtonsTransparent() {
        allButtons.forEach(Button::makeTransparent);
        
    }
    
    protected void removeFromWorld() {
        allButtons.forEach((Button b) -> {world().removeObject(b);});
        
    }
    
    
}
