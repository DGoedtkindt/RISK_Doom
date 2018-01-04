package newGameMenu;

import xmlChoosers.MapChooser;
import base.*;
import appearance.Theme;
import greenfoot.GreenfootImage;
import java.awt.event.ActionEvent;
import basicChoosers.BasicChooser;
import basicChoosers.DifficultyChoices;
import javax.swing.JOptionPane;

public class Manager extends StateManager{
    
    protected MapChooser mapChooser = new MapChooser(false);
    protected PlayersPanel playersPanel = new PlayersPanel();
    protected BasicChooser difficulty = new BasicChooser(new DifficultyChoices());
    protected NButton play = new NButton((ActionEvent ae)->{getSettingsAndGameOn();}, "Game On !");
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
        playersPanel.addToWorld(world(),world().getWidth()/2, 750);
        world().addObject(play, world().getWidth()/2, 980);
    
    }
    
    @Override
    public void clearScene() {
        throw new UnsupportedOperationException("Not supported yet.");
        
    }
    
    private void getSettingsAndGameOn() {
        throw new UnsupportedOperationException("Not supported yet.");
        
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