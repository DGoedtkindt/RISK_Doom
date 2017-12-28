package newGameMenu;

import xmlChoosers.MapChooser;
import greenfoot.World;
import base.GColor;
import appearance.Theme;
import greenfoot.GreenfootImage;
import java.awt.event.ActionEvent;
import base.NButton;
import basicChoosers.BasicChooser;
import basicChoosers.DifficultyChoices;

public class Manager {
    
    protected MapChooser mapChooser = new MapChooser(false);
    protected PlayersPanel playersPanel = new PlayersPanel();
    protected BasicChooser difficulty = new BasicChooser(new DifficultyChoices());
    protected NButton play = new NButton((ActionEvent ae)->{getSettingsAndGameOn();}, "Game On !");
    
    private void getSettingsAndGameOn() {
        throw new UnsupportedOperationException("Not supported yet.");
        
    };
    
    public void setupMenu(World world) {
        GreenfootImage title = 
                new GreenfootImage("Setup your game", 30, Theme.used.textColor, new GColor(0,0,0,0));
        world.getBackground().drawImage(title, 
                (world.getWidth()-title.getWidth())/2, 50);
        mapChooser.addToWorld(world,world.getWidth()/2, 340);
        difficulty.addToWorld(world,world.getWidth()/2, 520);
        playersPanel.addToWorld(world, world.getWidth()/2, 750);
        world.addObject(play, world.getWidth()/2, 980);
    
    }
    
    public void clearScene() {
        throw new UnsupportedOperationException("Not supported yet.");
        
    }
    
}