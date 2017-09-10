import greenfoot.GreenfootImage;

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
            GreenfootImage img = new GreenfootImage("texture1.png");
            img.scale(50, 60);
            GreenfootImage txt = new GreenfootImage(""+newBonus, 25 , Color.WHITE,null);
            img.drawImage(txt, 25-txt.getWidth()/2, 30-txt.getHeight()/2);
            this.setImage(img);
    
        }else{
            setImage(new GreenfootImage(1,1));
        
        }
    }
    
}
