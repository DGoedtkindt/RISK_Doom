import greenfoot.*; 
import java.awt.Color;
import java.awt.Shape;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
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
    private static int nextId = 0; //stoque le prochain ID à attribuer à un territoire

    private int id; //l'identifiant de ce territoire
    private Continent continent;
    private Color continentColor = new Color(143,134,155);
    private int bonusPoints = 0;
    
    
    
    //Public methods///////////////////////////////////////////////////////////////////////////////////////
    

    
    public int getCapitalBonus(){
        
        return capitalBonus;
        
    }
    
    
    public void changeThisBonus(int newBonus){
        
        capitalBonus = newBonus;
        
    }
    
    
    public void changeCapital(int changedCapitalBonus){
        
        for(Territory otherTerritory : continent.getContainedTerritories()){
            
            otherTerritory.changeThisBonus(0);
            
        }
        
        capitalBonus = changedCapitalBonus;
        
    }
    
    
    public Continent getContinent(){
        
        return continent;
        
    }
    
    
    
    
    
    public Territory[] getBorderingTerritories(){
        
        return borderingTerritories;
        
    }
    
    
    public Set calculateBorderingTerritories(){
        
        Set hexesAround = new HashSet();
        
        
        for(TerritoryHex terrHex : getComposingTerrHex()){// Met tous les territoryhex autour du territoire dans le set
            
            List<TerritoryHex> terrHexAround = terrHex.getTerrHexInRange((int)(1.5 * Hexagon.HEXAGON_SIZE));
            
            for(TerritoryHex territoryhex : terrHexAround){
                
                hexesAround.add(territoryhex);
                
            }
            
        }
        
        for(TerritoryHex territHex : getComposingTerrHex()){// enlève les territoryhex qui sont dans le territoire
            
            hexesAround.remove(territHex);
            
        }
        
        Set territoriesAround = new HashSet();
        
        for(Object hexAround : hexesAround){// obtient la liste des territoires limitrophes à partir de cette liste de hex
            
            Territory borderingTerritory = ((TerritoryHex)hexAround).getTerritory();
            
            territoriesAround.add(borderingTerritory);
            
        }
        
        return territoriesAround;
        
    }
    
    
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
    
    public void setContinent(Continent newContinent){
        
        continent = newContinent;
        
    }
    
    
    public void setNewLink(Territory newLink)
    {
        
        boolean alreadyPresent = false;
        
        for(Territory terr : borderingTerritories){//Regarde s'il est déjà limitrophe
            
            if(terr == newLink){
                
                alreadyPresent = true;
                
            }
            
        }
        
        if(!alreadyPresent){//Sinon, le rajoute
            
            borderingTerritories[borderingNumber] = newLink;
            borderingNumber++;
            
        }
        
        borderingTerritoriesIDSet.add(newLink.getId());
    }

    public void autoSetLinks() //Should work, but was not tested
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
    
    public int getBonusPoints()
    {
        return bonusPoints;
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
            if(hex != null){
                
                int[] rectCoord = hex.getRectCoord();
                rectCoord[0] -= Hexagon.getSize();
                rectCoord[1] -= Hexagon.getSize();
                getBackground().drawImage(img, rectCoord[0], rectCoord[1]);
                

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

                        
                        
                        linksPoly[linksCount] = new Polygon(temporary[0],temporary[1],4);
                        linksCount++;
                        
                    }

                
        private void deleteSingleHexs()
        {
            for(Coordinates hex : hexCoordSet){
                    
                    int[] hexCoord = hex.getHexCoord();
                    SingleHex hexToDel = getWorld().singleHex2DArray[hexCoord[0]][hexCoord[1]];
                    getWorld().removeObject(hexToDel);


            }
        }
        
        
        return linksPoly;
        
    }
                
    private void deleteSingleHexs()
    {
        for(Coordinates hex : hexCoords){
            if(hex != null){
                
                int[] hexCoord = hex.getHexCoord();
                SingleHex hexToDel = getWorld().singleHex2DArray[hexCoord[0]][hexCoord[1]];
                getWorld().removeObject(hexToDel);
                
            }
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
        
        for(TerritoryHex hex : this.terrHexArray){
            

            for(TerritoryHex hex : this.terrHexList){
                    
                    temporary = hex.getBorderingHex();
                    
                    for(TerritoryHex otherHex : temporary){
                        
                        borderingHexSet.add(otherHex);
                        
                    }


            }
        }
        
        return borderingHexSet;
    }
}
