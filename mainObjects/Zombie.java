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
        reset();
        
    }
    
    public int zombiesNextWave() {
        return zombiesNextWave;
    }

    public int turnsBeforeNextWave() {
        return turnsBeforeNextWave;
    }
    
    public void takeTurn() {
        //actions the zombie takes
        turnsBeforeNextWave --;
        if(turnsBeforeNextWave <= 0) {
            propagate();
            turnsBeforeNextWave = difficulty.ZOMBIES_TURN_LIMIT;
            zombiesNextWave += difficulty.INCREMENT;
        
        }
        
        Turn.endCurrentTurn();
        Turn.startNewTurn();
    }

    private void reset(){
        zombiesNextWave = difficulty.ZOMBIES_SPAWNING;
        turnsBeforeNextWave = difficulty.ZOMBIES_TURN_LIMIT;
        
    }
    
    @Override
    public boolean hasLostQ() {
        return false; //zombies always come back!
    
    }

    private void propagate() {
        System.out.println("Method propagate() in class Zombie is not supported yet");
    }
    

}
