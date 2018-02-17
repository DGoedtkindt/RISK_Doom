package game;

import appearance.Appearance;
import appearance.ArmiesInHandDisplayer;
import appearance.ComboDisplayer;
import base.Button;
import base.Game;
import base.MyWorld;
import greenfoot.Color;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import java.util.List;
import mainObjects.Player;
import mainObjects.Zombie;
import mode.Mode;
import selector.Selector;

public class Turn {
    
    public boolean hasGainedCombo = false;
    
    public Player player;
    protected int turnNumber;
    
    public static Turn currentTurn;
    private static Game game(){return MyWorld.theWorld.stateManager.game();}
    private static List<Player> players() {return game().players;}
    
    protected Turn(int turnNumber) {
        this.turnNumber = turnNumber;
        int playerNumber = turnNumber % (players().size());
        player = players().get(playerNumber);
        hasGainedCombo = false;
        
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
        new NextTurnPanel(this).show();
        
    }
    
    public void start(){
    
        if(player instanceof Zombie){
            ((Zombie)player).takeTurn();
        }else{
            System.out.println("yo, i'm no zombie");
            Mode.setMode(Mode.CLEARING_HAND);
            player.getArmies();
            ArmiesInHandDisplayer.show(player);
            Selector.setValidator(Selector.IS_OWNED_TERRITORY);
            ComboDisplayer.displayCombos(player);
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
    private static Game game(){return MyWorld.theWorld.stateManager.game();}
    private static List<Player> players() {return game().players;}
        
    private final Player OWNER;
    private final Turn TURN;
    
    private TurnStat stats;

    public NextTurnPanel(Turn turn){
        TURN = turn;
        OWNER = TURN.player;
        stats = new TurnStat(players(),TURN.turnNumber);
        setImage(new GreenfootImage(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT));
    }

    public void show(){
        colorBackground();
        writeName();
        writeStats();
        addToWorld();

    }
    
    private void colorBackground() {
        getImage().setColor(OWNER.color());
        getImage().fill();
    
    }
    
    private void writeName() {
        if(OWNER.color().luminosity() > 128) {
            getImage().setColor(Color.BLACK);
        } else {
            getImage().setColor(Color.WHITE);
        }
        getImage().setFont(new Font("monospaced", true, false, 50));
        getImage().drawString(OWNER.name(), 700, 500);
    
    }
    
    private void writeStats() {
        if(OWNER.color().luminosity() > 128) {
            getImage().setColor(Color.BLACK);
        } else {
            getImage().setColor(Color.WHITE);
        }
        
        String infos = "";
        String armies = "Total number of armies : " 
                + stats.numberOfArmies.get(OWNER);
        String territories = "Number of territories owned : " 
                + stats.numberOfTerritories.get(OWNER);
        String armiesPerTurn = "Armies in reinforcement this turn : "
                + stats.numberOfArmiesPerTurn.get(OWNER);
        
        infos += armies + "\n";
        infos += territories + "\n";
        infos += armiesPerTurn + "\n";
        
        getImage().setFont(new Font("monospaced", true, false, 25));
        getImage().drawString(infos, 600, 600);
    }
    
    public void addToWorld() {
        MyWorld.theWorld.addObject(this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
    
    }

    @Override
      public void clicked() {
          MyWorld.theWorld.removeObject(this);
          TURN.start();
          
      }

}

