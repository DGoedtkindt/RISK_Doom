package mainObjects;

import base.GColor;
import game.Difficulty;
import game.Turn;


public class Zombie extends Player{
    
    public static final GColor ZOMBIE_COLOR = new GColor(0,0,0);
    
    private Difficulty difficulty; 
    private int zombiesNextWave;
    private int turnsBeforeNextWave;

    public Zombie(Difficulty difficulty) {
        super("H1N1XX",ZOMBIE_COLOR);
        this.difficulty = difficulty;
        zombiesNextWave = difficulty.ZOMBIES_SPAWNING;
        turnsBeforeNextWave = difficulty.ZOMBIES_TURN_LIMIT;
    }
    
    public int ZombiesNextWave() {
        return zombiesNextWave;
    }

    public int TurnsBeforeNextWave() {
        return turnsBeforeNextWave;
    }
    
    public void takeTurn() {
        System.out.println("Zombie.takeTurn() is not supported yet");
        Turn.endCurrentTurn();
        Turn.startNewTurn();
    }
    
    public void countdown(){
        turnsBeforeNextWave --;
        if(turnsBeforeNextWave == -1){
            turnsBeforeNextWave = difficulty.ZOMBIES_TURN_LIMIT;
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
