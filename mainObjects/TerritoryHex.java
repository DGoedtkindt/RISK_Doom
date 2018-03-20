package mainObjects;

import appearance.MessageDisplayer;
import appearance.Theme;
import base.Button;
import base.GColor;
import base.Hexagon;
import game.Player;
import game.Turn;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import input.Form;
import java.util.ArrayList;
import java.util.List;
import mode.Mode;
import selector.Selector;

/**
 * The Class that represents the hexagons contained in a Territory.
 * 
 */
public class TerritoryHex extends Button {
    
    private final Territory TERRITORY;
    private final int[] HEX_COORD = new int[2];
    
    /**
     * Creates a TerritoryHex.
     * @param territory The Territory in which it is contained.
     * @param color The Color of this TerritoryHex.
     * @param x The x hexagonal coordinate of this TerritoryHex.
     * @param y The y hexagonal coordinate of this TerritoryHex.
     */
    public TerritoryHex(Territory territory, GColor color, int x, int y){
        TERRITORY = territory;
        drawTerrHex(color);
        HEX_COORD[0] = x;
        HEX_COORD[1] = y;
    }
    
    /**
     * Gets the hexagonal coordinates of this TerritoryHex
     * @return Its hexagonal coordinates.
     */
    public int[] hexCoord() {
        return HEX_COORD;
    }
    
    /**
     * Gets the rectangular, normal of this TerritoryHex
     * @return Its rectangular, normal coordinates.
     */
    public int[] rectCoord() {
        return Hexagon.hexToRectCoord(HEX_COORD);
    }
    
    @Override
    public void clicked() {
        try {
            switch (Mode.mode()) {
                case CREATE_CONTINENT :
                case DELETE_TERRITORY :
                    Selector.select(territory());
                    break;
                    
                case EDIT_TERRITORY_BONUS :
                    Selector.select(TERRITORY);
                    Selector.setValidator(Selector.NOTHING);
                    world().repaint(); //pour forcer l'actualisation des images
                    TERRITORY.editBonus();
                    world().stateManager.escape();;
                    break;
                    
                case EDIT_CONTINENT_COLOR :
                    if(TERRITORY.continent() != null){
                        Selector.select(TERRITORY.continent());
                        Selector.setValidator(Selector.NOTHING);
                        world().repaint(); //pour forcer l'actualisation des images
                        TERRITORY.continent().editColor();
                        world().stateManager.escape();
                    }
                    break;
                    
                case EDIT_CONTINENT_BONUS :
                    if(TERRITORY.continent() != null){
                        Selector.select(TERRITORY.continent());
                        Selector.setValidator(Selector.NOTHING);
                        world().repaint(); //pour forcer l'actualisation des images
                        TERRITORY.continent().editBonus();
                        world().stateManager.escape();
                    }
                    break;
                    
                case DELETE_CONTINENT :
                    Selector.select(TERRITORY.continent());
                    break;
                    
                case SET_LINK :
                    if(Links.newLinks == null) {
                        Form.inputColor("Enter a Color for this Link.", (input)->{
                            GColor newColor = GColor.fromRGB(input.get("inputedColor"));
                            Links.newLinks = new Links(newColor);
                            Links.newLinks.addToWorld();
            
                            new LinkIndic(TERRITORY, ((TerritoryHex)this).getX(), ((TerritoryHex)this).getY()).addToWorld();
                        });
                        
                    }else if(!Links.newLinks.linkedTerrs.contains(TERRITORY)){
                        
                        MouseInfo mouse = Greenfoot.getMouseInfo();
                        int xPos = mouse.getX();
                        int yPos = mouse.getY();
                        new LinkIndic(TERRITORY, xPos, yPos).addToWorld(); 
                        
                    }
                    
                    break;
                    
                case ATTACK : 
                    if(Territory.actionSource == null
                       && TERRITORY.owner() == Turn.currentTurn.player){
                        
                        Territory.actionSource = TERRITORY;
                        Selector.select(TERRITORY);
                        Selector.setValidator(Selector.IS_ATTACKABLE);
                        
                    }else if(TERRITORY.owner() != Turn.currentTurn.player){
                        
                        Selector.select(TERRITORY);
                        
                        if(Selector.territoriesNumber() == 2){
                            Territory.actionTarget = TERRITORY;
                            try{
                                Territory.actionSource.invade(Territory.actionTarget);
                            }catch(Exception e){
                                MessageDisplayer.showMessage(e.getMessage());
                            }
                            Selector.setValidator(Selector.NOTHING);
                            world().stateManager.escape();
                        }
                        
                    }
                    
                    break;
                
                case MOVE : 
                    if(TERRITORY.owner() == Turn.currentTurn.player){
                        
                        if(Territory.actionSource == null){
                            Territory.actionSource = TERRITORY;
                            Selector.select(TERRITORY);
                        }else{
                            Selector.select(TERRITORY);
                            Territory.actionTarget = TERRITORY;
                            try{
                                Territory.actionSource.moveTo(Territory.actionTarget);
                            }catch(Exception e){
                                MessageDisplayer.showMessage(e.getMessage());
                            }
                            Selector.setValidator(Selector.NOTHING);
                            world().stateManager.escape();
                        }
                        
                    }
                    
                    break;
                    
                case CLEARING_HAND :
                    if(TERRITORY.owner() == Turn.currentTurn.player){
                        Form.inputText("The number of armies you want to put on this territory.", (input)->{
                            if(input.get("inputedText").matches("\\d+")){

                                int newArmies = Integer.parseInt(input.get("inputedText"));

                                if(newArmies < 0){
                                    MessageDisplayer.showMessage("This is a negative number.");
                                }else if(newArmies > ((TerritoryHex)this).TERRITORY.owner().armiesInHand()){
                                    MessageDisplayer.showMessage("You don't have enough armies.");
                                }else{
                                    TERRITORY.addArmies(newArmies);
                                    TERRITORY.owner().addArmiesToHand(-newArmies);
                                    TERRITORY.drawTerritory();
                                }

                            }else{
                                MessageDisplayer.showMessage("Invalid entry.");
                            }
                            
                        });
                        
                    }
                    
                    break;
                    
                case SAP : 
                    if(TERRITORY.owner() != Turn.currentTurn.player){
                        TERRITORY.owner().addArmiesToHand(TERRITORY.armies());
                        TERRITORY.setOwner(null);
                        TERRITORY.drawTerritory();
                        Turn.currentTurn.player.combos().useSap();
                        world().stateManager.escape();
                    }
                    break;
                    
                default: break;
            }
        } catch (Exception ex) {
            MessageDisplayer.showException(ex);
            System.err.println(ex);
            world().stateManager.escape();
        }
        
    }
    
