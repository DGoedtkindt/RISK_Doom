package base;

import javax.swing.JOptionPane;
import greenfoot.GreenfootImage;

public class BackButton extends Button{

    public BackButton(){
        GreenfootImage img = new GreenfootImage("backToHome.png");
        img.scale(30,30);
        this.setImage(img);

    }
    
    private static MyWorld world() {return MyWorld.theWorld;}
    
    @Override
    public void clicked() {
        
        int choice;
        
        switch(Mode.currentMode()){
            
            case MAP_EDITOR_DEFAULT : 
                
                choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the map editor menu? Unsaved changes will be lost.", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);

                if(choice == JOptionPane.YES_OPTION){

                    world().mapEditorMenu();

                }
                
                break;
                
            case GAME_DEFAULT : 
                
                choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the game menu? Your game will be lost if it is not saved.", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);

                if(choice == JOptionPane.YES_OPTION){

                    world().gameMenu();

                }
                
                break;
            
            case MAP_EDITOR_MENU :
            case GAME_MENU : 
                
                choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the main menu?", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);

                if(choice == JOptionPane.YES_OPTION){

                    world().mainMenu();

                }
                
                break;
            
            default : world().escape();
            
        }
        
    }
    
    
}
