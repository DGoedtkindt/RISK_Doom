package newGameMenu;

import appearance.Appearance;
import appearance.InputPanel;
import appearance.MessageDisplayer;
import xmlChoosers.MapChooser;
import appearance.Theme;
import base.GColor;
import base.Game;
import base.MyWorld;
import base.NButton;
import base.StateManager;
import greenfoot.GreenfootImage;
import basicChoosers.DifficultyChooser;
import game.Zombie;

/**
 * This manager manages the creation of a new Game.
 * 
 */
public class Manager extends StateManager{
    
    protected MapChooser mapChooser = new MapChooser(false);
    protected PlayersPanel playersPanel = new PlayersPanel();
    protected DifficultyChooser difficulty = new DifficultyChooser();
    protected NButton play = new NButton(()->{getSettingsAndPlay();}, "Let's play !");
    
    @Override
    public void setupScene() {
        world().makeSureSceneIsClear();
        GreenfootImage title = 
                new GreenfootImage("Setup your game", 30, Theme.used.textColor, new GColor(0,0,0,0));
        world().getBackground().drawImage(title, 
                (world().getWidth()-title.getWidth())/2, 50);
        mapChooser.addToWorld(world().getWidth()/2, 340);
        difficulty.addToWorld(world().getWidth()/2, 520);
        difficulty.setArrows(150, DifficultyChooser.DEFAULT_ARROW_SIZE);
        playersPanel.addToWorld(world(),world().getWidth()/2, 750);
        world().addObject(play, world().getWidth()/2, 980);
    
    }
    
    @Override
    public void clearScene() {
        mapChooser.destroy();
        playersPanel.destroy();
        difficulty.destroy();
        world().removeObject(play);
        
    }
    
    /**
     * Starts a Game with the informations chosen by the User.
     */
    private void getSettingsAndPlay() {
        try {
            Game game = new Game();
            game.players = playersPanel.getPlayers();
            game.map = mapChooser.getSelectedMap();
            game.difficulty = difficulty.selectedDifficulty();
            game.players.add(new Zombie(game.difficulty));
            world().load(new game.Manager(game));
            
        } catch (Exception ex) {
            String message = "Cannot launch new Game";
            MessageDisplayer.showException(new Exception(message, ex));
            
        }
        
    }

    @Override
    public void escape() {
        if(InputPanel.usedPanel == null){
            InputPanel.showConfirmPanel("Do you want to quit the Game?", 100, "escape", this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        }else{
            InputPanel.usedPanel.destroy();
        }
    }

    @Override
    public void useInformations(String information, String type) throws Exception {
        
        if(type.equals("escape")){
            
            if(information.equals(InputPanel.YES_OPTION)){
                MyWorld.theWorld.load(new menu.Manager());

            }
            
        }
        
    }

    
}