import greenfoot.*; 

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

    
    
    
    
    public Territory getTerritory(){
        
        return territory;
        
    }
   
    
    
    
}
