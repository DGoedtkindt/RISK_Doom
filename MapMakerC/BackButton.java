import javax.swing.JOptionPane;
import greenfoot.GreenfootImage;

public class BackButton extends Button{

    public BackButton(){
        GreenfootImage img = new GreenfootImage("backToHome.png");
        img.scale(30,30);
        this.setImage(img);

        
    }
    
    @Override
    public void clicked() {
        
        int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the menu? Unsaved changes will be lost.", 
                                                         "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
        
        if(choice == JOptionPane.YES_OPTION){
            
            MyWorld.theWorld.basicMenu();
            
        }
        
    }
    
}
