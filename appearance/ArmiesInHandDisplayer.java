package appearance;

import base.MyWorld;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import mainObjects.Player;
import mode.Mode;
import selector.Selector;

/**
 * Displays the number of armies a Player can place.
 * 
 */
public class ArmiesInHandDisplayer extends Actor{
    
    private static ArmiesInHandDisplayer current;
    
    public int armies = 0;
    public final Player PLAYER;
    
    /**
     * Creates a displayer.
     * @param p The Player placing its armies.
     */
    private ArmiesInHandDisplayer(Player p){
        
        PLAYER = p;
        armies = p.armiesInHand;
        createImage("" + armies);
        
    }
    
    /**
     * Creates an image for the displayer.
     */
    private void createImage(String armiesString){
        
        GreenfootImage img = new GreenfootImage(1, 1);
        img.setFont(Appearance.GREENFOOT_FONT);
        FontMetrics fm = img.getAwtImage().getGraphics().getFontMetrics(Appearance.AWT_FONT);
        img.scale(fm.stringWidth(armiesString), fm.getMaxAscent() + fm.getMaxDescent());
        img.setColor(Theme.used.backgroundColor.brighter());
        img.fill();
        img.setColor(Theme.used.textColor);
        img.drawRect(1, 1, img.getWidth() - 3, img.getHeight() - 3);
        img.drawString(armiesString, 0, fm.getMaxAscent());
        setImage(img);
        
    }
    
    /**
     * Shows a new ArmiesInHandDisplayer.
     * @param p The Player placing its armies.
     */
    public static void show(Player p){
        
        if(p.armiesInHand > 0){
            current = new ArmiesInHandDisplayer(p);
            MyWorld.theWorld.addObject(current, current.getImage().getWidth() / 2, current.getImage().getHeight() / 2);
        }
        
    }
    
    /**
     * Updates the displayer's image after the Player placed armies.
     */
    public static void update(){
        
        current.armies = current.PLAYER.armiesInHand;
        
        if(current.armies == 0){
            MyWorld.theWorld.removeObject(current);
            Mode.setMode(Mode.GAME_DEFAULT);
            Selector.clear();
        }else{
            current.createImage("" + current.armies);
        }
        
    }
    
}
