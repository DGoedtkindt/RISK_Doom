package game;

import base.GColor;
import base.MyWorld;
import greenfoot.Greenfoot;
import java.util.ArrayList;
import territory.Attack;
import territory.Territory;

/**
 * The Class of the Zombie Player.
 * 
 */
public class Zombie extends Player{
    
    public static final GColor ZOMBIE_COLOR = new GColor(0,0,0);
    
    private final Difficulty DIFFICULTY; 
    private int zombiesNextWave;
    private int turnsBeforeNextWave;

    /**
     * Creates the Zombie of a given Difficulty.
     * @param difficulty The Difficulty of this Zombie.
     */
    public Zombie(Difficulty difficulty) {
        super("H1N1XX",ZOMBIE_COLOR);
        DIFFICULTY = difficulty;
        zombiesNextWave = difficulty.ZOMBIES_SPAWNING;
        turnsBeforeNextWave = difficulty.ZOMBIES_TURN_LIMIT;
    }
    
    /**
     * Gets the number of Zombie Territories that appear during the next wave.
     * @return The number of Zombies of the next wave.
     */
    public int ZombiesNextWave() {
        return zombiesNextWave;
    }
    
    /**
     * Gets the number of Turns before the next wave.
     * @return The number of Turns before the next wave.
     */
    public int TurnsBeforeNextWave() {
        return turnsBeforeNextWave;
    }
    
    /**
     * The Zombie plays his Turn.
     */
    public void takeTurn() {
        
        if(territories().isEmpty()){
            takeTerritoriesRandomly();
        }
        attackRandomly();
        incrementNextWave();
        
        if(Turn.currentTurn.player == this){
            Turn.endCurrentTurn();
            Turn.startNewTurn();
        }
        
    }
    
    /**
     * Invades random Territories.
     */
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
    
    /**
     * Attacks random Territories.
     */
    private void attackRandomly(){
        for(Territory t : territories()){
            if(Math.random() < DIFFICULTY.ATTACK_CHANCE){
                Attack attack = new Attack(this);
                attack.attacking = t;
                attack.randomAttack();

            }
            
        }
        
    }
    
    /**
     * Lowers the number of Turns before the next wave.
     */
    public void countdown(){
        turnsBeforeNextWave --;
        if(turnsBeforeNextWave == -1){
            turnsBeforeNextWave = DIFFICULTY.ZOMBIES_TURN_LIMIT;
            takeTurn();
        }
    }
    
    /**
     * Increments the number of Zombies of the next wave.
     */
    public void incrementNextWave(){
        zombiesNextWave += DIFFICULTY.INCREMENT;
    }

    /**
     * Resets the number of Zombies of the next wave.
     */
    public void reset(){
        zombiesNextWave = DIFFICULTY.ZOMBIES_SPAWNING;
    }
    
    @Override
    public boolean hasLost() {
        return false; //Zombies always come back!
    
    }
    

}
