package game;

import base.Action;
import base.Game;
import base.Map;
import base.NButton;
import base.StateManager;
import greenfoot.Greenfoot;
import javax.swing.JOptionPane;
import mainObjects.Continent;
import mainObjects.Links;
import mainObjects.Player;
import mainObjects.Territory;
import mode.Mode;
import mode.ModeMessageDisplay;
import selector.Selector;

public class Manager extends StateManager{

    public Difficulty usedDifficulty;
    
    private static final int STARTING_TERRITORIES = 4;
    
    private Game gameToLoad = new Game();
    private Game loadedGame = new Game();
    
    private ControlPanel ctrlPanel;
    private ModeMessageDisplay modeDisp;
    private NButton options;
    
    public Manager(Game loadGame) {
        ctrlPanel = new ControlPanel();
        modeDisp = new ModeMessageDisplay();
        options = new NButton(loadOptionsMenu, "Options");
        usedDifficulty = loadGame.difficulty;
        gameToLoad = loadGame;
        
    }
    
    public Game game() {return loadedGame;}
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
        loadGame();
        /*world().addObject(new mode.ModeButton("backToHome.png", mode.Mode.ATTACK, selector.Selector.IS_NOT_OWNED_TERRITORY), 1800, 300);
        world().addObject(new mode.ModeButton("backToHome.png", mode.Mode.MOVE, selector.Selector.IS_NOT_OWNED_TERRITORY), 1800, 600);
        world().addObject(new NButton(() -> {Turn.nextTurn();}, new GreenfootImage("backToHome.png")), 1800, 900);*/
    }
    
    private void loadGame(){
        loadMap();
        loadedGame = gameToLoad;
        start();
    
    }
    
    private void loadMap(){
        
        gameToLoad.map.territories.forEach(Territory::addToWorld);
        gameToLoad.map.continents.forEach(Continent::addToWorld);
        gameToLoad.map.links.forEach(Links::addToWorld);
        
    }
    
    @Override
    public void clearScene() {
        throw new UnsupportedOperationException("Not supported yet.");
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
            
            if(!p.name().equals(Player.ZOMBIE_NAME)){
                
                int terrNumber = 0;
                Territory terrToAttribute;

                while(terrNumber < STARTING_TERRITORIES){

                    terrToAttribute = loadedGame.map.territories.get(Greenfoot.getRandomNumber(loadedGame.map.territories.size()));

                    if(terrToAttribute.owner() == null){
                        terrToAttribute.setOwner(p);
                        terrNumber++;
                    }

                }
                
            }
            
        }
        
    }
    
    public void start(){
        
        giveTerritoriesRandomly();
        Turn.nextTurn();
        
    }
    
    private Action loadOptionsMenu = () -> {clearScene();
                                            world().load(new userPreferences.Manager(this));};
    
}
