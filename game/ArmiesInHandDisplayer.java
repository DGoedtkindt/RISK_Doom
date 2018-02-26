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
    
    private static Actor displayer;
    
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
        
        GreenfootImage img = new GreenfootImage(1, 1);
        img.setFont(Appearance.GREENFOOT_FONT);
        FontMetrics fm = img.getAwtImage().getGraphics().getFontMetrics(Appearance.AWT_FONT);
        img.scale(fm.stringWidth(numArmies+""), fm.getMaxAscent() + fm.getMaxDescent());
        img.setColor(Theme.used.backgroundColor.brighter());
        img.fill();
        img.setColor(Theme.used.textColor);
        img.drawRect(1, 1, img.getWidth() - 3, img.getHeight() - 3);
        img.drawString(numArmies + "", 0, fm.getMaxAscent());
        displayer.setImage(img);
        
    }
    
    private static void show() {
        MyWorld.theWorld.addObject(displayer, 30, 30);
        Selector.setValidator(Selector.IS_OWNED_TERRITORY);
    }
    
    private static void hide() {
        MyWorld.theWorld.removeObject(displayer);
        Selector.setValidator(Selector.EVERYTHING);
        Mode.setMode(Mode.DEFAULT);
    
    }
    
    /**
     * Updates the displayer's image after the Player placed armies.
     */
    public static void update(){
        if(Turn.currentTurn != null) {
            if(Mode.mode() == Mode.CLEARING_HAND
                    & Turn.currentTurn.player.armiesInHand() > 0) {
                show();
                updateImage(Turn.currentTurn.player.armiesInHand());
            } else if(Mode.mode() == Mode.CLEARING_HAND) {
                hide();
            };
        }
        
    }
    
}

class Display extends Actor {}
