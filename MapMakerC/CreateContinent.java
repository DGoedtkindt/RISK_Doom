import greenfoot.*; 

public class CreateContinent extends Button
{
    
    public void clicked(int mode){
        
        ((MyWorld)(getWorld())).changeMode(Mode.SELECT_TERRITORY);
                               
    }
    
    public void act() 
    {
        
    }    
    
    
    
    
}
