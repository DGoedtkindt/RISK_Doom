import greenfoot.*; 
import java.util.List;

public class TerritoryHex extends Button
{
    Territory territory;

    
    
    public TerritoryHex()
    {
        setImage(new GreenfootImage(Hexagon.getSize(),Hexagon.getSize()));
    }
    
    
    
    
    private MyWorld getMyWorld()
    {
        return (MyWorld)this.getWorld();
    }
    
    
    
    
    public void clicked(int mode){
        
        switch(mode)
        {
            
            case Mode.SELECT_TERRITORY : getMyWorld().selectTerritory(getTerritory()); 
                                        break;
                                        
            default: getMyWorld().escape(); 
                                        break;
                                        
        }
        
    }

    
    public List getTerrHexInRange(int range){
        
        List terrHexInRange = getObjectsInRange((int)(1.5 * Hexagon.HEXAGON_SIZE), TerritoryHex.class);
        
        return terrHexInRange;
        
    }
    
    
    public Territory getTerritory(){
        
        return territory;
        
    }
   
    
    
}
