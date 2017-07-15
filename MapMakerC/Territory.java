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
    private Color continentColor = Color.blue;
    
    /////////////////////////////////////////////////////////////////////////////////////////
    
    public Territory(ArrayList<Coordinates> hexs) throws Exception
    {
        if(hexs.size() < 2) throw new Exception("At least 2 hexes must be selected");
        hexCoords = (Coordinates[])hexs.toArray(); //erreur java.lang.ClassCastException
        createTerrHexs();
        drawTerritory();
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
        
        drawTerritory();
        
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
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
        private void createTerrHexs()
        //crée tous les territoryHex de ce territoire
        {
            GreenfootImage img = Hexagon.createSimpleHexImage(continentColor,0.95);
            for(Coordinates hex : hexCoords){
                int[] rectCoord = hex.getRectCoord();
                int[] hexCoord = hex.getHexCoord();
                TerritoryHex trHex = new TerritoryHex();
                TerritoryHex2DArray[hexCoord[0]][hexCoord[1]] = trHex;
                getWorld().addObject(trHex, rectCoord[0], rectCoord[1]);
            }
        }
    
        private void drawTerritory()
        //dessine le territoire sur le monde
        {
            drawHexs();
            drawAllHexsLinks();
        }
    
            private void drawHexs()
            {
                GreenfootImage img = Hexagon.createHexagonImage(continentColor);
                for(Coordinates hex : hexCoords){
                    int[] rectCoord = hex.getRectCoord();
                    rectCoord[0] -= Hexagon.getSize();
                    rectCoord[1] -= Hexagon.getSize();
                    getBackground().drawImage(img, rectCoord[0], rectCoord[1]);
                }
            }
    
            private void drawAllHexsLinks()
            {
                for(Coordinates hex : hexCoords){
                    drawHexLinks(hex);
                }
            }
            
                private void drawHexLinks(Coordinates hex)
                {
                    Polygon[] links = getLinkPolygon(hex);
                    getBackground().setColor(continentColor);
                    for(Polygon poly : links){
                        getBackground().fillShape(poly);
                    }
                    
                }
    
                    private Polygon[] getLinkPolygon(Coordinates hex)
                    //"Buckle your seatbelt Dorothy, 'cause clean code is going bye-bye"
                    {
                        int[] hexCoord = hex.getHexCoord();
                        Polygon[] linksPoly = new Polygon[6];
                        int[][] temporary = new int[4][2]; 
                        temporary[0] = hex.getRectCoord();
                        int[][] temporary2 = new int[2][4];
                        Polygon poly;
                        //pour l'hex +1;0
                        if(TerritoryHex2DArray[hexCoord[0]+1][hexCoord[0]+0] != null)
                        {
                            int[] coord2 = {hexCoord[0]+1,hexCoord[0]+0};
                            temporary[1] = Hexagon.getHexagonCoord(1)[0];
                            temporary[2] = Coordinates.hexToRectCoord(coord2);
                            temporary[3] = Hexagon.getHexagonCoord(1)[1];
                            temporary2 = Hexagon.format(temporary);
                            poly = new Polygon(temporary2[0],temporary2[1],4);
                            linksPoly[0] = poly;
                        }
                        //pour l'hex 0;-1
                        if(hexCoord[1]>0){
                            if(TerritoryHex2DArray[hexCoord[0]+0][hexCoord[0]-1] != null)
                                {
                                    int[] coord2 = {hexCoord[0]+0,hexCoord[0]-1};
                                    temporary[1] = Hexagon.getHexagonCoord(1)[1];
                                    temporary[2] = Coordinates.hexToRectCoord(coord2);
                                    temporary[3] = Hexagon.getHexagonCoord(1)[2];
                                    temporary2 = Hexagon.format(temporary);
                                    poly = new Polygon(temporary2[0],temporary2[1],4);
                                    linksPoly[1] = poly;
                                }
                        }
                        //pour l'hex -1;0
                        if(hexCoord[0]>0){
                            if(TerritoryHex2DArray[hexCoord[0]-1][hexCoord[0]+0] != null)
                                {
                                    int[] coord2 = {hexCoord[0]-1,hexCoord[0]+0};
                                    temporary[1] = Hexagon.getHexagonCoord(1)[2];
                                    temporary[2] = Coordinates.hexToRectCoord(coord2);
                                    temporary[3] = Hexagon.getHexagonCoord(1)[3];
                                    temporary2 = Hexagon.format(temporary);
                                    poly = new Polygon(temporary2[0],temporary2[1],4);
                                    linksPoly[2] = poly;
                                }
                        }
                        //pour l'hex -1;+1
                        if(hexCoord[0]>0){
                            if(TerritoryHex2DArray[hexCoord[0]-1][hexCoord[0]+1] != null)
                                {
                                    int[] coord2 = {hexCoord[0]-1,hexCoord[0]+1};
                                    temporary[1] = Hexagon.getHexagonCoord(1)[3];
                                    temporary[2] = Coordinates.hexToRectCoord(coord2);
                                    temporary[3] = Hexagon.getHexagonCoord(1)[4];
                                    temporary2 = Hexagon.format(temporary);
                                    poly = new Polygon(temporary2[0],temporary2[1],4);
                                    linksPoly[3] = poly;
                                }
                        }
                        //pour l'hex 0;+1
                        if(TerritoryHex2DArray[hexCoord[0]+0][hexCoord[0]+1] != null)
                        {
                            int[] coord2 = {hexCoord[0]+0,hexCoord[0]+1};
                            temporary[1] = Hexagon.getHexagonCoord(1)[4];
                            temporary[2] = Coordinates.hexToRectCoord(coord2);
                            temporary[3] = Hexagon.getHexagonCoord(1)[5];
                            temporary2 = Hexagon.format(temporary);
                            poly = new Polygon(temporary2[0],temporary2[1],4);
                            linksPoly[4] = poly;
                        }
                        //pour l'hex +1;+1
                        if(TerritoryHex2DArray[hexCoord[0]+1][hexCoord[0]+1] != null)
                        {
                            int[] coord2 = {hexCoord[0]+1,hexCoord[0]+1};
                            temporary[1] = Hexagon.getHexagonCoord(1)[5];
                            temporary[2] = Coordinates.hexToRectCoord(coord2);
                            temporary[3] = Hexagon.getHexagonCoord(1)[6];
                            temporary2 = Hexagon.format(temporary);
                            poly = new Polygon(temporary2[0],temporary2[1],4);
                            linksPoly[5] = poly;
                        }
                        
                        return linksPoly;
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
