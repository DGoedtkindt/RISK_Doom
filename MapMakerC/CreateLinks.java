
public class CreateLinks extends Button
{
    
    public void clicked(int mode){
        
        if(((MyWorld)(getWorld())).getCurrentMode() == Mode.DEFAULT){
            
            ((MyWorld)(getWorld())).changeMode(Mode.SET_LINKS);
            
        }else{
                
            ((MyWorld)(getWorld())).escape();
            
        }
                               
        MyWorld.theWorld.setSingleHexTransparent();
        
    }
    
    
    public void act() 
    {
        
    }    
    
    
    
    
}
