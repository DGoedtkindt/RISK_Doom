
public class DeleteContinent extends Button
{
    
    
    public void clicked(int mode){
        
        if(((MyWorld)(getWorld())).getCurrentMode() == Mode.DEFAULT){
            
            ((MyWorld)(getWorld())).changeMode(Mode.DELETE_CONTINENT);
            
        }else{
                
            ((MyWorld)(getWorld())).escape();
            
        }
        
        MyWorld.theWorld.setSingleHexTransparent();
        MyWorld.theWorld.setUnoccupiedTerritoriesTransparent();
        
    }
    
    
    public void act() 
    {
        
    }   
    
    
    
}
