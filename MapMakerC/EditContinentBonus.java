import greenfoot.*;  

public class EditContinentBonus extends Button
{
    
    public void clicked(int mode){
        
        if(((MyWorld)(getWorld())).getCurrentMode() == Mode.DEFAULT){
            
            ((MyWorld)getWorld()).changeMode(Mode.EDIT_CONTINENT_BONUS);
            
        }else{
                
            ((MyWorld)(getWorld())).escape();
            
        }
                             
        Selector.setAllTransparent();
        
    }

}
