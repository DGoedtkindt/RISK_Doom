import greenfoot.*;  
public class OKButton extends Button
{
    
    
    public void clicked(int mode){
        
        switch(mode){
            
            default : ((MyWorld)getWorld()).escape();
                        break;
        }
        
    }
    
    
    
    public void act() 
    {
        
    }    
    
    
    
    
}