    /**
     * Obtains a list of the TerritoryHexes that surrounds this one.
     * @return The list pf the neighbouring TerritoryHexes.
     */
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
    
    /**
    * Draws the image of this TerritoryHex.
    * @param color The Color of this TerritoryHex.
    */
    public void drawTerrHex(GColor color){
        setImage(Hexagon.createImage(color, 0.95));
        getImage().setTransparency(150);
    }
    
    /**
     * Gets the Territory that contains this TerritoryHex.
     * @return Its Territory.
     */
    public Territory territory(){
       return TERRITORY;
        
    }
    
    /**
     * Calculates the distance between this and another TerritoryHex.
     * @param otherHex The other TerritoryHex.
     * @return The distance between those two TerritoryHexes, in the unit of the 
     *         Greenfoot coordinate grid.
     */
    public double distance(TerritoryHex otherHex) {
        return Math.sqrt(   (Math.pow(this.getX()  -  otherHex.getX(), 2))    +     (Math.pow(this.getY()  -  otherHex.getY(), 2))  );
        
    }
    
    /**
     * Draws the Color of the owner of this TerritoryHex's Territory.
     * @param p The owner of this TerritoryHex's Territory.
     */
    protected void drawPlayerColor(Player p){
        
        GreenfootImage img;
        
        if(p != null){
            img = Hexagon.createImage(p.color(), 0.5);
        }else{
            
            try{
                img = Hexagon.createImage(TERRITORY.continent().color(), 0.5);
            }catch(Exception e){
                img = Hexagon.createImage(Theme.used.territoryColor, 0.5);
            }
            
        }
        int offset = (int)(getImage().getWidth() - img.getWidth())/2;
        getImage().drawImage(img, offset, offset);
        
    }
    

    /**
     * Makes this TerritoryHex unclickable.
     */
    @Override
    public void toggleUnusable() {
        usable = false;
        
    }
    
    /**
     * Makes this TerritoryHex clickable.
     */
    @Override
    public void toggleUsable() {
        usable = true;
        
    }
    
}
