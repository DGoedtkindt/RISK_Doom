import greenfoot.GreenfootImage;
import java.awt.Color;
import java.awt.Polygon;
import java.util.HashSet;
import java.util.ArrayList;

public class Territory
{
    public static ArrayList<Territory> territoryList = new ArrayList<Territory>();
    private static int nextId = 0; //stoque le prochain ID à attribuer à un territoire
    private int id; //l'identifiant de ce territoire
    private HashSet<Coordinates> hexCoordSet;
    private ArrayList<TerritoryHex> terrHexList = new ArrayList<>();
    private HashSet<Territory> borderingTerritorySet = new HashSet<>();
    private GreenfootImage getBackground() {return MyWorld.theWorld.getBackground();}
    private MyWorld getWorld() {return MyWorld.theWorld;}
    private Continent continent = null;
    public Color continentColor = MyWorld.BASE_WORLD_COLOR;
    private int bonusPoints = 0;
    
    
    
    //Public methods///////////////////////////////////////////////////////////////////////////////////////
    
    static public ArrayList<Territory> getAllTerritories(){
        
        return territoryList;
        
    }
    
    public Territory(HashSet<Coordinates> hexs)  throws Exception {
        
        if(hexs.size() < 2) throw new Exception("At least 2 hexes must be selected");
        hexCoordSet = hexs;
        createTerrHexs();
        drawTerritory();
        deleteSingleHexs();
        setId();
        autoSetLinks();
    }
    
    public void destroy()
    /* destroys the TerritoryHexes
     * removes itself from the territoryList
     * removes itself from the continent
     * breaks the links with other territories
     */
    {
        
        continentColor = MyWorld.BASE_WORLD_COLOR;
        makeTransparent();
        
        for(Coordinates c : hexCoordSet){
            
            getWorld().addObject(new SingleHex(c.getHexCoord()[0], c.getHexCoord()[1]), c.getRectCoord()[0], c.getRectCoord()[1]);
            
        }
        
        getWorld().removeObjects(terrHexList);
        
        territoryList.set(id, null);
        
        if(continent != null) continent.removeTerritory(this);
        
        for(Territory otherTerr : territoryList){
            if(otherTerr != null) {
                
                otherTerr.removeLink(this);
                
            }
        }
        
    }

    public void setContinent(Continent newContinent) {
        
        continent = newContinent;
        
        if(newContinent != null){
            
            continentColor = newContinent.getContinentColor();
            
        }else{
            
            continentColor = MyWorld.BASE_WORLD_COLOR;
            
        }
        
        drawTerritory();
        
        for(TerritoryHex hex : terrHexList){
                
                hex.drawTerrHex(continentColor);
                
        }
        
    }
    
    public void setNewLink(Territory newLink) {
        
        borderingTerritorySet.add(newLink);
    }

    public void autoSetLinks(){
        
         HashSet<TerritoryHex> borderingHexSet;
         
         borderingHexSet = getBorderingHex();
         
         for(TerritoryHex hex : borderingHexSet){
             
             borderingTerritorySet.add(hex.getTerritory());
            
         }
         
         borderingTerritorySet.remove(this);
         
         for(Territory t : borderingTerritorySet){
             
             t.setNewLink(this);
             
         }
         
    }
    
    public void removeLink(Territory terrToRemove){
        
        borderingTerritorySet.remove(terrToRemove);
    }
    
    public int getId() {
        
        return id;
    }
    
    public Continent getContinent() {
        
        return continent;
    }
    
    public int getBonusPoints() {
        
        return bonusPoints;
    }
    
    public void setBonusPoints(int bonus) {
        
        bonusPoints = bonus;
    }
    
    public ArrayList<Territory> getBorderTerritories() {
        
        ArrayList<Territory> borderTerritoryList = new ArrayList<>();
        borderTerritoryList.addAll(0, borderingTerritorySet);
        
        return borderTerritoryList;
    }
    
    public ArrayList<TerritoryHex> getComposingHex() {
        
        return terrHexList;
    }
    
    public void makeTransparent() {  
        
        GreenfootImage img = Hexagon.createSimpleHexImage(MyWorld.BASE_WORLD_COLOR);
        
        for(TerritoryHex hex : terrHexList){
                
                int xPos = hex.getX() - Hexagon.getSize();
                int yPos = hex.getY() - Hexagon.getSize();
                getBackground().drawImage(img, xPos, yPos);
                
        }
        
    }
    
    public void makeGreen() {
        
        GreenfootImage img = Hexagon.createSimpleHexImage(MyWorld.SELECTION_COLOR);
        
        for(TerritoryHex hex : terrHexList){
                
                int xPos = hex.getX() - Hexagon.getSize();
                int yPos = hex.getY() - Hexagon.getSize();
                getBackground().drawImage(img, xPos, yPos);
                
        }
        
    }
    
    public void makeOpaque() {
        
        drawTerritory();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void drawTerritory()
    //dessine le territoire sur le monde
    {
        drawHexs();
        drawAllHexsLinks();
    }
    
    private void createTerrHexs()
    //crée tous les territoryHex de ce territoire
    {
        GreenfootImage img = Hexagon.createSimpleHexImage(continentColor, 0.95);
        
        for(Coordinates hex : hexCoordSet){
                
                int[] rectCoord = hex.getRectCoord();
                TerritoryHex trHex = new TerritoryHex(this, continentColor);
                terrHexList.add(trHex);
                getWorld().addObject(trHex, rectCoord[0], rectCoord[1]);
                
        }
        
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
    //retourne des losanges qui couvrent certains bords noirs de l'Hexagone
    //dirty code incoming!
    {
        ArrayList<Polygon> linksPoly = new ArrayList<>();
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
        HashSet<TerritoryHex> borderingHexSet = new HashSet<>();
        ArrayList<TerritoryHex> temporary;// cette liste
        // contiendra les TerritoryHex le temps de les rajouter dans
        // borderingHexSet
        
        for(TerritoryHex hex : terrHexList){
                
            temporary = hex.getBorderingHex();
            
            for(TerritoryHex th : temporary){
                
                borderingHexSet.add(th);
                
            }
            
        }
        
        for(TerritoryHex th : terrHexList){       
            borderingHexSet.remove(th);
        }
        
        return borderingHexSet;
    }
}
