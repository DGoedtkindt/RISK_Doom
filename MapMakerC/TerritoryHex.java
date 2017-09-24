import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.JOptionPane;
import greenfoot.MouseInfo;
import greenfoot.Greenfoot;

public class TerritoryHex extends Button
{
    private Territory territory;
    private Coordinates coord = new Coordinates();
    public ArrayList<LinkSpot> linksPlacedInIt = new ArrayList<LinkSpot>();
    
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

            if(mode == Mode.CREATE_CONTINENT ||
               mode == Mode.DELETE_TERRITORY){
                    Selector.select(territory());

            }else if(mode == Mode.EDIT_TERRITORY_BONUS) {
                Selector.select(territory);
                Selector.setValidator(Selector.NOTHING);
                MyWorld.theWorld.repaint(); //pour forcer l'actualisation des images
                territory.editBonus();
                MyWorld.theWorld.escape();

            }else if(mode == Mode.EDIT_CONTINENT_COLOR) {
                Selector.select(territory.continent());
                Selector.setValidator(Selector.NOTHING);
                MyWorld.theWorld.repaint(); //pour forcer l'actualisation des images
                territory.continent().editColor();
                MyWorld.theWorld.escape();

            }else if(mode == Mode.EDIT_CONTINENT_BONUS) {
                Selector.select(territory.continent());
                Selector.setValidator(Selector.NOTHING);
                MyWorld.theWorld.repaint(); //pour forcer l'actualisation des images
                territory.continent().editBonus();
                MyWorld.theWorld.escape();

            }else if(mode == Mode.DELETE_CONTINENT){
                Selector.select(territory.continent());
                Selector.setValidator(Selector.NOTHING);

            }else if(mode == Mode.SET_LINK) {
                if(Link.currentLink == null){
                    
                    int rColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de rouge (int)"));
                    int gColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de vert (int)"));
                    int bColor = Integer.parseInt(JOptionPane.showInputDialog("Entrez la teinte de bleu (int)"));
                    Color linkColor = new Color(rColor, gColor, bColor);
                    Link.currentLink = new Link(linkColor);
                }
                
                if(!territory.links.contains(Link.currentLink)){
                    
                    MouseInfo mouse = Greenfoot.getMouseInfo();

                    Link.currentLink.linkedTerritories.add(territory);
                    territory.links.add(Link.currentLink);
                    int[] relativePos = {mouse.getX() - this.getX(), mouse.getY() - this.getY()};
                    LinkSpot spot = new LinkSpot(Link.currentLink, this, relativePos);
                    linksPlacedInIt.add(spot);
                    
                    int x = spot.terrHexCoordinates.rectCoord()[0] + spot.relativePosition[0];
                    int y = spot.terrHexCoordinates.rectCoord()[1] + spot.relativePosition[1];
                    getWorld().addObject(spot, x, y);
                    
                }
                
            }else{
                MyWorld.theWorld.escape();

            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            MyWorld.theWorld.escape();
        }
        
    }
    
    public ArrayList<TerritoryHex> getBorderingHexes() {
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
