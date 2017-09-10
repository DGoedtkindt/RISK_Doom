import java.util.ArrayList;
import java.util.List;
import greenfoot.GreenfootImage;
import java.awt.Font;
import java.awt.FontMetrics;

public class Mode
{
    
    public Mode(String text){
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

    private static void drawModeMessage(String messageToDisplay){
        
        theWorld().getBackground().setColor(MyWorld.MENU_COLOR);
        theWorld().getBackground().fillRect(MyWorld.WORLD_WIDTH - 200, 700, 200, 380);
        
        int spacing = 20;
        int maxLineLength = 200;
        
        String[] words = messageToDisplay.split(" ");
        
        ArrayList<String> linesList = new ArrayList<String>();
        
        String currentLine = "";
        
        Font instructionsFont = new Font("System", Font.PLAIN, 20);
        FontMetrics fm = theWorld().getBackground().getAwtImage().getGraphics().getFontMetrics(instructionsFont);
        
        for(String currentWord : words){
            
            if(fm.stringWidth(currentLine + currentWord) > maxLineLength){
                linesList.add(currentLine);
                currentLine = "";
                
            }
            
            currentLine += " " + currentWord;
            
        }
        
        linesList.add(currentLine);
        
        GreenfootImage instructions = new GreenfootImage(200, 380);
        instructions.setFont(instructionsFont);
        instructions.setColor(MyWorld.SELECTION_COLOR);
        
        for(int i = 0; i < linesList.size(); i++){
            String line = linesList.get(i);
            instructions.drawString(line, 0, 20 + i * spacing);
            
        }
        
        theWorld().getBackground().drawImage(instructions, MyWorld.WORLD_WIDTH - 200, 700);
        
        
    }
    
    ///Final Modes//////////////////////////////////////////
    
    static public final Mode DEFAULT                     = new Mode("");
    static public final Mode CREATE_TERRITORY            = new Mode("Create a territory by selecting at least two blank hexes.");
    static public final Mode CREATE_CONTINENT            = new Mode("Create a continent by selecting at least one territory that is not already part of a continent.");
    static public final Mode EDIT_TERRITORY_BONUS        = new Mode("Select a territory to change its bonus.");
    static public final Mode SET_LINK                    = new Mode("Select two territories to allow troops to go from one to another.");
    static public final Mode EDIT_CONTINENT_COLOR        = new Mode("Select a continent and change its color.");
    static public final Mode EDIT_CONTINENT_BONUS        = new Mode("Select a continent and change its bonus.");
    static public final Mode DELETE_TERRITORY            = new Mode("Select a territory to delete it.");
    static public final Mode DELETE_CONTINENT            = new Mode("Select a continent to delete it (without destroying it's composing territories).");
    static public final Mode SELECT_INFO_HEX             = new Mode("Select the hex wich will show the current bonus of this territory.");
    
}
