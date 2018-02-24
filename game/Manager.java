package game;

import appearance.Appearance;
import appearance.Theme;
import base.Action;
import base.Button;
import base.Game;
import base.Map;
import base.MyWorld;
import base.NButton;
import base.StateManager;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import javax.swing.JOptionPane;
import mainObjects.Continent;
import mainObjects.Links;
import mainObjects.Territory;
import mode.Mode;
import mode.ModeMessageDisplay;
import selector.Selector;

public class Manager extends StateManager{
    
    private static final int STARTING_TERRITORIES = 4;
    
    private Game gameToLoad = new Game();
    private Game loadedGame = new Game();
    
    private ControlPanel ctrlPanel;
    private ModeMessageDisplay modeDisp;
    private NButton options;
    private ComboDisplayer comboDisplayer;
    
    public Manager(Game loadGame) {
        this.ctrlPanel = new ControlPanel(this);
        this.modeDisp = new ModeMessageDisplay();
        this.options = new NButton(loadOptionsMenu, "Options");
        this.comboDisplayer = ComboDisplayer.current();
        this.gameToLoad = loadGame;
        
    }
    
    @Override
    public Game game() {return loadedGame;}
    @Override
    public Map map() {return loadedGame.map;}
    
    @Override
    public void setupScene() {
        Mode.setMode(Mode.GAME_DEFAULT);
        world().makeSureSceneIsClear();
        world().placeBlankHexs();
        ctrlPanel.addToWorld(world().getWidth() - 100, 300);
        modeDisp.addToWorld(world().getWidth() - 90, 850);
        world().addObject(Continent.display, 840, 960);
        world().addObject(options, world().getWidth() - 120, 50);
        world().addObject(comboDisplayer, world().getWidth() - 90, 900);
        loadGame();
        for(Territory t : loadedGame.map.territories){
            t.drawTerritory();
        }
    }
    
    private void loadGame(){
        loadMap();
        loadedGame = gameToLoad;
        
        if(loadedGame.gameState == Game.State.INITIALISATION){
            start();
            loadedGame.gameState = Game.State.INGAME;
        }
        
    }
    
    private void loadMap(){
        
        gameToLoad.map.territories.forEach(Territory::addToWorld);
        gameToLoad.map.continents.forEach(Continent::addToWorld);
        gameToLoad.map.links.forEach(Links::addToWorld);
        
    }
    
    @Override
    public void clearScene() {
        
        gameToLoad = loadedGame;
        loadedGame = new Game();
        
        ctrlPanel.removeFromWorld();
        modeDisp.removeFromWorld();
        
        world().removeObjects(world().getObjects(Actor.class));
        
    }
    
    @Override
    public void escape() {
        if(Mode.mode() == Mode.GAME_DEFAULT) {

            int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the main menu?", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                clearScene();
                world().load(new menu.Manager());

            } 
        
        } else {
            Selector.clear();
            Mode.setMode(Mode.GAME_DEFAULT);
            
        }
    }
    
    private void giveTerritoriesRandomly(){
        
        for(Player p : loadedGame.players){
            
            int terrNumber = 0;
            Territory terrToAttribute;
            
            while(terrNumber < STARTING_TERRITORIES){

                terrToAttribute = loadedGame.map.territories.get(Greenfoot.getRandomNumber(loadedGame.map.territories.size()));

                if(terrToAttribute.owner() == null){
                    terrToAttribute.setOwner(p);
                    terrNumber++;
                }

            }
            
            p.getnewCapital();
            
        }
        
    }
    
    public void start(){
        giveTerritoriesRandomly();
        Turn.startNewTurn();
        
    }
    
    public void endByVictory(Player p){
        EndTurnPanel.showVictory(p);
    }
    
    public void endByDeath(Player p){
        EndTurnPanel.showDeath(p);
    }
    
    protected Action saveGame = () -> {
        (new GameSaver(game())).saveGame();
    
    };
    
    private Action loadOptionsMenu = () -> {
                if(Mode.mode() == Mode.GAME_DEFAULT){
                    clearScene();
                    world().load(new userPreferences.Manager(this));
                }};
    
}

class EndTurnPanel extends Button{
    
    private EndTurnPanel(String message){
        GreenfootImage img = new GreenfootImage(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT);
        img.setColor(Theme.used.backgroundColor.brighter());
        img.fill();
        img.setColor(Theme.used.textColor);
        img.setFont(Appearance.GREENFOOT_FONT);
        FontMetrics fm = img.getAwtImage().getGraphics().getFontMetrics();
        img.drawString(message, (Appearance.WORLD_WIDTH - fm.stringWidth(message)) / 2, (Appearance.WORLD_HEIGHT + fm.getMaxAscent() + fm.getMaxDescent()) / 2);
        setImage(img);
    }
    
    public static void showVictory(Player p){
        MyWorld.theWorld.addObject(new EndTurnPanel("Player " + p.name() + " has won"), Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
    }
    
    public static void showDeath(Player p){
        MyWorld.theWorld.addObject(new EndTurnPanel("Player " + p.name() + " has lost"), Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
    }
    
    @Override
    public void clicked() {
        world().load(new menu.Manager());
    }
    
}
