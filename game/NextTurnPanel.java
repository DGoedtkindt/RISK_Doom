package game;

import appearance.Appearance;
import base.Action;
import base.GColor;
import base.Game;
import base.MyWorld;
import base.NButton;
import greenfoot.Color;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import java.util.List;


class NextTurnPanel {
    private static Game game(){return MyWorld.theWorld.stateManager.game();}
    private static List<Player> players() {return game().players;}
        
    private final Player OWNER;
    private final Turn TURN;
    
    private TurnStat stats;
    
    private NButton saveButton;
    private NButton panel;

    public NextTurnPanel(Turn turn){
        TURN = turn;
        OWNER = TURN.player;
        this.saveButton = new NButton(showSaveGameDialog,"Save Game");
        this.panel = new NButton(() -> {
            this.removeFromWorld();
            TURN.start();
        });
        stats = new TurnStat(players(),TURN.turnNumber);
        panel.setImage(new GreenfootImage(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT));
        
    }

    public void show(){
        colorBackground();
        writeName();
        writeStats();
        addToWorld();

    }
    
    private void colorBackground() {
        GColor color = OWNER.color();
        GColor transparentColor = new GColor(color.getRed(),color.getGreen(),color.getBlue(),220);
        panel.getImage().setColor(transparentColor);
        panel.getImage().fill();
    
    }
    
    private void writeName() {
        if(OWNER.color().luminosity() > 128) {
            panel.getImage().setColor(Color.BLACK);
        } else {
            panel.getImage().setColor(Color.WHITE);
        }
        panel.getImage().setFont(new Font("monospaced", true, false, 50));
        panel.getImage().drawString(OWNER.name(), 700, 500);
    
    }
    
    private void writeStats() {
        if(OWNER.color().luminosity() > 128) {
            panel.getImage().setColor(Color.BLACK);
        } else {
            panel.getImage().setColor(Color.WHITE);
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
        
        panel.getImage().setFont(new Font("monospaced", true, false, 25));
        panel.getImage().drawString(infos, 600, 600);
    }
    
    public void addToWorld() {
        MyWorld.theWorld.addObject(panel, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        MyWorld.theWorld.addObject(saveButton, Appearance.WORLD_WIDTH - 100, 60);
    
    }
    
    private void removeFromWorld() {
        MyWorld.theWorld.removeObject(panel);
        MyWorld.theWorld.removeObject(saveButton);
    }
      
    private Action showSaveGameDialog = ()->{
        new GameSaver(MyWorld.theWorld.stateManager.game()).saveGame();
    
    };

        
}
