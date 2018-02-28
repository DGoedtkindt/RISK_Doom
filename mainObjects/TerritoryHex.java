package mainObjects;

import game.Player;
import appearance.MessageDisplayer;
import base.Button;
import base.ColorChooser;
import base.GColor;
import base.Hexagon;
import game.Turn;
import mode.Mode;
import selector.Selector;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class TerritoryHex extends Button
{
    private Territory territory;
    private int[] hexCoord = new int[2];
    
    public TerritoryHex(Territory territory, GColor color, int x, int y){
        this.territory = territory;
        drawTerrHex(color);
        hexCoord[0] = x;
        hexCoord[1] = y;
    }
    
    public int[] hexCoord() {
        return hexCoord;
    }
    
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
                        try{
                            Territory.actionSource.invade(territory);
                        }catch(Exception e){
                            MessageDisplayer.showMessage(e.getMessage());
                        }
                        Territory.actionSource = null;
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
                            try{
                                Territory.actionSource.moveTo(territory);
                            }catch(Exception e){
                                MessageDisplayer.showMessage(e.getMessage());
                            }
                            Territory.actionSource = null;
                            Selector.setValidator(Selector.NOTHING);
                            world().stateManager.escape();
                        }
                        
                    }
                    
                    break;
                    
                case CLEARING_HAND :
                    if(territory.owner() == Turn.currentTurn.player){
                        
                        String newArmiesString = JOptionPane.showInputDialog("The number of armies you want to put on this territory");
                        
                        if(newArmiesString.matches("\\d+")){
                            
                            int newArmies = Integer.parseInt(newArmiesString);
                            
                            if(newArmies < 0){
                                MessageDisplayer.showMessage("This is a negative number.");
                            }else if(newArmies > territory.owner().armiesInHand()){
                                MessageDisplayer.showMessage("You don't have enough armies.");
                            }else{
                                territory.addArmies(newArmies);
                                territory.owner().addArmiesToHand(-newArmies);
                                territory.drawTerritory();
                            }
                            
                        }else{
                            MessageDisplayer.showMessage("Invalid entry.");
                        }
                        
                    }
                    
                    break;
                    
                case SAP : 
                    if(territory.owner() != Turn.currentTurn.player){
                        territory.owner().addArmiesToHand(territory.armies());
                        territory.setOwner(null);
                        Turn.currentTurn.player.combos().useSap();
                        world().stateManager.escape();
                    }
                    
                default: break;
            }
        } catch (Exception ex) {
            MessageDisplayer.showException(ex);
            System.err.println(ex);
            world().stateManager.escape();
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
    
    public void drawTerrHex(GColor color){   
        this.setImage(Hexagon.createImage(color, 0.95));
        this.getImage().setTransparency(150);
    }
    
    public Territory territory(){
       return territory;
        
    }

    public double distance(TerritoryHex otherHex) {
        return Math.sqrt(   (Math.pow(this.getX()  -  otherHex.getX(), 2))    +     (Math.pow(this.getY()  -  otherHex.getY(), 2))  );
        
    }
    
    protected void drawPlayerColor(Player p){
        
        if(p != null){
            getImage().drawImage(Hexagon.createImage(p.color(), 0.5), 0, 0);
            getImage().drawString("" + (territory.armies()  + territory.owner().battlecryBonus), 20, 40);
        }
        
    }
    
}
