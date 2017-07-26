import greenfoot.*; 
import java.awt.*;
import java.awt.geom.Area;
import java.util.*;
import java.lang.Exception;

public class Territory implements Maskable
{
    private HashSet<Coordinates> hexCoordSet;
    private ArrayList<TerritoryHex> terrHexList = new ArrayList<TerritoryHex>();
    private HashSet<Integer> borderingTerritoriesIDSet = new HashSet<Integer>();
    private GreenfootImage getBackground() {return MyWorld.theWorld.getBackground();}
    private MyWorld getWorld() {return MyWorld.theWorld;}
    private static int nextId = 0; //stoque le prochain ID a attribuer à un territoire
    private int id; //l'identifiant de ce territoire
    private Color continentColor = new Color(143,134,155);
    private int bonusPoints = 0;
    
    
    
    //Public methods///////////////////////////////////////////////////////////////////////////////////////
    
    public Territory(HashSet<Coordinates> hexs)  throws Exception
    {
        if(hexs.size() < 2) throw new Exception("At least 2 hexes must be selected");
        hexCoordSet = hexs;
        createTerrHexs();
        drawTerritory();
        deleteSingleHexs();
        setId();
    }
    
    public Area getAreaShape()
    {

        
        return null;
        
    }
    
    public void destroy()
    {
        
        
        
    }

    public void setContinent(Color color)
    {
        continentColor = color;
        
        drawTerritory();
        
        for(TerritoryHex hex : terrHexList){
                
                hex.drawTerrHex(color);
                
        }
        
    }
    
    public void setNewLink(int newLink)
    {
        borderingTerritoriesIDSet.add(newLink);
    }

    public void autoSetLinks() //works normally, but was not tested
    {
         Set<TerritoryHex> borderingHexSet = new HashSet<TerritoryHex>();
         
         
         borderingHexSet = getBorderingHex();
         for(TerritoryHex hex : borderingHexSet)
         {
            borderingTerritoriesIDSet.add(hex.getTerritory().getId());
         }
         
         borderingTerritoriesIDSet.remove(this.getId());
    }
    
    public int getId()
    {
        return id;
    }
    
    public int getBonusPoints()
    {
        return bonusPoints;
    }
    
    public Integer[] getBorderTerritoryIDs()
    {
        Integer[] borderTerritoryIDs = new Integer[10];
        borderingTerritoriesIDSet.toArray(borderTerritoryIDs);
        
        return borderTerritoryIDs;
    }
    
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
        private void createTerrHexs()
        //crée tous les territoryHex de ce territoire
        {
            GreenfootImage img = Hexagon.createSimpleHexImage(continentColor,0.95);
            
            for(Coordinates hex : hexCoordSet){
                    
                    int[] rectCoord = hex.getRectCoord();
                    TerritoryHex trHex = new TerritoryHex(this,continentColor);
                    terrHexList.add(trHex);
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
                
                for(TerritoryHex hex : terrHexList){
                        
                        int xPos = hex.getX() - Hexagon.getSize();
                        int yPos = hex.getY() - Hexagon.getSize();
                        getBackground().drawImage(img, xPos, yPos);

                }
            }
    
            private void drawAllHexsLinks()
            {
                for(TerritoryHex hex : terrHexList){
                        
                        drawHexLinks(hex);

                }
            }
            
                private void drawHexLinks(TerritoryHex hex)
                {
                    ArrayList<Polygon> links = getLinkPolygon(hex);
                    getBackground().setColor(continentColor);
                    
                    for(Polygon poly : links){
                            
                            getBackground().fillShape(poly);

                    }
                    
                }
    
                    private ArrayList<Polygon> getLinkPolygon(TerritoryHex thisHex)
                    //returne des losanges qui couvrent certains bords noirs de l'Hexagone
                    //dirty code incoming!
                    {
                        ArrayList<Polygon> linksPoly = new ArrayList<Polygon>();
                        int[][] temporary = new int[2][4]; //stoque temporairement les coordonée d'un logange
                        
                        temporary[0][0] = thisHex.getX();
                        temporary[1][0] = thisHex.getY();
                        
                        for(TerritoryHex otherHex : terrHexList){
                                if(otherHex != thisHex){ // ne pas faire de lien avec soi-même
                                    if(thisHex.distance(otherHex) < 2.2*Hexagon.getSize()) { //pour ne lier que les hex adjacents
                                        
                                        temporary[0][2] = otherHex.getX();
                                        temporary[1][2] = otherHex.getY();
                                        
                                        double angle = Math.atan2(otherHex.getY()-thisHex.getY(), otherHex.getX()-thisHex.getX());
                                        
                                        temporary[0][1] = temporary[0][0] + (int)(Hexagon.getSize() * Math.cos(angle + Math.PI/6));
                                        temporary[1][1] = temporary[1][0] + (int)(Hexagon.getSize() * Math.sin(angle + Math.PI/6));
                                        temporary[0][3] = temporary[0][0] + (int)(Hexagon.getSize() * Math.cos(angle - Math.PI/6));
                                        temporary[1][3] = temporary[1][0] + (int)(Hexagon.getSize() * Math.sin(angle - Math.PI/6));
                                        
                                        
                                        linksPoly.add(new Polygon(temporary[0],temporary[1],4));
                                        
                                    }
                                }
                        }
                        
                        
                        return linksPoly;
                        
                    }
                
        private void deleteSingleHexs()
        {
            for(Coordinates hex : hexCoordSet){
                    
                    int[] hexCoord = hex.getHexCoord();
                    SingleHex hexToDel = getWorld().singleHex2DArray[hexCoord[0]][hexCoord[1]];
                    getWorld().removeObject(hexToDel);

            }
           
        }
        
    /////////////////////////////////////////////////////////////////////////////////////////////    
    
        private void setId()
        {
            
            id = nextId;
            nextId++;
            
        }
        
        private HashSet<TerritoryHex> getBorderingHex()
        {
            HashSet<TerritoryHex> borderingHexSet = new HashSet<TerritoryHex>();
            ArrayList<TerritoryHex> temporary = new ArrayList<TerritoryHex>();// cette liste
            // contiendra les TerritoryHex le temps de les rajouter dans
            // borderingHexSet
            
            for(TerritoryHex hex : this.terrHexList){
                    
                    temporary = hex.getBorderingHex();
                    
                    for(TerritoryHex otherHex : temporary){
                        
                        borderingHexSet.add(otherHex);
                        
                    }

            }
            
            return borderingHexSet;
        }
}
