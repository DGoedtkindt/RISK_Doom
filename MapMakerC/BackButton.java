import javax.swing.JOptionPane;

public class BackButton extends Button{

    public BackButton(){
        
        //GreenfootImage
        
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
