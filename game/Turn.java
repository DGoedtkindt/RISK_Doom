package game;

import base.Action;
import base.Game;
import base.MyWorld;
import java.util.List;
import mode.Mode;

/**
 * This Class represents the Turns of the Game.
 * 
 */
public class Turn {
    public static Turn currentTurn;
        
    private static Game game(){return MyWorld.theWorld.stateManager.game();}
    private static List<Player> players() {return game().players;}
    public Player player = null;
    
    protected int turnNumber;
    
    /**
     * Creates a Turn object.
     * @param turnNumber The number of this Turn.
     */
    protected Turn(int turnNumber) {
        this.turnNumber = turnNumber;
        int playerNumber = turnNumber % (players().size());
        player = players().get(playerNumber);
        
    }
    
    /**
     * Ends the Turn being currently played.
     */
    public static void endCurrentTurn() {
        currentTurn.end();
        
    }
    
    /**
     * Interrupts the current Turn while the User changes the Options.
     */
    public static void interruptCurrentTurn() {
        currentTurn.unlockMode();
        currentTurn = null;
    
    }
    
    /**
     * Starts a new Turn by creating a new Turn object for the next Turn.
     */
    public static void startNewTurn() {
        if(currentTurn != null) {
            int newTurnNumber = currentTurn.turnNumber + 1;
            startNewTurn(newTurnNumber);
        } else {
            startNewTurn(0);
            System.err.println("Turn.currentTurn was not initialized by the manager"
                    + " before calling Turn.startNewTurn. This should not happen");
        }
    }
    
    /**
     * Starts a new Turn with a given Turn number.
     * @param turnNumber The specific Turn number.
     */
    protected static void startNewTurn(int turnNumber) {
        currentTurn = new Turn(turnNumber);
        currentTurn.showNextTurnPanel();
    }
    
    /**
     * Shows a NextTurnPanel for the next Turn.
     */
    private void showNextTurnPanel() {
        new NextTurnPanel(this).show();
    }
    
    /**
     * Starts a Turn.
     */
    public void start(){
        lockModeToClearHand();
        ComboDisplayer.display();
        
        if(player instanceof Zombie){
            ((Zombie)player).takeTurn();
        }else{
            player.startTurn();
            Mode.setMode(Mode.CLEARING_HAND);
            
        }
            
        
    }
    
    /**
     * Ends a Turn.
     */
    public void end() {
        unlockMode();
        if(player.conqueredThisTurn) {
            player.gainComboPiece();
        }
        player.conqueredThisTurn = false;
        
        addTurnStats();
        autoSave();
        
        if(aPlayerIsDead() != null || aPlayerWon() != null){
            (new EndGamePanel()).show();
        }
        
    }
    
    /**
     * Checks if a Player is dead.
     * @return the dead Player, or null if no Player died.
     */
    public static Player aPlayerIsDead(){
        for(Player p : players()) {
            if(p.hasLost()) {
                return p;
            
            }
        
        }
        
        return null;
        
    }
    
    /**
     * Checks if a Player won the Game.
     * @return The winning Player, or null if no Player won.
     */
    public static Player aPlayerWon(){
        for(Player p : players()) {
            if(p.hasWon()) {
                return p;
            
            }
        
        }
        
        return null;
        
    }
    
    /**
     * Creates a TurnStats object for this Turn.
     */
    private void addTurnStats() {
        TurnStat turnStat = new TurnStat(players(), turnNumber);
        MyWorld.theWorld.stateManager.game().stats.add(turnStat);
    }
    
    /**
     * Automatically saves the Game.
     */
    private void autoSave() {
        new GameSaver(MyWorld.theWorld.stateManager.game()).autoSave();
        
    }
    
    private Action lockModeToClearingHand = () -> {
        if(player.armiesInHand() > 0 & Mode.mode() != Mode.CLEARING_HAND) 
            Mode.setMode(Mode.CLEARING_HAND);
        
    };
    
    /**
     * Prevents the Player to quit the Mode in wich he places his armies.
     */
    private void lockModeToClearHand() {
        Mode.addModeChangeListener(lockModeToClearingHand);
    }
    
    /**
     * Allows the Player to quit the Mode in wich he places his armies.
     * The consequence of this method is the end of this Mode.
     */
    private void unlockMode() {
        Mode.removeModeChangeListener(lockModeToClearingHand);
    
    }
    
}
