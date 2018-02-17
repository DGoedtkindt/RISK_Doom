package appearance;

import base.MyWorld;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import mainObjects.Player;
import mode.Mode;
import selector.Selector;

public class ArmiesInHandDisplayer extends Actor{
    
    private static ArmiesInHandDisplayer current;
    
    public int armies = 0;
    public final Player PLAYER;
    
    public ArmiesInHandDisplayer(Player p){
        
        PLAYER = p;
        armies = p.armiesInHand;
        createImage("" + armies);
        
    }
    
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
    
    public static void show(Player p){
        
        if(p.armiesInHand > 0){
            current = new ArmiesInHandDisplayer(p);
            MyWorld.theWorld.addObject(current, current.getImage().getWidth() / 2, current.getImage().getHeight() / 2);
        }
        
    }
    
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
