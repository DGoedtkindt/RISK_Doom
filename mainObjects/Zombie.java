package mainObjects;

import base.GColor;
import base.MyWorld;
import game.Difficulty;
import game.Turn;
import greenfoot.Greenfoot;
import java.util.ArrayList;


public class Zombie extends Player{
    
    public static Zombie ZOMBIE = null;
    
    public static final GColor ZOMBIE_COLOR = new GColor(0,0,0);
    
    private Difficulty difficulty; 
    private int zombiesNextWave;
    private int turnsBeforeNextWave;

    public Zombie(Difficulty difficulty) {
        super("H1N1XX",ZOMBIE_COLOR);
        this.difficulty = difficulty;
        zombiesNextWave = difficulty.ZOMBIES_SPAWNING;
        turnsBeforeNextWave = difficulty.ZOMBIES_TURN_LIMIT;
        ZOMBIE = this;
    }
    
    public int ZombiesNextWave() {
        return zombiesNextWave;
    }

    public int TurnsBeforeNextWave() {
        return turnsBeforeNextWave;
    }
    
    public void takeTurn() {
        
        if(territories().isEmpty()){
            takeTerritoriesRandomly();
        }
        attackRandomly();
        incrementNextWave();
        Turn.endCurrentTurn();
        Turn.startNewTurn();
        
    }
    
    private void takeTerritoriesRandomly(){
        
        int terrs = 0;
        
        Territory t;
        
        ArrayList<Territory> territories = MyWorld.theWorld.stateManager.map().territories;
        
        while(terrs < zombiesNextWave){
            
            t = territories.get(Greenfoot.getRandomNumber(territories.size()));
            
            if(t.owner() != null && t.owner() != this){
                
                t.setOwner(this);
                terrs++;
                
            }
            
        }
        
    }
    
    private void attackRandomly(){
        
        for(Territory t : territories()){
            
            if(Math.random() < difficulty.ATTACK_CHANCE){
                t.attackRandomly();

            }
            
        }
        
    }
    
    public void countdown(){
        turnsBeforeNextWave --;
        if(turnsBeforeNextWave == -1){
            turnsBeforeNextWave = difficulty.ZOMBIES_TURN_LIMIT;
            takeTurn();
        }
    }
    
    public void incrementNextWave(){
        zombiesNextWave += difficulty.INCREMENT;
    }

    public void reset(){
        zombiesNextWave = difficulty.ZOMBIES_SPAWNING;
    }
    
    public boolean hasLost() {
        return false; //zombies always come back!
    
    }
    

}
