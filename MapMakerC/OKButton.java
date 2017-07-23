import greenfoot.*;  
public class OKButton extends Button
{
    
    
    public void clicked(int mode){
        
        switch(((MyWorld)getWorld()).currentMode){
            
            default : ((MyWorld)getWorld()).escape();
                        break;
        }
        
    }
    
    
    
    public void act() 
    {
        
    }    
    
    
    
    
}
