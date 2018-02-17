package appearance;

import base.Button;
import base.MyWorld;
import greenfoot.GreenfootImage;
import java.awt.Font;
import java.awt.FontMetrics;
import mode.Mode;

/**
 * An Actor that displays error messages.
 * 
 */
public class MessageDisplayer extends Button{
    
    private static final java.awt.Font awtFont = new java.awt.Font("Monospaced", Font.BOLD, 50);
    private static final greenfoot.Font greenfootFont = new greenfoot.Font("Monospaced", true, false, 50);
    
    private static FontMetrics fm;
    
    public int width;
    public int height;
    private int linesNumber = 1;
    
    /**
     * Creates a MessageDisplayer with the specified message.
     * @param message Displayed message
     */
    private MessageDisplayer(String message){
        
        GreenfootImage img = new GreenfootImage(1, 1);
        fm = img.getAwtImage().getGraphics().getFontMetrics(awtFont);
        
        width = fm.stringWidth(message);
        if(width > Appearance.WORLD_WIDTH){width = Appearance.WORLD_WIDTH;}
        height = fm.getMaxAscent() + fm.getMaxDescent();
        
        message = wrapText(message);
        
        img.scale(width, height * linesNumber);
        img.setColor(Theme.used.backgroundColor.brighter());
        img.fill();
        img.setColor(Theme.used.textColor);
        img.setFont(greenfootFont);
        img.drawString(message, 0, fm.getMaxAscent());
        setImage(img);
        
    }
    
    /**
     * Shows a MessageDisplayer with a specified message.
     * @param message Displayed message
     */
    public static void showMessage(String message){
        
        MessageDisplayer displayer = new MessageDisplayer(message);
        MyWorld.theWorld.addObject(displayer, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        Mode.setMode(Mode.SHOWING_ERROR);
        
    }
    
    /**
     * Modifies the message by adding lines to it.
     * @param message Transformed message
     */
    private String wrapText(String message) {

        String[] words = message.split(" ");
        String finalString = "";
        
        String currentLine = "";
        for(String currentWord : words){
            if(fm.stringWidth(currentLine + " " + currentWord) > Appearance.WORLD_WIDTH){
                finalString += currentLine + "\n";
                currentLine = "";
                linesNumber ++;

            }
            
            if(!currentLine.isEmpty()){
                currentLine += " ";
            }
            
            currentLine += currentWord;

        }
        finalString += currentLine;

        return finalString;

    }
    
    @Override
    public void clicked() {
        world().removeObject(this);
        world().stateManager.escape();
    }
    
}
