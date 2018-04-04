package mode;

import appearance.Theme;
import base.Action;
import base.MyWorld;
import greenfoot.Actor;
import greenfoot.Font;
import greenfoot.GreenfootImage;

/**
 * An Actor that displays the messages given by the different Modes.
 * 
 */
public class ModeMessageDisplay extends Actor{
    final int WIDTH = 182;
    final int HEIGHT = 330;
    
    private MyWorld world() {return MyWorld.theWorld;}
    
    private final Action UPDATE_THIS = () -> {display(Mode.mode().message);};
    
    /**
     * Creates a ModeMessageDisplay.
     */
    public ModeMessageDisplay() {
        display("");
        
    }
    
    /**
     * Adds this Actor to the World.
     * @param xPos The x coordinate of this Actor.
     * @param yPos The y coordinate of this Actor.
     */
    public void addToWorld(int xPos, int yPos) {
        world().addObject(this, xPos, yPos);
        Mode.addModeChangeListener(UPDATE_THIS);
        
    }
    
    /**
     * Removes this Actor ftom the World.
     */
    public void removeFromWorld() {
        world().removeObject(this);
        Mode.removeModeChangeListener(UPDATE_THIS);
        
    }
    
    /**
     * Changes the text displayed on this ModeMessageDisplay.
     * @param message The message that should be displayed.
     */
    private void display(String message){
        setImage(new GreenfootImage(WIDTH, HEIGHT));
        Font font = new Font("Monospaced", 17);
        String textToDisplay = wrapText(message, 16);
        getImage().setColor(Theme.used.textColor);
        getImage().setFont(font);
        getImage().drawString(textToDisplay, 0, 0);

    }
    
    /**
     * Adds new lines to the displayed text until it fits in the image.
     * @param strToWrap The text that must be transformed by adding '\n's to it.
     * @param maxLineLength The maximal number of characters in a line.
     */
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

