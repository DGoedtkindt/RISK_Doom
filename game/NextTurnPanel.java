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
    
    private TurnStat stats;
    
    private NButton saveButton;
    private NButton panel;

    /**
     * Creates a NextTurnPanel.
     * @param turn The Turn to be played.
     */
    public NextTurnPanel(Turn turn){
        TURN = turn;
        OWNER = TURN.player;
        this.saveButton = new NButton(showSaveGameDialog,"Save Game");
        this.panel = new NButton(() -> {
            this.removeFromWorld();
            TURN.start();
        });
        stats = new TurnStat(players(),TURN.turnNumber);
        panel.setImage(new GreenfootImage(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT));
        
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
        panel.getImage().setColor(transparentColor);
        panel.getImage().fill();
    
    }
    
    /**
     * Writes the name of the current Player on the Panel.
     */
    private void writeName() {
        if(OWNER.color().luminosity() > 128) {
            panel.getImage().setColor(GColor.BLACK);
        } else {
            panel.getImage().setColor(GColor.WHITE);
        }
        panel.getImage().setFont(new Font("monospaced", true, false, 50));
        panel.getImage().drawString(OWNER.name(), 700, 500);
    
    }
    
    /**
     * Write the current Player's stats on the Panel.
     */
    private void writeStats() {
        if(OWNER.color().luminosity() > 128) {
            panel.getImage().setColor(GColor.BLACK);
        } else {
            panel.getImage().setColor(GColor.WHITE);
        }
        
        String infos = "";
        String armies = "Total number of armies : " 
                + stats.numberOfArmies.get(OWNER);
        String territories = "Number of territories owned : " 
                + stats.numberOfTerritories.get(OWNER);
        String armiesPerTurn = "Armies in reinforcement this turn : "
                + (stats.numberOfArmiesPerTurn.get(OWNER) + OWNER.armiesInHand());
        
        infos += armies + "\n";
        infos += territories + "\n";
        infos += armiesPerTurn + "\n";
        
        panel.getImage().setFont(new Font("monospaced", true, false, 25));
        panel.getImage().drawString(infos, 600, 600);
    }
    
    /**
     * Adds the Panel to the World.
     */
    public void addToWorld() {
        MyWorld.theWorld.addObject(panel, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        MyWorld.theWorld.addObject(saveButton, Appearance.WORLD_WIDTH - 100, 60);
    
    }
    
    /**
     * Removes the Panel from the World.
     */
    private void removeFromWorld() {
        MyWorld.theWorld.removeObject(panel);
        MyWorld.theWorld.removeObject(saveButton);
    }
      
    private Action showSaveGameDialog = ()->{
        new GameSaver(MyWorld.theWorld.stateManager.game()).askForSaveInfo();
    
    };
    
    /**
     * Writes the points on the Panel.
     */
    private void writePoints() {
        panel.getImage().setFont(new Font("monospaced", true, false, 25));
        java.awt.Font awtFont = new java.awt.Font("monospaced", java.awt.Font.BOLD, 25);
        FontMetrics fm = panel.getImage().getAwtImage().getGraphics().getFontMetrics(awtFont);
        
        int lineSize = fm.getMaxAscent() + fm.getMaxDescent();
        int baseY = 500;
        int xPos = 10;
        int lineNumber = 1;
        
        panel.getImage().setColor(GColor.WHITE);
        panel.getImage().drawString("- Points -", xPos, baseY);
        
        for(Player p : MyWorld.theWorld.stateManager.game().players){
            
            if(!(p instanceof Zombie)){
                
                if(p != OWNER){
                    panel.getImage().setColor(p.color());
                }else{
                    panel.getImage().setColor(GColor.WHITE);
                }
                
                panel.getImage().drawString(p.name() + " : " + p.points, xPos, baseY + lineNumber * lineSize);
                lineNumber++;
                
            }
            
        }
        
    }

        
}
