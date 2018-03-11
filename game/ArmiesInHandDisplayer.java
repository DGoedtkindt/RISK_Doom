package game;

import appearance.Appearance;
import appearance.Theme;
import base.MyWorld;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import mode.Mode;
import selector.Selector;

/**
 * Displays the number of armies a Player can place.
 * 
 */
public class ArmiesInHandDisplayer {
    
    private static Display displayer;
    
    static {
        displayer = new Display();
        Mode.addModeChangeListener(() -> {
            update();
        });
        
    }
    
    /**
     * Creates an image for the displayer.
     */
    private static void updateImage(int numArmies){
        
        String displayedText = "You can play " + numArmies + " armies.";
        
        GreenfootImage img = new GreenfootImage(1, 1);
        img.setFont(Appearance.GREENFOOT_FONT);
        FontMetrics fm = img.getAwtImage().getGraphics().getFontMetrics(Appearance.AWT_FONT);
        
        int lineHeight = fm.getMaxAscent() + fm.getMaxDescent();
        int textWidth = fm.stringWidth(displayedText);
        
        img.scale(textWidth + 4, lineHeight);
        
        img.setColor(Theme.used.backgroundColor.brighter());
        img.fill();
        
        img.setColor(Theme.used.textColor);
        img.drawRect(1, 1, img.getWidth() - 3, img.getHeight() - 3);
        img.drawString(displayedText, (img.getWidth() - textWidth) / 2 + 2, fm.getMaxAscent());
        
        displayer.setImage(img);
        
    }
    
    /**
     * Shows the Displayer.
     */
    private static void show() {
        MyWorld.theWorld.addObject(displayer, 30 + displayer.getImage().getWidth() / 2, 30 + displayer.getImage().getHeight() / 2);
        Selector.setValidator(Selector.IS_OWNED_TERRITORY);
    }
    
    /**
     * Hides the Displayer.
     */
    private static void hide() {
        MyWorld.theWorld.removeObject(displayer);
        Selector.setValidator(Selector.EVERYTHING);
        Mode.setMode(Mode.GAME_DEFAULT);
    
    }
    
    /**
     * Updates the displayer's image after the Player placed armies.
     */
    public static void update(){
        if(Turn.currentTurn != null) {
            if(Mode.mode() == Mode.CLEARING_HAND && Turn.currentTurn.player.armiesInHand() > 0) {
                updateImage(Turn.currentTurn.player.armiesInHand());
                show();
            } else if(Mode.mode() == Mode.CLEARING_HAND) {
                hide();
            }
        }
        
    }
    
}

class Display extends Actor {}
