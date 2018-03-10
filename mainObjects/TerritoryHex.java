package mainObjects;

import appearance.Appearance;
import appearance.InputPanel;
import game.Player;
import appearance.MessageDisplayer;
import base.Button;
import base.ColorChooser;
import base.GColor;
import base.Hexagon;
import base.InputPanelUser;
import game.Turn;
import mode.Mode;
import selector.Selector;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class that represents the hexagons contained in a Territory.
 * 
 */
public class TerritoryHex extends Button implements InputPanelUser{
    private Territory territory;
    private int[] hexCoord = new int[2];
    
    /**
     * Creates a TerritoryHex.
     * @param territory The Territory in which it is contained.
     * @param color The Color of this TerritoryHex.
     * @param x The x hexagonal coordinate of this TerritoryHex.
     * @param y The y hexagonal coordinate of this TerritoryHex.
     */
    public TerritoryHex(Territory territory, GColor color, int x, int y){
        this.territory = territory;
        drawTerrHex(color);
        hexCoord[0] = x;
        hexCoord[1] = y;
    }
    
    /**
     * Gets the hexagonal coordinates of this TerritoryHex
     * @return Its hexagonal coordinates.
     */
    public int[] hexCoord() {
        return hexCoord;
    }
    
    /**
     * Gets the rectangular, normal of this TerritoryHex
     * @return Its rectangular, normal coordinates.
     */
    public int[] rectCoord() {
        return Hexagon.hexToRectCoord(hexCoord);
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
                    Selector.select(territory);
                    Selector.setValidator(Selector.NOTHING);
                    world().repaint(); //pour forcer l'actualisation des images
                    territory.editBonus();
                    world().stateManager.escape();;
                    break;
                    
                case EDIT_CONTINENT_COLOR :
                    if(territory.continent() != null){
                        Selector.select(territory.continent());
                        Selector.setValidator(Selector.NOTHING);
                        world().repaint(); //pour forcer l'actualisation des images
                        territory.continent().editColor();
                    }
                    world().stateManager.escape();;
                    break;
                    
                case EDIT_CONTINENT_BONUS :
                    if(territory.continent() != null){
                        Selector.select(territory.continent());
                        Selector.setValidator(Selector.NOTHING);
                        world().repaint(); //pour forcer l'actualisation des images
                        territory.continent().editBonus();
                    }
                    world().stateManager.escape();;
                    break;
                    
                case DELETE_CONTINENT :
                    Selector.select(territory.continent());
                    break;
                    
                case SET_LINK :
                    if(Links.newLinks == null) {
                        GColor newColor = ColorChooser.getColor();
                        Links.newLinks = new Links(newColor);
                        Links.newLinks.addToWorld();
                        
                    }
                    
                    if(!Links.newLinks.linkedTerrs.contains(territory)){
                        
                       MouseInfo mouse = Greenfoot.getMouseInfo();
                        int xPos = mouse.getX();
                        int yPos = mouse.getY();
                        new LinkIndic(territory,xPos, yPos).addToWorld(); 
                        
                    }
                    
                    break;
                    
                case ATTACK : 
                    if(Territory.actionSource == null
                       && territory.owner() == Turn.currentTurn.player){
                        
                        Territory.actionSource = territory;
                        Selector.select(territory);
                        Selector.setValidator(Selector.IS_ATTACKABLE);
                        
                    }else if(territory.owner() != Turn.currentTurn.player){
                        
                        Selector.select(territory);
                        Territory.actionTarget = territory;
                        try{
                            Territory.actionSource.invade(Territory.actionTarget);
                        }catch(Exception e){
                            MessageDisplayer.showMessage(e.getMessage());
                        }
                        Selector.setValidator(Selector.NOTHING);
                        world().stateManager.escape();
                    }
                    
                    break;
                
                case MOVE : 
                    if(territory.owner() == Turn.currentTurn.player){
                        
                        if(Territory.actionSource == null){
                            Territory.actionSource = territory;
                            Selector.select(territory);
                        }else{
                            Selector.select(territory);
                            Territory.actionTarget = territory;
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
                    if(territory.owner() == Turn.currentTurn.player){
                        
                        InputPanel.showInsertionPanel("The number of armies you want to put on this territory.", 
                                                      100, 
                                                      "added_armies", 
                                                      this, 
                                                      Appearance.WORLD_WIDTH / 2, 
                                                      Appearance.WORLD_HEIGHT / 2);
                        
                    }
                    
                    break;
                    
                case SAP : 
                    if(territory.owner() != Turn.currentTurn.player){
                        territory.owner().addArmiesToHand(territory.armies());
                        territory.setOwner(null);
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
       return territory;
        
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
        
        if(p != null){
            getImage().drawImage(Hexagon.createImage(p.color(), 0.5), 0, 0);
        }
        
    }

    @Override
    public void useInformations(String information, String type) throws Exception {
        
        if(type.equals("added_armies")){
            
            if(information.matches("\\d+")){
                            
                int newArmies = Integer.parseInt(information);

                if(newArmies < 0){
                    MessageDisplayer.showMessage("This is a negative number.");
                }else if(newArmies > ((TerritoryHex)this).territory.owner().armiesInHand()){
                    MessageDisplayer.showMessage("You don't have enough armies.");
                }else{
                    ((TerritoryHex)this).territory.addArmies(newArmies);
                    ((TerritoryHex)this).territory.owner().addArmiesToHand(-newArmies);
                    ((TerritoryHex)this).territory.drawTerritory();
                }

            }else{
                MessageDisplayer.showMessage("Invalid entry.");
            }
            
        }
        
    }
    
}
