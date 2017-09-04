import java.util.ArrayList;
import java.util.List;
import java.awt.Color;

public class TerritoryHex extends Button
{
    private Territory territory;
    
    private MyWorld getMyWorld(){ return (MyWorld)this.getWorld();}
    
    public TerritoryHex(Territory territory, Color color){
        this.territory = territory;
        drawTerrHex(color);
    }
    
    public void clicked(){
        Mode mode = Mode.currentMode();
        
        if(mode == Mode.CREATE_CONTINENT ||
           mode == Mode.EDIT_TERRITORY ||
           mode == Mode.SET_LINK ||
           mode == Mode.DELETE_TERRITORY ||
           mode == Mode.EDIT_TERRITORY_BONUS){
            
            Selector.select(getTerritory());
            
        }else if(mode == Mode.EDIT_CONTINENT_COLOR ||
                 mode == Mode.EDIT_CONTINENT_BONUS ||
                 mode == Mode.DELETE_CONTINENT){
            
            if(Selector.select(getTerritory().getContinent())) {
                        Selector.setValidator(Selector.NOTHING);
                        
                    } else {
                getMyWorld().escape();
            }
            
        }else{
            getMyWorld().escape();
            
        }
        
    }
    
    public ArrayList<TerritoryHex> getBorderingHex() {
        List<TerritoryHex> allOtherTerritoryHex;
        ArrayList<TerritoryHex> borderingHexList = new ArrayList<>();
        
        allOtherTerritoryHex = getWorld().getObjects(TerritoryHex.class);
        
        for(TerritoryHex otherHex : allOtherTerritoryHex){
            if(this.distance(otherHex) < 2 * Hexagon.getSize()){
                borderingHexList.add(otherHex);
                
            }
            
        }
        return borderingHexList;
    }
    
    public void drawTerrHex(Color color){   
        this.setImage(Hexagon.createImage(color, 0.95));
        this.getImage().setTransparency(150);
    }
    
    
    public Territory getTerritory(){
       return territory;
        
    }

   
    public double distance(TerritoryHex otherHex) {
        return Math.sqrt(   (Math.pow(this.getX()  -  otherHex.getX(), 2))    +     (Math.pow(this.getY()  -  otherHex.getY(), 2))  );
        
    }
    
}
