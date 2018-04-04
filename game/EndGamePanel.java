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
    private final Actor PANEL = new Actor() {};
    private final NButton BACK_TO_MENU = new NButton(() -> {MyWorld.theWorld.load(new menu.Manager());}, "Main Menu");
    
    /**
     * Paints the Background.
     */
    private void colorBackground() {
        PANEL.getImage().setColor(new GColor(0, 0, 0, 150));
        PANEL.getImage().fill();
    
    }
    
    /**
     * Shows the Panel.
     */
    public void show() {
        PANEL.setImage(new GreenfootImage(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT));
        colorBackground();
        writeMessage();
        Mode.setMode(Mode.DEFAULT);
        addToWorld();
        
    }
    
    /**
     * Writes the correct message on the Panel.
     */
    private void writeMessage() {
        PANEL.getImage().setColor(GColor.WHITE);
        PANEL.getImage().setFont(Appearance.GREENFOOT_FONT);
        
        FontMetrics fm = PANEL.getImage().getAwtImage().getGraphics().getFontMetrics(Appearance.AWT_FONT);
        
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
        
        int baseX = (PANEL.getImage().getWidth() - maxWidth) / 2;
        int baseY = (PANEL.getImage().getHeight() - (linesNumber - 1) * lineHeight) / 2;
        
        PANEL.getImage().drawString(message, baseX, baseY);
        
        for(Player toDraw : sortedPlayers){
            
            PANEL.getImage().setColor(toDraw.color);
            PANEL.getImage().drawString(toDraw.name() + " : " + toDraw.points, baseX, baseY + lineNumber * lineHeight);
            
            lineNumber ++;
            
        }
        
    }
    
    /**
     * Adds the Panel to the World.
     */
    private void addToWorld() {
        MyWorld.theWorld.addObject(PANEL, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        MyWorld.theWorld.addObject(BACK_TO_MENU, Appearance.WORLD_WIDTH / 2, 3 * Appearance.WORLD_HEIGHT / 4);
    }
    
}
