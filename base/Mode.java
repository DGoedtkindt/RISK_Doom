package base;

import appearance.Appearance;
import mainObjects.Continent;
import mainObjects.Territory;
import java.util.ArrayList;
import java.util.List;
import greenfoot.GreenfootImage;
import greenfoot.Font;

public enum Mode
{
    //Map Editor modes
    MAP_EDITOR_DEFAULT(""),
    CREATE_TERRITORY("Create a territory by selecting at least two blank hexes."),
    CREATE_CONTINENT("Create a continent by selecting at least one territory that is not already part of a continent."),
    EDIT_TERRITORY_BONUS("Select a territory to change its bonus."),
    SET_LINK("Click on the territories you want to connect. \n A dot will be placed at the exact place you click."
                                                            + "\n You will be asked to chose a color the first time."),
    EDIT_CONTINENT_COLOR("Select a continent and change its color."),
    EDIT_CONTINENT_BONUS("Select a continent and change its bonus."),
    DELETE_TERRITORY("Select a territory to delete it."),
    DELETE_CONTINENT("Select a continent to delete it (without destroying its composing territories)."),
    SELECT_INFO_HEX("Select the hex wich will show the current bonus of this territory."),
    
    //Game modes
    GAME_DEFAULT(""),
    ATTACK(""),
    MOVE_TROOPS(""),
    
    //Menu modes
    GAME_MENU(""),
    LOAD_GAME_MENU(""),
    NEW_GAME_MENU(""),
    MAP_EDITOR_MENU(""),
    OPTIONS(""),
    MAIN_MENU("");
    
    Mode(String text){
        message = text;
        
    }
    
    static private Mode currentMode = MAIN_MENU;
    
    private final String message;
    
    private static MyWorld world() {return MyWorld.theWorld;}
    
    ///Public Methods///////////////////////////////////////
    
    static public void changeMode(Mode newMode){
        currentMode = newMode;
        makeAllButtonsTransparent();
        makeValidButtonsOpaque(currentMode);
        drawModeMessage(currentMode.message);
        
    }
    
    static public Mode currentMode() {
        return currentMode;
        
    }
    
    ///Private Method/////////////////////////////////////
    
    private static void makeValidButtonsOpaque(Mode mode){
        
        switch (mode) {
            case MAP_EDITOR_DEFAULT:
                ArrayList<Territory> allTerritories = Territory.allTerritories();
                int unoccupiedTerritoriesNumber = 0;
                for(Territory t : allTerritories){
                    
                    if(t != null){
                        if(t.continent() == null){
                            unoccupiedTerritoriesNumber++;
                        }
                    }
                }   world().createTerritory.makeOpaque();
                if(unoccupiedTerritoriesNumber > 0){
                    world().createContinent.makeOpaque();
                }if(!Continent.continentList().isEmpty()){
                    world().editContinentBonus.makeOpaque();
                    world().editContinentColor.makeOpaque();
                    world().deleteContinent.makeOpaque();
                }if(!allTerritories.isEmpty()){
                    world().editTerritoryBonus.makeOpaque();
                    world().deleteTerritory.makeOpaque();
                }if(allTerritories.size() > 1){
                    world().createLink.makeOpaque();
                }   
                world().makeXMLButton.makeOpaque();
                world().backButton.makeOpaque();
                break;
            case GAME_DEFAULT:
                break;
            case MAP_EDITOR_MENU:
            case GAME_MENU:
            case LOAD_GAME_MENU:
            case NEW_GAME_MENU:
            case OPTIONS:
                world().backButton.makeOpaque();
                break;
            default:
                List<ModeButton> buttonList = world().getObjects(ModeButton.class);
                for(ModeButton mb : buttonList){
                    
                    if(mb.linkedMode == mode){
                        
                        mb.makeOpaque();
                        
                    }
                    
                }   world().okButton.makeOpaque();
                break;
        }
        
    }
    
    private static void makeAllButtonsTransparent() {
        world().createTerritory.makeTransparent();
        world().createContinent.makeTransparent();
        world().editContinentBonus.makeTransparent();
        world().editContinentColor.makeTransparent();
        world().editTerritoryBonus.makeTransparent();
        world().createLink.makeTransparent();
        world().deleteTerritory.makeTransparent();
        world().deleteContinent.makeTransparent();
        world().okButton.makeTransparent();
        world().makeXMLButton.makeTransparent();
        world().backButton.makeTransparent();
        
    }

    private static void drawModeMessage(String message){
        
        Font font = new Font("Monospaced", 17);
        
        String textToDisplay = wrapText(message, 16);
        
        GreenfootImage instructions = new GreenfootImage(182, 330);
        instructions.setColor(MyWorld.usedTheme.backgroundColor);
        instructions.fill();
        instructions.setColor(GColor.WHITE);
        instructions.setFont(font);
        instructions.drawString(textToDisplay, 0, 0);
        
        world().getBackground().drawImage(instructions, Appearance.WORLD_WIDTH - 182, 750);
        
    }
    
    private static String wrapText(String strToWrap, int maxLineLength) {
        
        String[] words = strToWrap.split(" ");
        String finalString = "\n";
        
        String currentLine = "";
        for(String currentWord : words){
            if((currentLine + currentWord).length() > maxLineLength){
                finalString += currentLine + "\n";
                currentLine = "";
                
            }
            currentLine += " " + currentWord;
            
        }
        finalString += currentLine;
        
        return finalString;
    
    }
    
}
