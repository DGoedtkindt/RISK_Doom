import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.JOptionPane;

public class TerritoryHex extends Button
{
    private Territory territory;
    
    public TerritoryHex(Territory territory, Color color){
        this.territory = territory;
        drawTerrHex(color);
    }
    
    public void clicked() {
        Mode mode = Mode.currentMode();
        
        if(mode == Mode.CREATE_CONTINENT ||
           mode == Mode.SET_LINK ||
           mode == Mode.DELETE_TERRITORY){
            
            Selector.select(getTerritory());
            
        }else if(mode == Mode.EDIT_TERRITORY_BONUS) {
            Selector.select(territory);
            territory.editBonus();
            MyWorld.theWorld.escape();
        
        }else if(mode == Mode.EDIT_CONTINENT_COLOR) {
            Selector.select(territory.continent());
            Selector.setValidator(Selector.NOTHING);
            territory.continent().editColor();
            MyWorld.theWorld.escape();
        
        }else if(mode == Mode.EDIT_CONTINENT_BONUS) {
            Selector.select(territory.continent());
            Selector.setValidator(Selector.NOTHING);
            territory.continent().editBonus();
            MyWorld.theWorld.escape();
        
        }else if(mode == Mode.DELETE_CONTINENT){
                    Selector.select(territory.continent());
                    Selector.setValidator(Selector.NOTHING);   
            
        }else{
            MyWorld.theWorld.escape();
            
        }
    }
    
    public ArrayList<TerritoryHex> getBorderingHex() {
        List<TerritoryHex> allOtherTerritoryHex;
        ArrayList<TerritoryHex> borderingHexList = new ArrayList<>();
        
        allOtherTerritoryHex = getWorld().getObjects(TerritoryHex.class);
        
        for(TerritoryHex otherHex : allOtherTerritoryHex){
            if(this.distance(otherHex) < 2 * Hexagon.RADIUS){
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
