import greenfoot.*; 

public class TerritoryHex extends Button
{
    Territory territory;

   
    
    private MyWorld getMyWorld()
    {
        return (MyWorld)this.getWorld();
    }
    
    public void clicked(int mode){
        
        switch(mode)
        {
            case Mode.SELECT_TERRITORY : getMyWorld().selectTerritory(getTerritory()); break;
                                default: getMyWorld().escape(); break;                   
        }
        
    }
    
    public Territory getTerritory(){
        
        return territory;
        
    }
    
    
    
    
    
}
