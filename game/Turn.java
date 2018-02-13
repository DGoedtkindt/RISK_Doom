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
    
    public static Player currentPlayer;
    private static int currentPlayerNumber = 0;

    private static Game game(){return MyWorld.theWorld.stateManager.game();}
    
    public static void nextTurn(){
        
        if(((game.Manager)(MyWorld.theWorld.stateManager)).usedDifficulty.turnsBeforeNextWave == 0){
            
            zombieTurn();
            
        }else{
            
            if(aPlayerIsDead()){
                //game().end();
            }else{
                
                if(currentPlayerNumber == game().players.size()){
                    
                    zombieTurn();
                    currentPlayerNumber = 0;
                    ((game.Manager)(MyWorld.theWorld.stateManager)).usedDifficulty.incrementNextWave();
                    
                }else{
                    ((game.Manager)(MyWorld.theWorld.stateManager)).usedDifficulty.countdown();
                    currentPlayer = game().players.get(currentPlayerNumber);
                    currentPlayerNumber++;
                    (new NextTurnPanel(currentPlayer)).show();
                    
                }
                
            }
            
        }
        
    }
    
    static public boolean aPlayerIsDead(){
        
        ArrayList<Player> playersAlive = new ArrayList<Player>();
        
        for(Territory t : game().map.territories){
            
            if(t.owner() != null && !t.owner().name().equals(Player.ZOMBIE_NAME) && !playersAlive.contains(t.owner())){
                playersAlive.add(t.owner());
            }
            
        }
        
        return playersAlive.size() != game().players.size() - 1;
        
    }

    private static void zombieTurn() {
        
    }
    
    static class NextTurnPanel extends Button{
        
        private final Player OWNER;
        
        public NextTurnPanel(Player player){
            OWNER = player;
        }
        
        public void show(){
            GreenfootImage img = new GreenfootImage(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT);
            img.setColor(Theme.used.backgroundColor.brighter());
            img.fill();
            img.setColor(OWNER.color());
            img.setFont(new Font("monospaced", 50));
            img.drawString(OWNER.name(), 700, 500);
            setImage(img);
            MyWorld.theWorld.addObject(this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
            
        }
        
        @Override
        public void clicked() {
            MyWorld.theWorld.removeObject(this);
            OWNER.startTurn();
        }
        
    }
    
}
