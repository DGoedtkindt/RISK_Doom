package game;

import appearance.Theme;
import base.Hexagon;
import base.MyWorld;
import greenfoot.GreenfootImage;
import greenfoot.Color;
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
        
        onArmiesChange = ()->{updateInfoHex();};
        onOwnerChange = ()->{drawPlayerColor();};
        onStatusChange = ()->{paint();};
        
    }
    
    @Override
    public void show() {
        super.show();
        terrInfo.addToWorld();
        showLinkIndics();
        
    }
    
    private void updateInfoHex() {
        terrInfo.setImage(new GreenfootImage(terrInfoBackground));
        int armies = territory.armies();
        GreenfootImage armiesDisplay = new GreenfootImage("" + armies, 25, Color.WHITE ,new Color(0,0,0,0));
        terrInfo.getImage().drawImage(armiesDisplay, (terrInfo.getImage().getWidth() - armiesDisplay.getWidth()) / 2, (terrInfo.getImage().getHeight() - armiesDisplay.getHeight()) / 2);
        
        
    }

    

    private void drawPlayerColor() {
        Color playerColor = playerColor();
        GreenfootImage coloredHex = Hexagon.createImage(playerColor, 0.5);
        territory.blankHexSet.forEach((hex)->{
            int[] position = hex.rectCoord();
            position[0] -= coloredHex.getWidth()/2;
            position[1] -= coloredHex.getHeight()/2;
            MyWorld.theWorld.getBackground()
                .drawImage(coloredHex,position[0], position[1]);
        
        });
        
    }
    
    private Color continentColor() {
        if(territory.continent() != null) return territory.continent().color();
        else return Theme.used.territoryColor;
        
    }
    
    private Color playerColor() {
        if(territory.owner() != null) return territory.owner().color;
        else return continentColor();
    
    }

    private void paint() {
        switch(territory.selectionStatus()) {
            case OPAQUE: paint(continentColor()); break;
            case SELECTED : paint(Theme.used.selectionColor); break;
            case TRANSPARENT : paint(Theme.used.territoryColor); break;
        
        }
        drawPlayerColor();
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
