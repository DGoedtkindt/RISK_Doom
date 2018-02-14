package game;

import appearance.Appearance;
import base.Button;
import base.Game;
import base.MyWorld;
import greenfoot.Color;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import java.util.ArrayList;
import mainObjects.Player;
import mainObjects.Zombie;

public class Turn {
    private ArrayList<Player> players() {return game().players;}
    
    public Player player;
    protected int turnNumber;
    
    public static Turn currentTurn;

    private static Game game(){return MyWorld.theWorld.stateManager.game();}
    
    protected Turn(int turnNumber) {
        this.turnNumber = turnNumber;
        int playerNumber = turnNumber % (players().size());
        player = players().get(playerNumber);
        
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
        
        new NextTurnPanel(player).show();
        
        if(player instanceof Zombie){
            ((Zombie)player).takeTurn();

        }else{;
            //do stuff

        }
            
        
    }
    
    public void end() {
        System.out.println("Turn.end() doesn't do anything yet");
    
    }
    
    private boolean aPlayerIsDead(){
        for(Player p : players()) {
            if(p.hasLostQ()) {
                return true;
            
            }
        
        }
        
        return false;
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
            img.drawString(OWNER.name(), 700, 500);
            setImage(img);
            MyWorld.theWorld.addObject(this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
            
        }
        
        @Override
        public void clicked() {
            MyWorld.theWorld.removeObject(this);
            
        }
        
    }
