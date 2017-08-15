
public class EditContinentColor extends Button
{
    
    public void clicked(int mode){
        
        if(MyWorld.theWorld.getCurrentMode() == Mode.DEFAULT){
            
            MyWorld.theWorld.changeMode(Mode.EDIT_CONTINENT_COLOR);
            
        }else{
                
            MyWorld.theWorld.escape();
            
        }
                               
        MyWorld.theWorld.setSingleHexTransparent();
        MyWorld.theWorld.setUnoccupiedTerritoriesTransparent();
        
    }
    
    
    public void act() 
    {
        
    }    
    
    
    
    
}
