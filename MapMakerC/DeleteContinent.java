import greenfoot.*;  

public class DeleteContinent extends Button
{
    
    
    public void clicked(int mode){
        
        ((MyWorld)(getWorld())).changeMode(Mode.DELETE_CONTINENT);
                               
    }
    
    
    public void act() 
    {
        
    }   
    
    
    
}
