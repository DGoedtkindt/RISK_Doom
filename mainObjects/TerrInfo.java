package mainObjects;

import base.Button;
import base.GColor;
import game.Turn;
import greenfoot.GreenfootImage;
import mode.Mode;

/**
 * This Class represents the Actors that display informations about a Territory.
 * 
 */
public class TerrInfo extends Button {
    
    private TerritoryHex linkedTerrHex;
    
    /**
     * Creates a TerrInfo.
     * @param terrHex The TerritoryHex on which it is placed.
     */
    public TerrInfo(TerritoryHex terrHex) {
        linkedTerrHex = terrHex;
        setImage(new GreenfootImage(1,1));
        
    }
    
    @Override
    public void clicked() {
        linkedTerrHex.clicked();
    }
    
    /**
     * Displays the bonus of its Territory.
     * @param bonus The bonus to display.
     */
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
    
    /**
     * Displays the number of armies on its Territory and its bonuses.
     * @param armies The number of armies to display.
     */
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
        
        if(linkedTerrHex.territory().owner() != null){
            if(Turn.currentTurn != null){
                if(Turn.currentTurn.player != null){
                    
                    int armiesBonus = linkedTerrHex.territory().owner().battlecryBonus;
                    
                    if(armiesBonus != 0){
                        
                        GColor color;
                        
                        if(linkedTerrHex.territory().owner() == Turn.currentTurn.player){
                            color = new GColor(0, 255, 0);
                        }else{
                            color = new GColor(255, 140, 0);
                        }
                        
                        GreenfootImage armiesBonusDisplay = new GreenfootImage("" + armiesBonus, 19 , color, new GColor(0,0,0,0));
                        img.drawImage(armiesBonusDisplay, img.getWidth() - armiesBonusDisplay.getWidth() - 4, 12);
                        
                    }
                    
                }
                
            }
            
        }
        
        setImage(img);
        
    }
    
    /**
     * Updates the image of this TerrInfo.
     */
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
    
    /**
     * Gets the TerritoryHex on which it is placed.
     * @return The said TerritoryHex.
     */
    public TerritoryHex linkedTerrHex(){
        return linkedTerrHex;
    }
    
}
