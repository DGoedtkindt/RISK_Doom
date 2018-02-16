package appearance;

import base.MyWorld;
import greenfoot.Actor;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import mainObjects.Player;
import mode.Mode;
import selector.Selector;

/**
 * Classe de l'objet qui indique le nombre d'armées que le joueur 
 * peut placer depuis sa main au début de son tour.
 */
public class ArmiesInHandDisplayer extends Actor{
    
    private static ArmiesInHandDisplayer current;
    
    public int armies = 0;
    public final Player PLAYER;
    
    private ArmiesInHandDisplayer(Player p){
        
        PLAYER = p;
        armies = p.armiesInHand;
        createImage("" + armies);
        
    }
    
    /**
     * Méthode qui donne à l'objet une image correspondant au nombre d'armées du joueur.
     * @param armiesString Une chaîne de caractères représentant le nombre d'armées du 
     *        joueur
     */
    private void createImage(String armiesString){
        
        Font f = new Font("monospaced", true, false, 50);
        
        GreenfootImage img = new GreenfootImage(1, 1);
        img.setFont(f);
        FontMetrics fm = img.getAwtImage().getGraphics().getFontMetrics(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 50));
        img.scale(fm.stringWidth(armiesString), fm.getMaxAscent() + fm.getMaxDescent());
        img.setColor(Theme.used.backgroundColor.brighter());
        img.fill();
        img.setColor(Theme.used.textColor);
        img.drawRect(1, 1, img.getWidth() - 3, img.getHeight() - 3);
        img.drawString(armiesString, 0, fm.getMaxAscent());
        setImage(img);
        
    }
    
    /**
     * Méthode permettant de créer un objet ArmiesInHandDisplayer.
     * @param p Le joueur dont c'est le tour
     */
    public static void show(Player p){
        
        current = new ArmiesInHandDisplayer(p);
        MyWorld.theWorld.addObject(current, current.getImage().getWidth() / 2, current.getImage().getHeight() / 2);
        
    }
    
    /**
     * Méthode permettant à un objet d'être mis à jour en fonction
     * des actions du joueur.
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
