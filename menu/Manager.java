package menu;

import base.*;
import greenfoot.GreenfootImage;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class Manager extends StateManager {
    private MyWorld world() {return MyWorld.theWorld;}
    
    //Buttons in the main menu
    private NButton playGameButton           = new NButton((ActionEvent ae) -> {gameMenu();}     , "Play");
    private NButton mapEditorButton          = new NButton((ActionEvent ae) -> {mapEditorMenu();}, "Map Editor");
    private NButton optionsButton            = new NButton((ActionEvent ae) -> {optionsMenu();}  , "Options");
    
    //Buttons in game menu
    private NButton newGameButton            = new NButton((ActionEvent ae) -> {newGameMenu();}  , "New Game");
    private NButton loadGameButton           = new NButton((ActionEvent ae) -> {loadGameMenu();} , "Load Game");

    @Override
    public void setupScene() {
        world().makeSureSceneIsClear();
        drawPineapple();
        world().addObject(playGameButton, world().getWidth() / 2, world().getHeight() / 2);
        world().addObject(mapEditorButton, world().getWidth() / 4, world().getHeight() / 2);
        world().addObject(optionsButton, 3 * world().getWidth() / 4, world().getHeight() / 2);
    }

    @Override
    public void clearScene() {
        world().removeObject(playGameButton);
        world().removeObject(mapEditorButton);
        world().removeObject(optionsButton);
        world().removeObject(newGameButton);
        world().removeObject(loadGameButton);
        
    }
    
    private void drawPineapple() {
        GreenfootImage pineapple = new GreenfootImage("TheMightyPineappleOfJustice.png");
        pineapple.scale(400, 150);
        world().getBackground().drawImage(pineapple, 75, 75);
    
    }
    
    private void gameMenu() {
        clearScene();
        world().addObject(newGameButton, world().getWidth() / 4, world().getHeight() / 2);
        world().addObject(loadGameButton, 3 * world().getWidth() / 4, world().getHeight() / 2); 
    
    }
    
    private void mapEditorMenu() {
        clearScene();
        world().load(new mapEditorMenu.Manager());
    
    }
    
    private void optionsMenu() {
        clearScene();
        world().load(new userPreferences.Manager(this));
    
    }
    
    private void newGameMenu() {
        clearScene();
        world().load(new newGameMenu.Manager());
    
    }
    
    private void loadGameMenu() {
        clearScene();
        throw new UnsupportedOperationException("There is no loadGameMenu Manager yet");
    
    }

    @Override
    public void escape() {
        int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the main menu?", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
            if(choice == JOptionPane.YES_OPTION){ 
                clearScene(); setupScene();

            }
    
    }

}
