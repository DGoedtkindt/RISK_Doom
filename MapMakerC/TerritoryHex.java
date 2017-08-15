import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class TerritoryHex extends Button
{
    private Territory territory;
    
    private MyWorld getMyWorld(){ return (MyWorld)this.getWorld();}
    
    public TerritoryHex(Territory territory, Color color)
    {
        this.territory = territory;
        drawTerrHex(color);
    }
    
    public void clicked(int mode){
        if(mode == Mode.SELECT_TERRITORY || mode == Mode.CHOOSE_CAPITAL_TERRITORY 
            || mode == Mode.SET_LINKS || mode == Mode.DELETE_TERRITORY) {
            if(getTerritory().getContinent() == null) {
                Selector.selectTerritory(getTerritory());
            }
            }
        else if(mode == Mode.EDIT_CONTINENT_COLOR || mode == Mode.EDIT_CONTINENT_BONUS 
            || mode == Mode.DELETE_CONTINENT) {
            
            Selector.selectContinent(getTerritory().getContinent());
            
            }
        else {
        
            getMyWorld().escape();
            
        }        
    }
    
    public ArrayList<TerritoryHex> getBorderingHex()
    {
        List<TerritoryHex> allOtherTerritoryHex = new ArrayList<TerritoryHex>();
        ArrayList<TerritoryHex> borderingHexList = new ArrayList<TerritoryHex>();
        
        allOtherTerritoryHex = getWorld().getObjects(TerritoryHex.class);
        
        for(TerritoryHex otherHex : allOtherTerritoryHex)
        {
            if(this.distance(otherHex) < 2 * Hexagon.getSize()){
                
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
    
}
