package game;

import appearance.Appearance;
import appearance.Theme;
import base.Button;
import base.Game;
import base.MyWorld;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import java.util.ArrayList;
import mainObjects.Player;
import mainObjects.Territory;

public class Turn {
    
    private static Player currentPlayer;
    private static int currentPlayerNumber = 0;

    private static Game game(){return MyWorld.theWorld.stateManager.game();}
    
    public static void start() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static void nextTurn(){
        
        if(aPlayerIsDead()){
            game().end();
        }else{
            
            if(currentPlayerNumber == game().players.size()){
                
                zombieTurn();
                currentPlayerNumber = 0;
                
            }else{
                
                currentPlayer = game().players.get(currentPlayerNumber);
                currentPlayerNumber++;
                //(new NextTurnPanel(currentPlayer)).show();
                
            }
            
        }
        
        
    }
    
    static public boolean aPlayerIsDead(){
        
        ArrayList<Player> playersAlive = new ArrayList<Player>();
        
        for(Territory t : game().map.territories){
            
            if(t.owner() != null && !playersAlive.contains(t.owner())){
                playersAlive.add(t.owner());
            }
            
        }
        
        return playersAlive.size() != game().players.size();
        
    }

    private static void zombieTurn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    class NextTurnPanel extends Button{
        
        private final Player OWNER;
        
        public NextTurnPanel(Player player){
            OWNER = player;
        }
        
        public void show(){
            GreenfootImage img = new GreenfootImage(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT);
            img.setColor(Theme.used.backgroundColor);
            img.fill();
            img.setColor(OWNER.color());
            img.setFont(new Font("monospaced", 25));
            //img.drawString(OWNER.name(), 1000, 500);
            MyWorld.theWorld.addObject(this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
            
        }
        
        @Override
        public void clicked() {
            MyWorld.theWorld.removeObject(this);
            OWNER.startTurn();
        }
        
    }
    
}
