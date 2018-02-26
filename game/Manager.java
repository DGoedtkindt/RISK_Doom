package game;

import appearance.Appearance;
import base.Action;
import base.Button;
import base.Game;
import base.Map;
import base.MyWorld;
import base.NButton;
import base.StateManager;
import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Font;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
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
    
    /** Creates a new Manager that will allow to play a certain Game when 
     * setupScene() is called.
     * 
     * @param loadGame the game that will be loaded.
     */
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
        Mode.setMode(Mode.DEFAULT);
        world().placeBlankHexs();
        ctrlPanel.addToWorld(world().getWidth() - 100, 300);
        modeDisp.addToWorld(world().getWidth() - 90, 850);
        world().addObject(Continent.display, 840, 960);
        world().addObject(options, world().getWidth() - 120, 50);
        world().addObject(comboDisplayer, world().getWidth() - 90, 900);
        loadGame();
    }
    
    private void loadGame(){
        loadMap();
        loadedGame = gameToLoad;
        
        if(null != loadedGame.gameState)switch (loadedGame.gameState) {
            case INITIALISATION:
                giveTerritoriesRandomly();
                startNewTurn();
                loadedGame.gameState = Game.State.INGAME;
                break;
            case INGAME:
                startNewTurn();
                break;
            case FINISHED:
                //do nothing yet. should maybe show the stats and the EndGamePanel
                break;
            default:
                break;
        } 
        
    }
    
    private void loadMap(){
        
        gameToLoad.map.territories.forEach(Territory::addToWorld);
        gameToLoad.map.continents.forEach(Continent::addToWorld);
        gameToLoad.map.links.forEach(Links::addToWorld);
        
    }
    
    private void startNewTurn() {
        int turnNumber = 1 + loadedGame.stats.size();
        Turn.startNewTurn(turnNumber);
    
    }
    
    @Override
    public void clearScene() {
        Turn.endCurrentTurn();
        
        gameToLoad = loadedGame;
        loadedGame = new Game();
        
        ctrlPanel.removeFromWorld();
        modeDisp.removeFromWorld();
        
        world().removeObjects(world().getObjects(Actor.class));
        
    }
    
    @Override
    public void escape() {
        if(Mode.mode() == Mode.DEFAULT) {

            int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the main menu?", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                clearScene();
                world().load(new menu.Manager());

            } 
        
        } else {
            Selector.clear();
            Mode.setMode(Mode.DEFAULT);
            
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
            
            p.updateCapital();
            
        }
        
    }
    
    public void endByVictory(Player p){
        new EndGamePanel().showVictory(p);
    }
    
    public void endByDeath(Player p){
        new EndGamePanel().showDeath(p);
    }
    
    private Action loadOptionsMenu = () -> {
                if(Mode.mode() == Mode.DEFAULT){
                    clearScene();
                    world().load(new userPreferences.Manager(this));
                }};
    
}

/* Temporarily a button. Should then show buttons to see the stats of the game
 * and a button to go to the menu.
 */
class EndGamePanel extends Button{
    private Player p;
    private String message;
    
    private void colorBackground() {
        Color color = p.color();
        Color transparentColor = new Color(color.getRed(),color.getGreen(),color.getBlue(),150);
        getImage().setColor(transparentColor);
        getImage().fill();
    
    }
    
    public void showVictory(Player player){
        p = player;
        message = "Player " + p.name() + " has won";
        show();
    }
    
    public void showDeath(Player p){
        message = "Player " + p.name() + " has lost";
        show();
    }
    
    private void show() {
        setImage(new GreenfootImage(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT));
        colorBackground();
        writeMessage();
        addToWorld();
        
    }
    
    private void writeMessage() {
        if(p.color().luminosity() > 128) {
            getImage().setColor(Color.BLACK);
        } else {
            getImage().setColor(Color.WHITE);
        }
        getImage().setFont(new Font("monospaced", true, false, 50));
        getImage().drawString(message, 400, 500);
    }
    
    void addToWorld() {
        MyWorld.theWorld.addObject(this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
    }
    
    @Override
    public void clicked() {
        world().load(new menu.Manager());
    }
    
}
