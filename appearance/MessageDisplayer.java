package appearance;

import base.Button;
import base.MyWorld;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 * An Actor that displays error messages.
 * 
 */
public class MessageDisplayer extends Button{
    
    private static final int TOTAL_WIDTH = 700;
    
    private static FontMetrics fm;
    
    public int width;
    public int height;
    private int linesNumber = 1;
    
    /**
     * Creates a MessageDisplayer with the specified message.
     * @param message Displayed message
     */
    private MessageDisplayer(String message){
        message += "";
        GreenfootImage img = new GreenfootImage(1, 1);
        fm = img.getAwtImage().getGraphics().getFontMetrics(Appearance.AWT_FONT);
        
        width = fm.stringWidth(message) + 4;
        if(width > TOTAL_WIDTH){width = TOTAL_WIDTH;}
        height = fm.getMaxAscent() + fm.getMaxDescent();
        
        message = wrapText(message);
        
        img.scale(width, height * linesNumber);
        img.setColor(Theme.used.backgroundColor.brighter());
        img.fill();
        img.setColor(Theme.used.textColor);
        img.drawRect(1, 1, width - 3, height * linesNumber - 3);
        img.setFont(Appearance.GREENFOOT_FONT);
        img.drawString(message, 2, fm.getMaxAscent());
        setImage(img);
        
    }
    
    /**
     * Shows a MessageDisplayer with a specified message.
     * @param message Displayed message
     */
    public static void showMessage(String message){
        
        MessageDisplayer displayer = new MessageDisplayer(message);
        MyWorld.theWorld.addObject(displayer, TOTAL_WIDTH / 2, Appearance.WORLD_HEIGHT - (displayer.height * displayer.linesNumber / 2));
        displayer.createTimer();
        
    }
    
    /**
     * Show a MessageDisplayer with the string representation of an Exception
     * @param ex Displayed Exception 
     */
    public static void showException(Exception ex) {
        showMessage(ex.toString());
        ex.printStackTrace(System.err);
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
            if(fm.stringWidth(currentLine + " " + currentWord) > TOTAL_WIDTH){
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
    
    /**
     * Creates a Timer that destroys the MessageDisplayer
     */
    private void createTimer(){
        
        Timer timer = new Timer(40, (ActionEvent ae) -> {
            this.getImage().setTransparency(this.getImage().getTransparency() - 5);
            
            if(this.getImage().getTransparency() == 0){
                MyWorld.theWorld.removeObject(this);
                ((Timer)ae.getSource()).stop();
            }
            
        });
        
        timer.setInitialDelay(1200);
        timer.setRepeats(true);
        timer.start();
        
    }
    
    @Override
    public void clicked() {
        world().removeObject(this);
    }
    
}
