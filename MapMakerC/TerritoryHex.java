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
    
    @Override
    public void clicked(int mode){
        switch (mode) {
            case Mode.SELECT_TERRITORY:
            case Mode.CHOOSE_CAPITAL_TERRITORY:
            case Mode.SET_LINKS:
            case Mode.DELETE_TERRITORY: 
                if(getTerritory().getContinent() == null) {
                    Selector.selectTerritory(getTerritory());
                }   break;
                
            case Mode.EDIT_CONTINENT_COLOR:
            case Mode.EDIT_CONTINENT_BONUS:
            case Mode.DELETE_CONTINENT:
                Selector.selectContinent(getTerritory().getContinent());
                break;
                
            default:
                getMyWorld().escape();
                break;
        }        
    }
    
    public ArrayList<TerritoryHex> getBorderingHex()
    {
        List<TerritoryHex> allOtherTerritoryHex;
        ArrayList<TerritoryHex> borderingHexList = new ArrayList<>();
        
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
