import greenfoot.*;  


public class DeleteTerritory extends Button
{
    
    
    public void clicked(int mode){
        
        ((MyWorld)(getWorld())).changeMode(Mode.SELECT_HEX);
                               
    }
    
    
    public void act() 
    {
        
    }   
    
    
    
}
