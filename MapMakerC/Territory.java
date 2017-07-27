import greenfoot.*; 
import java.awt.*;
import java.awt.geom.Area;
import java.util.*;
import java.lang.Exception;

public class Territory implements Maskable
{
    public static ArrayList<Territory> territoryList = new ArrayList<Territory>();
    private static int nextId = 0; //stoque le prochain ID à attribuer à un territoire
    private int id; //l'identifiant de ce territoire
    private HashSet<Coordinates> hexCoordSet;
    private ArrayList<TerritoryHex> terrHexList = new ArrayList<TerritoryHex>();
    private HashSet<Territory> borderingTerritorySet = new HashSet<Territory>();
    private GreenfootImage getBackground() {return MyWorld.theWorld.getBackground();}
    private MyWorld getWorld() {return MyWorld.theWorld;}
    private Continent continent;
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
        Area thisShape = new Area();
        
        for(TerritoryHex hex : terrHexList){
        
            thisShape.add(hex.getAreaShape());
            
        }
        
        return thisShape;
        
    }
    
    public void destroy()
    {
        territoryList.set(id, null);
        
        if(continent != null) continent.removeTerritory(this);
        
        for(Territory otherTerr : territoryList){
            if(otherTerr != null) {
                
                otherTerr.removeLink(otherTerr);
                
            }
        }
        
        
    }

    public void setContinent(Continent newContinent)
    {
        continent = newContinent;
        continentColor = newContinent.getContinentColor();
        
        drawTerritory();
        
        for(TerritoryHex hex : terrHexList){
                
                hex.drawTerrHex(continentColor);
                
        }
        
    }
    
    public void setNewLink(Territory newLink)
    {
        borderingTerritorySet.add(newLink);
    }

    public void autoSetLinks() //Should work, but was not tested
    {
         Set<TerritoryHex> borderingHexSet = new HashSet<TerritoryHex>();
         
         
         borderingHexSet = getBorderingHex();
         for(TerritoryHex hex : borderingHexSet)
         {
            borderingTerritorySet.add(hex.getTerritory());
         }
         
         borderingTerritorySet.remove(this);
    }
    
    public void removeLink(Territory terrToRemove)
    {
        borderingTerritorySet.remove(terrToRemove);
    }
    
    public int getId()
    {
        return id;
    }
    
    public int getBonusPoints()
    {
        return bonusPoints;
    }
    
    public ArrayList<Territory> getBorderTerritories()
    {
        ArrayList<Territory> borderTerritoryList = new ArrayList<Territory>();
        borderTerritoryList.addAll(0, borderingTerritorySet);
        
        return borderTerritoryList;
    }
    
    public ArrayList<TerritoryHex> getComposingHex()
    {

        return terrHexList;
        
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
                        int[][] temporary = new int[2][4]; //cette liste
                        //stoque temporairement les coordonée d'un logange
                        //le temps d'être transformé en Polygon
                        
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
                    SingleHex hexToDel = SingleHex.array2D[hexCoord[0]][hexCoord[1]];
                    hexToDel.destroy();

            }
        }
        
    /////////////////////////////////////////////////////////////////////////////////////////////    
    
    
    
        private void setId()
        {
            
            id = nextId;
            territoryList.add(this);
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
                    borderingHexSet.addAll(temporary);

            }
            
            return borderingHexSet;
        }
}
