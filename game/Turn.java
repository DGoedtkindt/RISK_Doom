package game;

import appearance.Appearance;
import base.Button;
import base.Game;
import base.MyWorld;
import greenfoot.Color;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import java.util.List;
import mainObjects.Player;
import mainObjects.Zombie;

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
    
    public static void startNewTurn() {
        if(currentTurn != null) {
            int newTurnNumber = currentTurn.turnNumber + 1;
            currentTurn = new Turn(newTurnNumber);
        } else {
            currentTurn = new Turn(1);
        }
            currentTurn.showNextTurnPanel();
    }
    
    
    private void showNextTurnPanel() {
        new NextTurnPanel().show();
        
    }
    
    public void start(){
        
        if(player instanceof Zombie){
            ((Zombie)player).takeTurn();

        }else{;
            //do stuff

        }           
        
    }
    
    private void end() {
        saveStats();
        autoSave();
        
    }
        
    private boolean aPlayerIsDead(){
        for(Player p : players()) {
            if(p.hasLostQ()) {
                return true;
            
            }
        
        }
        
        return false;
    }

    private void saveStats() {
        TurnStat stats = new TurnStat(players(),turnNumber);
        game().stats.add(stats);
    }

    private void autoSave() {
        System.out.println("Method autoSave() in class Turn is not supported yet");
        
    }

}

class NextTurnPanel extends Button{
        
    private final Player OWNER;
    private final Turn TURN;

    public NextTurnPanel(){
        TURN = Turn.currentTurn;
        OWNER = TURN.player;
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
        TURN.start();

    }

}