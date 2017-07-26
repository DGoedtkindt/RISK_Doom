import greenfoot.*;  

public class CreateLinks extends Button
{
    
    public void clicked(int mode){
        
        ((MyWorld)(getWorld())).changeMode(Mode.SET_LINKS);
                               
    }
    
    
    public void act() 
    {
        
    }    
    
    
    
    
}
