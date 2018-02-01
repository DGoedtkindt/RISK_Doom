package newGameMenu;

import xmlChoosers.MapChooser;
import appearance.Theme;
import base.GColor;
import base.Game;
import base.MyWorld;
import base.NButton;
import base.StateManager;
import greenfoot.GreenfootImage;
import java.awt.event.ActionEvent;
import basicChoosers.DifficultyChooser;
import javax.swing.JOptionPane;

public class Manager extends StateManager{
    
    protected MapChooser mapChooser = new MapChooser(false);
    protected PlayersPanel playersPanel = new PlayersPanel();
    protected DifficultyChooser difficulty = new DifficultyChooser();
    protected NButton play = new NButton((ActionEvent ae)->{getSettingsAndPlay();}, "Let's play !");
    private MyWorld world() {return MyWorld.theWorld;}
    
    
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
    
    private void getSettingsAndPlay() {
        try {
            Game game = new Game();
            game.players = playersPanel.getPlayers();
            game.map = mapChooser.getSelectedMap();
            game.difficulty = difficulty.selectedDifficulty();
            
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        
    };

    @Override
    public void escape() {
        int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the main menu?", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                clearScene();
                world().load(new menu.Manager());

            }
    }

    
}