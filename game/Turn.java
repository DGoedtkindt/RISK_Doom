package game;

import appearance.Appearance;
import appearance.ArmiesInHandDisplayer;
import appearance.ComboDisplayer;
import base.Button;
import base.Game;
import base.MyWorld;
import greenfoot.Color;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import java.util.ArrayList;
import mainObjects.Player;
import mainObjects.Zombie;
import mode.Mode;
import selector.Selector;

public class Turn {
    private static ArrayList<Player> players() {return game().players;}
    
    public boolean hasGainedCombo = false;
    
    public Player player;
    protected int turnNumber;
    
    public static Turn currentTurn;

    private static Game game(){return MyWorld.theWorld.stateManager.game();}
    
    protected Turn(int turnNumber) {
        this.turnNumber = turnNumber;
        int playerNumber = turnNumber % (players().size());
        player = players().get(playerNumber);
        hasGainedCombo = false;
        
    }
    
    public static void endCurrentTurn() {
        currentTurn.end();
        
    }
    
    public static void startNewTurn() {
        if(currentTurn != null) {
            int newTurnNumber = currentTurn.turnNumber + 1;
            currentTurn = new Turn(newTurnNumber);
        } else {
            currentTurn = new Turn(0);
        }
            currentTurn.start();
    }
    
    public void start(){
        
        if(player instanceof Zombie){
            ((Zombie)player).takeTurn();
        }else{
            new NextTurnPanel(player).show();
        }
            
        
    }
    
    public void end() {
        Zombie.ZOMBIE.countdown();
    
    }
    
    public static Player aPlayerIsDead(){
        for(Player p : players()) {
            if(p.hasLost()) {
                return p;
            
            }
        
        }
        
        return null;
        
    }
    
    public static Player aPlayerWon(){
        for(Player p : players()) {
            if(p.hasWon()) {
                return p;
            
            }
        
        }
        
        return null;
        
    }
    
}

class NextTurnPanel extends Button{
        
        private final Player OWNER;
        
        public NextTurnPanel(Player player){
            OWNER = player;
        }
        
        public void show(){
            GreenfootImage img = new GreenfootImage(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT);
            img.setColor(OWNER.color());
            img.fill();
            img.setColor(Color.BLACK);
            img.setFont(new Font("monospaced", true, false, 50));
            FontMetrics fm = img.getAwtImage().getGraphics().getFontMetrics(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 50));
            img.drawString(OWNER.name(), (img.getWidth() - fm.stringWidth(OWNER.name())) / 2, 
                                         (img.getHeight() - fm.getMaxAscent() - fm.getMaxDescent()) / 2);
            setImage(img);
            MyWorld.theWorld.addObject(this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
            
        }
        
        @Override
        public void clicked() {
            MyWorld.theWorld.removeObject(this);
            Turn.currentTurn.player.startTurn();
            Mode.setMode(Mode.CLEARING_HAND);
            Selector.setValidator(Selector.IS_OWNED_TERRITORY);
            ArmiesInHandDisplayer.show(OWNER);
            ComboDisplayer.displayCombos(OWNER);
        }
        
}
