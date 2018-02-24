package game;

import appearance.Appearance;
import base.Button;
import base.Game;
import base.MyWorld;
import greenfoot.Color;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import java.util.List;
import mode.Mode;
import selector.Selector;

public class Turn {
    public static Turn currentTurn;
        
    private static Game game(){return MyWorld.theWorld.stateManager.game();}
    private static List<Player> players() {return game().players;}
    
    
    public boolean hasGainedCombo = false;
    public Player player;
    
    protected int turnNumber;
    
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
        
        if(player instanceof Zombie){
            ((Zombie)player).takeTurn();
        }else{
            Mode.setMode(Mode.CLEARING_HAND);
            player.getArmies();
            ArmiesInHandDisplayer.show(player);
            Selector.setValidator(Selector.IS_OWNED_TERRITORY);
        }
            
        
    }
    
    public void end() {
        Zombie.ZOMBIE.countdown();
    
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
