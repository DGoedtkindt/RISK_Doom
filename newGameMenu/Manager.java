package newGameMenu;

import appearance.MessageDisplayer;
import appearance.Theme;
import base.GColor;
import base.Game;
import base.NButton;
import base.StateManager;
import basicChoosers.DifficultyChooser;
import game.Zombie;
import greenfoot.GreenfootImage;
import xmlChoosers.MapChooser;

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
            game.players.addAll(playersPanel.getPlayers());
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
        standardBackToMenu("Do you want to return to the main menu?");
    }
    
}