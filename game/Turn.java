package game;

import base.Action;
import base.Game;
import base.MyWorld;
import java.util.List;
import mode.Mode;

public class Turn {
    public static Turn currentTurn;
        
    private static Game game(){return MyWorld.theWorld.stateManager.game();}
    private static List<Player> players() {return game().players;}
    public Player player;
    
    protected int turnNumber;
    
    protected Turn(int turnNumber) {
        this.turnNumber = turnNumber;
        int playerNumber = turnNumber % (players().size());
        player = players().get(playerNumber);
        
    }
    
    public static void endCurrentTurn() {
        currentTurn.end();
        
    }
    
    public static void interruptCurrentTurn() {
        currentTurn.unlockMode();
        currentTurn = null;
    
    }
    
    public static void startNewTurn() {
        if(currentTurn != null) {
            int newTurnNumber = currentTurn.turnNumber + 1;
            startNewTurn(newTurnNumber);
        } else {
            startNewTurn(1);
            System.err.println("Turn.currentTurn was not initialized by the manager"
                    + " before calling Turn.startNewTurn. This should not happen");
        }
    }
    
    protected static void startNewTurn(int turnNumber) {
        currentTurn = new Turn(turnNumber);
        currentTurn.showNextTurnPanel();
    }
    
    
    private void showNextTurnPanel() {
        new NextTurnPanel(this).show();
        

    }
    
    public void start(){
        lockModeToClearHand();
        ComboDisplayer.display();
        
        if(player instanceof Zombie){
            ((Zombie)player).takeTurn();
        }else{
            player.updateCapital();
            player.getArmies();
            Mode.setMode(Mode.CLEARING_HAND);
            
        }
            
        
    }
    
    public void end() {
        unlockMode();
        if(player.conqueredThisTurn) {
            player.gainComboPiece();
        }
        player.conqueredThisTurn = false;
        
        addTurnStats();
        autoSave();
        
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
    
    private void addTurnStats() {
        TurnStat turnStat = new TurnStat(players(), turnNumber);
        MyWorld.theWorld.stateManager.game().stats.add(turnStat);
    }
    
    private void autoSave() {
        new GameSaver(MyWorld.theWorld.stateManager.game()).autoSave();
        
    }
    
    private Action lockModeToClearingHand = () -> {
        if(player.armiesInHand() > 0 & Mode.mode() != Mode.CLEARING_HAND) 
            Mode.setMode(Mode.CLEARING_HAND);
        
    };

    private void lockModeToClearHand() {
        Mode.addModeChangeListener(lockModeToClearingHand);
    }
    
    private void unlockMode() {
        Mode.removeModeChangeListener(lockModeToClearingHand);
    
    }
    
}
