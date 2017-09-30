import java.util.ArrayList;
import java.util.List;
import greenfoot.GreenfootImage;
import java.awt.Color;
import java.awt.Font;

public enum Mode
{
    DEFAULT(""),
    CREATE_TERRITORY("Create a territory by selecting at least two blank hexes."),
    CREATE_CONTINENT("Create a continent by selecting at least one territory that is not already part of a continent."),
    EDIT_TERRITORY_BONUS("Select a territory to change its bonus."),
    SET_LINK("Click on the territories you want to connect. \n A dot will be placed at the exact place you click."
                                                            + "\n You will be asked to chose a color the first time."),
    EDIT_CONTINENT_COLOR("Select a continent and change its color."),
    EDIT_CONTINENT_BONUS("Select a continent and change its bonus."),
    DELETE_TERRITORY("Select a territory to delete it."),
    DELETE_CONTINENT("Select a continent to delete it (without destroying it's composing territories)."),
    SELECT_INFO_HEX("Select the hex wich will show the current bonus of this territory.");
    
    
    Mode(String text){
        message = text;
        
    }
    
    static private Mode currentMode;
    
    private final String message;
    
    private static MyWorld theWorld() {return MyWorld.theWorld;}
    
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
        
        if(mode == DEFAULT){
            
            ArrayList<Territory> allTerritories = Territory.allTerritories();
            
            int unoccupiedTerritoriesNumber = 0;
            
            for(Territory t : allTerritories){
                
                if(t != null){
                    if(t.continent() == null){
                    unoccupiedTerritoriesNumber++;
                    }
                }
            }
            
            theWorld().createTerritory.makeOpaque();
            if(unoccupiedTerritoriesNumber > 0){
                theWorld().createContinent.makeOpaque();
            }
            if(!Continent.continentList().isEmpty()){
                theWorld().editContinentBonus.makeOpaque();
                theWorld().editContinentColor.makeOpaque();
                theWorld().deleteContinent.makeOpaque();
            }
            if(!allTerritories.isEmpty()){
                theWorld().editTerritoryBonus.makeOpaque();
                theWorld().deleteTerritory.makeOpaque();
            }
            if(allTerritories.size() > 1){
                theWorld().createLink.makeOpaque();
            }
            theWorld().makeXMLButton.makeOpaque();
            
        }else{
            
            List<ModeButton> buttonList = theWorld().getObjects(ModeButton.class);
            
            for(ModeButton mb : buttonList){
                
                if(mb.linkedMode == mode){
                    
                    mb.makeOpaque();
                    
                }
                
            }
            theWorld().okButton.makeOpaque();
        }
        
    }
    
    private static void makeAllButtonsTransparent() {
        theWorld().createTerritory.makeTransparent();
        theWorld().createContinent.makeTransparent();
        theWorld().editContinentBonus.makeTransparent();
        theWorld().editContinentColor.makeTransparent();
        theWorld().editTerritoryBonus.makeTransparent();
        theWorld().createLink.makeTransparent();
        theWorld().deleteTerritory.makeTransparent();
        theWorld().deleteContinent.makeTransparent();
        theWorld().okButton.makeTransparent();
        theWorld().makeXMLButton.makeTransparent();
    
    }

    private static void drawModeMessage(String message){
        
        Font font = new Font("Monospaced", Font.PLAIN, 17);
        
        String textToDisplay = wrapText(message, 16);
        
        GreenfootImage instructions = new GreenfootImage(182, 380);
        instructions.setColor(MyWorld.MENU_COLOR);
        instructions.fill();
        instructions.setColor(Color.BLACK);
        instructions.setFont(font);
        instructions.drawString(textToDisplay, 0, 0);
        
        theWorld().getBackground().drawImage(instructions, MyWorld.WORLD_WIDTH - 182, 700);
        
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
