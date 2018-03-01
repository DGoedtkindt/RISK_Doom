package mainObjects;

import base.Button;
import base.GColor;
import greenfoot.GreenfootImage;
import mode.Mode;

public class TerrInfo extends Button {
    
    private TerritoryHex linkedTerrHex;
    
    public TerrInfo(TerritoryHex terrHex) {
        linkedTerrHex = terrHex;
        setImage(new GreenfootImage(1,1));
        
    }
    
    public void clicked() {
        linkedTerrHex.clicked();
    }
    
    public void setDisplayedBonus(int bonus) {
        if(bonus > 0) {
            GreenfootImage img = new GreenfootImage("terrInfo.png");
            img.scale(50, 60);
            GreenfootImage txt = new GreenfootImage("" + bonus, 25 , GColor.WHITE,new GColor(0,0,0,0));
            img.drawImage(txt, 25-txt.getWidth()/2, 30-txt.getHeight()/2);
            setImage(img);
    
        }else{
            setImage(new GreenfootImage(1,1));
        
        }
        
    }
    
    public void setDisplayedArmiesNumber(int armies){
        
        GreenfootImage img = new GreenfootImage("terrInfo.png");
        img.scale(50, 60);
        GreenfootImage armiesDisplay = new GreenfootImage("" + armies, 25, GColor.WHITE ,new GColor(0,0,0,0));
        img.drawImage(armiesDisplay, (img.getWidth() - armiesDisplay.getWidth()) / 2, (img.getHeight() - armiesDisplay.getHeight()) / 2);
        
        int bonus = linkedTerrHex.territory().bonus();
        
        if(bonus != 0){
            GreenfootImage bonusDisplay = new GreenfootImage("" + bonus, 19 , GColor.YELLOW, new GColor(0,0,0,0));
            img.drawImage(bonusDisplay, 4, 12);
        }
        
        setImage(img);
        
    }
    
    public void updateImage(){
        
        switch(Mode.mode()){
            
            case MAP_EDITOR_DEFAULT :
            case CREATE_TERRITORY :
            case CREATE_CONTINENT :
            case EDIT_TERRITORY_BONUS :
            case SET_LINK :
            case EDIT_CONTINENT_COLOR :
            case EDIT_CONTINENT_BONUS :
            case DELETE_TERRITORY :
            case DELETE_CONTINENT :
            case SELECT_INFO_HEX :
                setDisplayedBonus(linkedTerrHex.territory().bonus());
                break;
                
            case GAME_DEFAULT :
            case ATTACK :
            case MOVE :
            case SELECTING_COMBO :
            case SAP :
            case CLEARING_HAND :
                setDisplayedArmiesNumber(linkedTerrHex.territory().armies());
                break;
            
        }
        
    }
    
    public TerritoryHex linkedTerrHex(){
        
        return linkedTerrHex;
        
    }
    
}
