package mainObjects;

import base.Button;
import base.GColor;
import game.Turn;
import greenfoot.Color;
import greenfoot.GreenfootImage;

/**
 * This Class represents the Actors that display informations about a Territory.
 * 
 */
public class TerrInfo extends Button {
    
    private final TerritoryHex LINKED_TERR_HEX;
    
    /**
     * Creates a TerrInfo.
     * @param terrHex The TerritoryHex on which it is placed.
     */
    public TerrInfo(TerritoryHex terrHex) {
        LINKED_TERR_HEX = terrHex;
        setImage(new GreenfootImage(1,1));
        
    }
    
    @Override
    public void clicked() {
        LINKED_TERR_HEX.clicked();
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
        
        //Draws the number of armies
        GreenfootImage armiesDisplay = new GreenfootImage("" + armies, 25, GColor.WHITE ,new GColor(0,0,0,0));
        img.drawImage(armiesDisplay, (img.getWidth() - armiesDisplay.getWidth()) / 2, (img.getHeight() - armiesDisplay.getHeight()) / 2);
        
        //Draws the bonus
        int bonus = LINKED_TERR_HEX.territory().bonus();
        
        if(bonus != 0){
            GreenfootImage bonusDisplay = new GreenfootImage("" + bonus, 19 , GColor.YELLOW, new GColor(0,0,0,0));
            img.drawImage(bonusDisplay, 4, 12);
        }
        
        if(LINKED_TERR_HEX.territory().owner() != null){
            
            //Draws the attack and defense bonus from the Battlecry Combo
            if(Turn.currentTurn != null){
                
                if(Turn.currentTurn.player != null){
                    
                    int armiesBonus = LINKED_TERR_HEX.territory().owner().battlecryBonus;
                    
                    if(armiesBonus != 0){
                        
                        GColor color;
                        
                        if(LINKED_TERR_HEX.territory().owner() == Turn.currentTurn.player){
                            color = new GColor(0, 255, 0);
                        }else{
                            color = new GColor(255, 140, 0);
                        }
                        
                        GreenfootImage armiesBonusDisplay = new GreenfootImage("" + armiesBonus, 19 , color, new GColor(0,0,0,0));
                        img.drawImage(armiesBonusDisplay, img.getWidth() - armiesBonusDisplay.getWidth() - 4, 12);
                        
                    }
                    
                }
                
            }
            
            //Draws the indication of the Territory being a capital (a golden star)
            if(LINKED_TERR_HEX.territory().owner().capital == LINKED_TERR_HEX.territory()){
                
                GreenfootImage capitalDisplay = new GreenfootImage(16, 16);
                capitalDisplay.setColor(Color.YELLOW);
                
                capitalDisplay.drawPolygon(new int[]{8, 13, 0, 16, 3}, 
                                           new int[]{0, 16, 5, 5,  16}, 5);
                
                img.drawImage(capitalDisplay, 4, img.getWidth() - capitalDisplay.getWidth());
                
            }
            
            //Draws an image if the player has a Fortress Combo activated (a shield)
            if(LINKED_TERR_HEX.territory().owner().fortressProtection){
                
                GreenfootImage fortressDisplay = new GreenfootImage(16, 16);
                fortressDisplay.setColor(new GColor(0, 191, 255));
                
                fortressDisplay.fillPolygon(new int[]{0, 16, 16, 8,  0}, 
                                            new int[]{0, 0,  9,  16, 9}, 5);
                
                img.drawImage(fortressDisplay, img.getWidth() - fortressDisplay.getWidth(), img.getHeight() - fortressDisplay.getHeight() - 4);
                
            }
            
        }
        
        setImage(img);
        
    }
    
    /**
     * Updates the image of this TerrInfo.
     */
    public void updateImage(){
        if(world().stateManager instanceof mapEditor.Manager)
            setDisplayedBonus(LINKED_TERR_HEX.territory().bonus());
        
        else if(world().stateManager instanceof game.Manager)
            setDisplayedArmiesNumber(LINKED_TERR_HEX.territory().armies());
        
        else System.err.println("There is a TerrInfo asking for an update in a "
                + "state that should not contain TerrInfo");
        
    }
    
    /**
     * Gets the TerritoryHex on which it is placed.
     * @return The said TerritoryHex.
     */
    public TerritoryHex linkedTerrHex(){
        return LINKED_TERR_HEX;
    }
    
}
