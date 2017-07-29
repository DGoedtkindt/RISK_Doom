import greenfoot.*; 

public class CreateContinent extends Button
{
    
    public void clicked(int mode){
        
        if(((MyWorld)(getWorld())).getCurrentMode() == Mode.DEFAULT){
            
            ((MyWorld)(getWorld())).changeMode(Mode.SELECT_TERRITORY);
            
        }else{
                
            ((MyWorld)(getWorld())).escape();
            
        }
        
        Selector.setAllTransparent();
        
    }
    
    public void act() 
    {
        
    }    
    
    
    
    
}
