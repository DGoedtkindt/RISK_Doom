import greenfoot.*;
import java.awt.Color;

public class TerrInfo extends Button {
    
    private TerritoryHex linkedTerrHex;
    
    public TerrInfo(TerritoryHex terrHex) {
        linkedTerrHex = terrHex;
        setImage(new GreenfootImage(1,1));
        
    }
    
    
    public void clicked() {
        linkedTerrHex.clicked();
    }
    
    public void setDisplayedBonus(int newBonus) {
        if(newBonus > 0) {
        this.setImage(new GreenfootImage(""+newBonus, 25 , Color.BLACK,Color.WHITE));
    
        }
    }
    
}
