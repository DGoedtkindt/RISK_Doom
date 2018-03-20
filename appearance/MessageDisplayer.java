package appearance;

import base.MyWorld;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

/**
 * An Actor that displays error messages.
 * 
 */
public class MessageDisplayer extends Actor{
    
    private static final int TOTAL_WIDTH = 700;
    private static MyWorld world() {return MyWorld.theWorld;}
    
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
        
        message = Appearance.standardTextWrapping(message, width);
        
        linesNumber = message.split("\n").length;
        
        //Creates the image of this displayer
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
        world().addObject(displayer, TOTAL_WIDTH / 2, Appearance.WORLD_HEIGHT - (displayer.height * displayer.linesNumber / 2));
        displayer.createTimer();
        
    }
    
    /**
     * Show a MessageDisplayer with the string representation of an Exception
     * @param ex Displayed Exception 
     */
    public static void showException(Exception ex) {
        showMessage(ex.getMessage());
        ex.printStackTrace(System.err); //Programmer's side
    }
    
    /**
     * Creates a Timer that destroys the MessageDisplayer
     */
    private void createTimer(){
        
        Timer timer = new Timer(50, (ActionEvent ae) -> {
            getImage().setTransparency(getImage().getTransparency() - 5);
            
            if(getImage().getTransparency() == 0){
                world().removeObject(this);
                ((Timer)ae.getSource()).stop();
            }
            
        });
        
        timer.setInitialDelay(1500);
        timer.setRepeats(true);
        timer.start();
        
    }
    
}
