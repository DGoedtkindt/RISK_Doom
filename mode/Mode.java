package mode;

import appearance.Appearance;
import appearance.Theme;
import base.MyWorld;
import greenfoot.GreenfootImage;
import greenfoot.Font;
import greenfoot.Actor;

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
    SELECT_INFO_HEX("Select the hex wich will show the current bonus of this territory.");
    
    Mode(String text){
        message = text;
        
    }
    
    private static Mode currentMode;
    public static final Actor display = new ModeMessageDisplay();
    private final String message;
    
    public static Mode mode() {
        return currentMode;
        
    };
    
    public static void setMode(Mode newMode) {
        currentMode = newMode;
        ((ModeMessageDisplay)display).display(currentMode.message);
        
    }
    
}

//Mode Message Display/////////////////////////////////////  
class ModeMessageDisplay extends Actor {
    private MyWorld world() {return MyWorld.theWorld;}
    static final int WIDTH = 182;
    static final int HEIGHT = 330;
    
    ModeMessageDisplay() {this.setImage(new GreenfootImage(WIDTH,HEIGHT));}

    void display(String message){
        this.setImage(new GreenfootImage(WIDTH,HEIGHT));
        Font font = new Font("Monospaced", 17);
        String textToDisplay = wrapText(message, 16);
        getImage().setColor(Theme.used.textColor);
        getImage().setFont(font);
        getImage().drawString(textToDisplay, 0, 0);

    }

    private String wrapText(String strToWrap, int maxLineLength) {

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
