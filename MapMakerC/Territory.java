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
    public static final int MAX_HEX = 20;
    

    TerritoryHex[][] TerritoryHex2DArray = new TerritoryHex[50][30];
    
    private int capitalBonus = 0;
    
    private Territory[] borderingTerritories = new Territory[20];
    
    private int borderingNumber = 0;
    
    private GreenfootImage getBackground() {
        
        return MyWorld.theWorld.getBackground();
    
    }
    
    
    private MyWorld getWorld() {
        
        return MyWorld.theWorld;
    
    }
    
    

    private Coordinates[] hexCoords = new Coordinates[MAX_HEX];
    private TerritoryHex[] terrHexArray = new TerritoryHex[MAX_HEX];
    private Set<Integer> borderingTerritoriesIDSet = new HashSet<Integer>();
    private static int nextId = 0; //stoque le prochain ID a attribuer à un territoire
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
        if(hexs.size() > MAX_HEX) throw new Exception("Maximum Hex selected " + MAX_HEX);
        hexs.toArray(hexCoords);
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
        
        for(TerritoryHex hex : terrHexArray){
            if(hex != null){
                
                hex.drawTerrHex(color);
                
            }
        }
        
    }
    
    public void setNewLink(Territory newLink)
    {
        
        boolean alreadyPresent = false;
        
        for(Territory terr : borderingTerritories){
            
            if(terr == newLink){
                
                alreadyPresent = true;
                
            }
            
        }
        
        if(!alreadyPresent){
            
            borderingTerritories[borderingNumber] = newLink;
            borderingNumber++;
            
        }
        
        borderingTerritoriesIDSet.add(newLink.getId());
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
    
    public Coordinates[] getComposingHex()
    {
        return hexCoords;
    }
    
    public TerritoryHex[] getComposingTerrHex(){
        
        return terrHexArray;
        
    }
    
    private void drawHexsLinks(){
    
    
    
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
        int hexCount = 0;
        
        for(Coordinates hex : hexCoords){
           if(hex != null){
                
                int[] rectCoord = hex.getRectCoord();
                TerritoryHex trHex = new TerritoryHex(this,continentColor);
                terrHexArray[hexCount] = trHex;
                getWorld().addObject(trHex, rectCoord[0], rectCoord[1]);
                hexCount ++;
                
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
                
            }
        }
    }

    private void drawAllHexsLinks()
    {
        for(Coordinates hex : hexCoords){
            if(hex != null){ 
                
                drawHexLinks(hex);
            
            }
        }
    }
            
    private void drawHexLinks(Coordinates hex)
    {
        Polygon[] links = getLinkPolygon(hex);
        getBackground().setColor(continentColor);
        
        for(Polygon poly : links){
            if(poly != null) {
                
                getBackground().fillShape(poly);
            
            }
        }
        
    }
    
    private Polygon[] getLinkPolygon(Coordinates thisHex)
    //returne des losanges qui couvrent certains bords noirs de l'Hexagone
    //dirty code incoming!
    {
        Polygon[] linksPoly = new Polygon[6];
        int[] thisRectCoord = thisHex.getRectCoord();
        int linksCount = 0;
        int[][] temporary = new int[2][4]; //stoque temporairement les coordonée d'un logange
        
        temporary[0][0] = thisRectCoord[0];
        temporary[1][0] = thisRectCoord[1];
        
        for(Coordinates otherHex : hexCoords){
            if(otherHex != null){
                if(otherHex != thisHex){ // ne pas faire de lien avec soi-même
                    if(thisHex.distance(otherHex) < 2.2*Hexagon.getSize()) { //pour ne lier que les hex adjacents
                        
                        int[] otherRectCoord = otherHex.getRectCoord();
                        temporary[0][2] = otherRectCoord[0];
                        temporary[1][2] = otherRectCoord[1];
                        
                        double angle = Math.atan2(otherRectCoord[1]-thisRectCoord[1], otherRectCoord[0]-thisRectCoord[0]);
                        
                        temporary[0][1] = temporary[0][0] + (int)(Hexagon.getSize() * Math.cos(angle + Math.PI/6));
                        temporary[1][1] = temporary[1][0] + (int)(Hexagon.getSize() * Math.sin(angle + Math.PI/6));
                        temporary[0][3] = temporary[0][0] + (int)(Hexagon.getSize() * Math.cos(angle - Math.PI/6));
                        temporary[1][3] = temporary[1][0] + (int)(Hexagon.getSize() * Math.sin(angle - Math.PI/6));
                        
                        
                        linksPoly[linksCount] = new Polygon(temporary[0],temporary[1],4);
                        linksCount++;
                        
                    }
                }
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
            
            if(hex != null){
                
                temporary = hex.getBorderingHex();
                
                for(TerritoryHex otherHex : temporary){
                    
                    borderingHexSet.add(otherHex);
                    
                }
                
            }
        }
        
        return borderingHexSet;
    }
}
