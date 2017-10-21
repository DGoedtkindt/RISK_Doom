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
        
        switch(Mode.currentMode()){
            
            case MAP_EDITOR_DEFAULT : 
                
                int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the menu? Unsaved changes will be lost.", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);

                if(choice == JOptionPane.YES_OPTION){

                    world().mapEditorMenu();

                }
                
                break;
                
            case GAME_DEFAULT : break;
            
            case MAP_EDITOR_MENU : break;
            
            case GAME_MENU : break;
            
            case MAIN_MENU : break;
            
            default : world().escape();
            
        }
        
    }
    
    public void makeTransparent() {
        getImage().setTransparency(MyWorld.TRANSPARENT);
    
    }
    
    public void makeOpaque() {
        getImage().setTransparency(MyWorld.OPAQUE);
    
    }
    
}
