package mapEditor;

import appearance.Theme;
import base.Hexagon;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import territory.Displayer;
import territory.Territory;


public class TerritoryDisplayer extends Displayer {
    
    private final static GreenfootImage terrInfoBackground;
    
    static{
        terrInfoBackground = new GreenfootImage("terrInfo.png");
        terrInfoBackground.scale(50, 60); 
        
    }

    public TerritoryDisplayer(Territory territory, TerritoryControler territoryControler) {
        super(territory);
        terrHexs.forEach((terrHex)->{terrHex.controler = territoryControler;});
        terrInfo.controler = territoryControler;
        
        onBonusChange = ()->{updateInfoHex();};
        onContinentChange = ()->{paint();};
        onLinksChange = ()->{showLinkIndics();};
        onStatusChange = ()->{paint();};
    }
    
    private void updateInfoHex() {
        int bonus = territory.bonus();
        if(bonus > 0) {
            terrInfo.addToWorld();
            terrInfo.setImage(new GreenfootImage(terrInfoBackground));
            GreenfootImage txt = new GreenfootImage("" + bonus, 25 , Color.WHITE,new Color(0,0,0,0));
            terrInfo.getImage().drawImage(txt, 25-txt.getWidth()/2, 30-txt.getHeight()/2);
    
        }else{
            terrInfo.removeFromWorld();
        
        }
    }
    
    private Color continentColor() {
        if(territory.continent() != null) return territory.continent().color();
        else return Theme.used.territoryColor;
        
    }
    
    private void paint() {
        switch(territory.selectionStatus()) {
            case OPAQUE: paint(continentColor()); break;
            case SELECTED : paint(Theme.used.selectionColor); break;
            case TRANSPARENT : paint(Theme.used.territoryColor); break;
        
        }
        setTerrHexImage();
        
    }
    
    private void setTerrHexImage() {
        Color color = continentColor();
        GreenfootImage image = Hexagon.createImage(color,0.95);
        image.setTransparency(100);
        terrHexs.forEach((hex)->{
            hex.setImage(image);
        
        });
    
    }
    

}
