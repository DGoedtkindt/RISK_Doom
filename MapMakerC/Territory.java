import greenfoot.*; 
import java.awt.*;
import java.util.ArrayList;
import java.lang.Exception;

public class Territory 
{

    
    private Coordinates[] hexCoords;
    TerritoryHex[][] TerritoryHex2DArray = new TerritoryHex[50][30];
    private GreenfootImage getBackground() {return MyWorld.theWorld.getBackground();}
    private MyWorld getWorld() {return MyWorld.theWorld;}
    private static int nextId = 0; //stoque le prochain ID a attribuer à un territoire
    private int id; //l'identifiant de ce territoire
    
    public Territory(ArrayList<Coordinates> hexs) throws Exception
    {
        if(hexs.size() < 2) throw new Exception("At least 2 hexes must be selected");
        hexCoords = (Coordinates[])hexs.toArray(); //erreur java.lang.ClassCastException
        paint();
        createTerrHexs();
        deleteSingleHexs();
        setId();
    }
    


    private void setId()
    {
        id = nextId;
        nextId++;
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
    
    public int getId()
    {
        return id;
    }
    
    private void createTerrHexs()
    //crée tous les territoryHex de ce territoire
    {
        GreenfootImage img = Hexagon.createSimpleHexImage(Color.blue,0.95);
        for(Coordinates hex : hexCoords){
            int[] rectCoord = hex.getRectCoord();
            TerritoryHex trHex = new TerritoryHex();
            TerritoryHex2DArray[rectCoord[0]][rectCoord[1]] = trHex;
            getWorld().addObject(trHex, rectCoord[0], rectCoord[1]);
        }
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
        
    }
    
    private void deleteSingleHexs()
    {
        for(Coordinates hex : hexCoords){
            int[] hexCoord = hex.getHexCoord();
            SingleHex hexToDel = getWorld().singleHex2DArray[hexCoord[0]][hexCoord[1]];
            getWorld().removeObject(hexToDel);
        }
       
    }
}
