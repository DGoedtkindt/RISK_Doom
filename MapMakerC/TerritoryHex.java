import greenfoot.*; 
import java.util.ArrayList;
import java.util.List;

public class TerritoryHex extends Button
{
    private Territory territory;
    private MyWorld getMyWorld(){ return (MyWorld)this.getWorld();}
    
    public TerritoryHex(int territory)
    {
        setImage(new GreenfootImage(Hexagon.getSize(),Hexagon.getSize()));
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

    
    public ArrayList<TerritoryHex> getBorderingHex()
    {
        ArrayList<TerritoryHex> allOtherTerritoryHex = new ArrayList<TerritoryHex>();
        ArrayList<TerritoryHex> borderingHexList = new ArrayList<TerritoryHex>();
        
        for(TerritoryHex otherHex : allOtherTerritoryHex)
        {
            if(this.distance(otherHex) < 1.2 * Hexagon.getSize()){
                
                borderingHexList.add(otherHex);
            
            }
        }
        
        
        
        return borderingHexList;
    }
    
    
    public Territory getTerritory(){
        
        return territory;
        
    }
   
    private double distance(TerritoryHex otherHex)
    {
        return Math.sqrt(   (Math.pow(this.getX()  -  otherHex.getX(), 2))    +     (Math.pow(this.getY()  -  otherHex.getY(), 2))  );
    }
    
}
