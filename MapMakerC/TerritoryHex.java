import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.JOptionPane;

public class TerritoryHex extends Button
{
    private Territory territory;
    private Coordinates coord = new Coordinates();
    
    private static MyWorld world() {return MyWorld.theWorld;}
    
    public TerritoryHex(Territory territory, Color color, int x, int y){
        this.territory = territory;
        drawTerrHex(color);
        coord.hexCoord[0] = x;
        coord.hexCoord[1] = y;
    }
    
    public Coordinates coordinates(){
        return coord;
    
    }
    
    public void clicked() {
        try {
            Mode mode = Mode.currentMode();

            if(mode == Mode.DEFAULT){
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
                    Selector.select(territory.continent());
                    Selector.setValidator(Selector.NOTHING);
                    world().repaint(); //pour forcer l'actualisation des images
                    territory.continent().editColor();
                    world().escape();
                    break;
                    
                case EDIT_CONTINENT_BONUS :
                    Selector.select(territory.continent());
                    Selector.setValidator(Selector.NOTHING);
                    world().repaint(); //pour forcer l'actualisation des images
                    territory.continent().editBonus();
                    world().escape();
                    break;
                    
                case DELETE_CONTINENT :
                    Selector.select(territory.continent());
                    Selector.setValidator(Selector.NOTHING);
                    break;
                    
                case SET_LINK :
                    if(Links.newLinks == null) {
                        int rColor = Integer.parseInt(JOptionPane.showInputDialog("Enter the shade of red (0 - 255)"));
                        int gColor = Integer.parseInt(JOptionPane.showInputDialog("Enter the shade of green (0 - 255)"));
                        int bColor = Integer.parseInt(JOptionPane.showInputDialog("Enter the shade of blue (0 - 255)"));
                        Color color = new Color(rColor,gColor,bColor);
                        Links.newLinks = new Links(color);
                    }
                    
                        MouseInfo mouse = Greenfoot.getMouseInfo();
                        int xPos = mouse.getX();
                        int yPos = mouse.getY();
                        new LinkIndic(territory,xPos,yPos);
                        
                    
                    break;
                    
                case CREATE_TERRITORY :
                case SELECT_INFO_HEX :
                    break;
                    
                default:
                    world().escape();
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
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
    
    public void drawTerrHex(Color color){   
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
