import greenfoot.*;  

public class EditContinentColor extends Button
{
    
    public void clicked(int mode){
        
        ((MyWorld)(getWorld())).changeMode(Mode.EDIT_CONTINENT_COLOR);
                               
    }
    
    
    public void act() 
    {
        
    }    
    
    
    
    
}
