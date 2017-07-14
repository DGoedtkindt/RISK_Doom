import greenfoot.*; 
import java.awt.*;
import java.util.ArrayList;
import java.lang.Exception;

public class Territory 
{
    private Coordinates[] hexCoords;
    TerritoryHex[][] TerritoryHex2DArray = new TerritoryHex[50][30];
    private GreenfootImage getBackground() {return MyWorld.theWorld.getBackground();}
    
    public Territory(ArrayList<Coordinates> hexs) throws Exception
    {
        if(hexs.size() < 2) throw new Exception("At least 2 hexes must be selected");
        hexCoords = (Coordinates[])hexs.toArray(); //erreur java.lang.ClassCastException
        paint();
        createTerrHexs();
    
    }
    
    public Shape getAreaShape()
    //retourne la forme du territoire pour le masque
    {
        
        return null;
        
    }
    
    public void destroy()
    {
        
        
        
    }
    
    public void setContinent()
    {
        
        
        
    }
    
    public void setNewLink(Territory newLink)
    {
        
        
        
    }
    
    public void autoSetLinks()
    {
    
    }
    
    private void createTerrHexs()
    //crée tous les territoryHex de ce territoire
    {
        
    }
    
    private void paint()
    //dessine le territoire sur le monde
    {
        drawHexs();
        drawHexsLinks();
    }
    
    private void drawHexs()
    {
        GreenfootImage img = Hexagon.createHexagonImage(Color.blue);
        for(Coordinates hex : hexCoords){
            int[] rectCoord = hex.getRectCoord();
            rectCoord[0] -= Hexagon.getSize();
            rectCoord[1] -= Hexagon.getSize();
            getBackground().drawImage(img, rectCoord[0], rectCoord[1]);
        }
    }
    
    private void drawHexsLinks()
    {
        for(Coordinates hex : hexCoords) {
            
        }
    }
}
