import greenfoot.*; 
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.geom.Area;

public class TerritoryHex extends Button implements Maskable
{
    private Territory territory;
    
    private MyWorld getMyWorld(){ return (MyWorld)this.getWorld();}
    
    public TerritoryHex(Territory territory, Color color)
    {
        this.territory = territory;
        drawTerrHex(color);
    }
    
    public void clicked(int mode){
        
        if(mode == Mode.DEFAULT || mode == Mode.SELECT_HEX){
            
            getMyWorld().escape();
            
        }else{
            
            Selector.selectTerritory(getTerritory()); 
            
        }
        
    }

    public List getTerrHexInRange(int range){
        
        List terrHexInRange = getObjectsInRange((int)(1.5 * Hexagon.HEXAGON_SIZE), TerritoryHex.class);
        
        return terrHexInRange;
        
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
    
    public void drawTerrHex(Color color)
    {
        this.setImage(Hexagon.createSimpleHexImage(color, 0.95));
    }
    
    
    public Territory getTerritory(){
        
       return territory;
        
    }

   
    public double distance(TerritoryHex otherHex)

    {
        return Math.sqrt(   (Math.pow(this.getX()  -  otherHex.getX(), 2))    +     (Math.pow(this.getY()  -  otherHex.getY(), 2))  );
    }
    
    public Area getAreaShape()
    {
        
        return null;
        
    }
}
