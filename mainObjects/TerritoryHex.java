package mainObjects;

import links.Links;
import links.LinkIndic;
import Selection.Selector;
import base.*;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import java.util.ArrayList;
import java.util.List;
public class TerritoryHex extends Button
{
    private Territory territory;
    private int[] hexCoord = new int[2];
    
    private static MyWorld world() {return MyWorld.theWorld;}
    
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
            Mode mode = Mode.currentMode();

            if(mode == Mode.MAP_EDITOR_DEFAULT){
                world().escape();
                
            }else switch (mode) {
                case CREATE_CONTINENT :
                case DELETE_TERRITORY :
                    Selector.select(territory());
                    break;
                    
                case EDIT_TERRITORY_BONUS :
                    Selector.select(territory);
                    Selector.setValidator(Selector.NOTHING);
                    world().repaint(); //pour forcer l'actualisation des images
                    territory.editBonus();
                    world().escape();
                    break;
                    
                case EDIT_CONTINENT_COLOR :
                    if(territory.continent() != null){
                        Selector.select(territory.continent());
                        Selector.setValidator(Selector.NOTHING);
                        world().repaint(); //pour forcer l'actualisation des images
                        territory.continent().editColor();
                    }
                    world().escape();
                    break;
                    
                case EDIT_CONTINENT_BONUS :
                    if(territory.continent() != null){
                        Selector.select(territory.continent());
                        Selector.setValidator(Selector.NOTHING);
                        world().repaint(); //pour forcer l'actualisation des images
                        territory.continent().editBonus();
                    }
                    world().escape();
                    break;
                    
                case DELETE_CONTINENT :
                    Selector.select(territory.continent());
                    Selector.setValidator(Selector.NOTHING);
                    break;
                    
                case SET_LINK :
                    if(Links.newLinks == null) {
                        GColor newColor = ColorChooser.getColor();
                        Links.newLinks = new Links(newColor);
                        
                    }
                    
                    if(!Links.newLinks.linkedTerrs.contains(territory)){
                        
                       MouseInfo mouse = Greenfoot.getMouseInfo();
                        int xPos = mouse.getX();
                        int yPos = mouse.getY();
                        new LinkIndic(territory,xPos, yPos); 
                        
                    }
                    
                    break;
                    
                case CREATE_TERRITORY :
                case SELECT_INFO_HEX :
                    break;
                    
                default:
                    world().escape();
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            world().escape();
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
    
}