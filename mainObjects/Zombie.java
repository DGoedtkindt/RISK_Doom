package mainObjects;

import base.GColor;
import base.MyWorld;
import game.Difficulty;
import game.Turn;
import greenfoot.Greenfoot;
import java.util.List;


public class Zombie extends Player{
    
    public static final GColor ZOMBIE_COLOR = new GColor(0,0,0);
    
    private Difficulty difficulty; 
    private int zombiesNextWave;
    private int turnsBeforeNextWave;

    public Zombie(Difficulty difficulty) {
        super("H1N1XX",ZOMBIE_COLOR);
        this.difficulty = difficulty;
        reset();

    }
    
    public int zombiesNextWave() {
        return zombiesNextWave;
    }

    public int turnsBeforeNextWave() {
        return turnsBeforeNextWave;
    }
    
    public void takeTurn() {
        
        if(territories().isEmpty()){
            propagate();
        }
        attackRandomly();
        countdown();
        incrementNextWave();
        Turn.endCurrentTurn();
        Turn.startNewTurn();
        
    }
    
    private void propagate(){
        
        int terrs = 0;
        
        Territory t;
        
        List<Territory> territories = MyWorld.theWorld.stateManager.map().territories;
        
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
        turnsBeforeNextWave--;
        if(turnsBeforeNextWave == 0) {
            propagate();
            turnsBeforeNextWave = difficulty.ZOMBIES_TURN_LIMIT;
            zombiesNextWave += difficulty.INCREMENT;
        }
            
    }

    private void reset(){
        zombiesNextWave = difficulty.ZOMBIES_SPAWNING;
        turnsBeforeNextWave = difficulty.ZOMBIES_TURN_LIMIT;
        
    }
    
    @Override
    public boolean hasLostQ() {
        return false; //zombies always come back!
    
    }

    private void incrementNextWave() {
        System.out.println("Method incrementNextWave() in class Zombie is not supported yet");
    }
    

}
