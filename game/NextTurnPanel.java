package game;

import appearance.Appearance;
import base.Action;
import base.GColor;
import base.Game;
import base.MyWorld;
import base.NButton;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import java.util.List;

/**
 * A Button showing informations about the next Turn.
 * 
 */
public class NextTurnPanel {
    
    private static Game game(){return MyWorld.theWorld.stateManager.game();}
    private static List<Player> players() {return game().players;}
        
    private final Player OWNER;
    private final Turn TURN;
    
    private final TurnStat STATS;
    
    private final NButton SAVE_BUTTON;
    private final NButton PANEL;

    /**
     * Creates a NextTurnPanel.
     * @param turn The Turn to be played.
     */
    public NextTurnPanel(Turn turn){
        TURN = turn;
        OWNER = TURN.player;
        SAVE_BUTTON = new NButton(SHOW_SAVE_GAME_DIALOG,"Save Game");
        PANEL = new NButton(() -> {
            removeFromWorld();
            TURN.start();
        });
        STATS = new TurnStat(players(),TURN.turnNumber);
        PANEL.setImage(new GreenfootImage(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT));
        
    }
    
    /**
     * Shows the Panel.
     */
    public void show(){
        colorBackground();
        writeName();
        writeStats();
        writePoints();
        addToWorld();

    }
    
    /**
     * Paints the background image of this Panel.
     */
    private void colorBackground() {
        GColor color = OWNER.color();
        GColor transparentColor = new GColor(color.getRed(),color.getGreen(),color.getBlue(),220);
        PANEL.getImage().setColor(transparentColor);
        PANEL.getImage().fill();
    
    }
    
    /**
     * Writes the name of the current Player on the Panel.
     */
    private void writeName() {
        if(OWNER.color().luminosity() > 128) {
            PANEL.getImage().setColor(GColor.BLACK);
        } else {
            PANEL.getImage().setColor(GColor.WHITE);
        }
        PANEL.getImage().setFont(new Font("monospaced", true, false, 50));
        PANEL.getImage().drawString(OWNER.name(), 700, 500);
    
    }
    
    /**
     * Write the current Player's stats on the Panel.
     */
    private void writeStats() {
        if(OWNER.color().luminosity() > 128) {
            PANEL.getImage().setColor(GColor.BLACK);
        } else {
            PANEL.getImage().setColor(GColor.WHITE);
        }
        
        String infos = "";
        String armies = "Total number of armies : " 
                + STATS.numberOfArmies.get(OWNER);
        String territories = "Number of territories owned : " 
                + STATS.numberOfTerritories.get(OWNER);
        String armiesPerTurn = "Armies in reinforcement this turn : "
                + (STATS.numberOfArmiesPerTurn.get(OWNER) + OWNER.armiesInHand());
        
        infos += armies + "\n";
        infos += territories + "\n";
        infos += armiesPerTurn + "\n";
        
        PANEL.getImage().setFont(new Font("monospaced", true, false, 25));
        PANEL.getImage().drawString(infos, 600, 600);
    }
    
    /**
     * Adds the Panel to the World.
     */
    public void addToWorld() {
        MyWorld.theWorld.addObject(PANEL, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        MyWorld.theWorld.addObject(SAVE_BUTTON, Appearance.WORLD_WIDTH - 100, 60);
    
    }
    
    /**
     * Removes the Panel from the World.
     */
    private void removeFromWorld() {
        MyWorld.theWorld.removeObject(PANEL);
        MyWorld.theWorld.removeObject(SAVE_BUTTON);
    }
      
    private final Action SHOW_SAVE_GAME_DIALOG = ()->{
        new GameSaver(MyWorld.theWorld.stateManager.game()).askForSaveInfo();
    
    };
    
    /**
     * Writes the points on the Panel.
     */
    private void writePoints() {
        PANEL.getImage().setFont(new Font("monospaced", true, false, 25));
        java.awt.Font awtFont = new java.awt.Font("monospaced", java.awt.Font.BOLD, 25);
        FontMetrics fm = PANEL.getImage().getAwtImage().getGraphics().getFontMetrics(awtFont);
        
        int lineSize = fm.getMaxAscent() + fm.getMaxDescent();
        int baseY = 500;
        int xPos = 10;
        int lineNumber = 1;
        
        PANEL.getImage().setColor(GColor.WHITE);
        PANEL.getImage().drawString("- Points -", xPos, baseY);
        
        for(Player p : MyWorld.theWorld.stateManager.game().players){
            
            if(!(p instanceof Zombie)){
                
                if(p != OWNER){
                    PANEL.getImage().setColor(p.color());
                }else{
                    PANEL.getImage().setColor(GColor.WHITE);
                }
                
                PANEL.getImage().drawString(p.name() + " : " + p.points, xPos, baseY + lineNumber * lineSize);
                lineNumber++;
                
            }
            
        }
        
    }

        
}
