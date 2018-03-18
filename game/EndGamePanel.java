package game;

import appearance.Appearance;
import base.GColor;
import base.MyWorld;
import base.NButton;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import mode.Mode;

/** 
 * This Class represents the Panel that appears at the end of the Game.
 */
public class EndGamePanel {
    
    private String message;
    private Actor panel = new Actor() {};
    private NButton backToMenu = new NButton(() -> {MyWorld.theWorld.load(new menu.Manager());}, "Main Menu");
    
    /**
     * Paints the Background.
     */
    private void colorBackground() {
        panel.getImage().setColor(new GColor(0, 0, 0, 150));
        panel.getImage().fill();
    
    }
    
    /**
     * Shows the Panel.
     */
    public void show() {
        panel.setImage(new GreenfootImage(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT));
        colorBackground();
        writeMessage();
        Mode.setMode(Mode.DEFAULT);
        addToWorld();
        
    }
    
    /**
     * Writes the correct message on the Panel.
     */
    private void writeMessage() {
        panel.getImage().setColor(GColor.WHITE);
        panel.getImage().setFont(Appearance.GREENFOOT_FONT);
        
        FontMetrics fm = panel.getImage().getAwtImage().getGraphics().getFontMetrics(Appearance.AWT_FONT);
        
        int lineHeight = fm.getMaxAscent() + fm.getMaxDescent();
        int maxWidth;
        int linesNumber = MyWorld.theWorld.stateManager.game().players.size();
        int lineNumber = 2;
        
        if(Turn.aPlayerIsDead() != null){
            message = "The Player " + Turn.aPlayerIsDead().name() + " died horribly.";
        }else if(Turn.aPlayerWon() != null){
            message = "The Player " + Turn.aPlayerWon().name() + " won the Game!";
        }
        
        maxWidth = fm.stringWidth(message);
        
        //Sorts the list of Players by their number of points.
        ArrayList<Player> sortedPlayers = new ArrayList<Player>();
        sortedPlayers.addAll(MyWorld.theWorld.stateManager.game().players);
        for(Player player : sortedPlayers){
            if(player instanceof Zombie){
                sortedPlayers.remove(player);
                break;
            }
        }
        
        Collections.sort(sortedPlayers, new Comparator<Player>() {
            @Override
            public int compare(Player t, Player t1) {
                return t.points - t1.points;
            }
        });
        
        Collections.reverse(sortedPlayers);
        
        for(Player p : sortedPlayers){
            
            if(fm.stringWidth(p.name() + " : " + p.points) > maxWidth){
                
                maxWidth = fm.stringWidth(p.name() + " : " + p.points);
                
            }
            
        }
        
        int baseX = (panel.getImage().getWidth() - maxWidth) / 2;
        int baseY = (panel.getImage().getHeight() - (linesNumber - 1) * lineHeight) / 2;
        
        panel.getImage().drawString(message, baseX, baseY);
        
        for(Player toDraw : sortedPlayers){
            
            panel.getImage().setColor(toDraw.color());
            panel.getImage().drawString(toDraw.name() + " : " + toDraw.points, baseX, baseY + lineNumber * lineHeight);
            
            lineNumber ++;
            
        }
        
    }
    
    /**
     * Adds the Panel to the world.
     */
    private void addToWorld() {
        MyWorld.theWorld.addObject(panel, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        MyWorld.theWorld.addObject(backToMenu, Appearance.WORLD_WIDTH / 2, 3 * Appearance.WORLD_HEIGHT / 4);
    }
    
}
