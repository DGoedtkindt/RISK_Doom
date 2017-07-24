import greenfoot.*; 
import java.awt.*;
import java.util.ArrayList;
import java.lang.Exception;

public class Territory 
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
    private int[] borderTerritoryIDs = new int[10]; 
    
    private static int nextId = 0; //stoque le prochain ID a attribuer à un territoire
    private int id; //l'identifiant de ce territoire
    private Color continentColor = Color.blue;
    private Continent continent;
    
    
    
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
    
    
    
    
    
    public Territory(ArrayList<Coordinates> hexs)  throws Exception

    {
        if(hexs.size() < 2) throw new Exception("At least 2 hexes must be selected");
        
        if(hexs.size() > MAX_HEX) throw new Exception("Maximum Hex selected " + MAX_HEX);
        hexs.toArray(hexCoords);
        createTerrHexs();
        drawTerritory();
        deleteSingleHexs();
        setId();
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
        
    }


    
    
    
    public void autoSetLinks()
    {
    
    }
    
    
    
    
    
    public int getId()
    {
        return id;
    }
    
    
    
    
    
    private void paint(){
    
    
    
    }
    //dessine le territoire sur le monde

    
    
    
    public Coordinates[] getComposingHex()
    {
        return hexCoords;
    }
    
    
    
    private void drawHexsLinks()
    {
        
        
        
    }
    
    
    
    public int[] getBorderTerritoryIDs()
    {
        return borderTerritoryIDs;
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
                TerritoryHex trHex = new TerritoryHex();
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
    //retourne des losanges qui couvrent certains bords noirs de l'Hexagone
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
    
    
    
    
    
    private void setId()
    {
            
        id = nextId;
        nextId++;
            
    }
    
    
}
