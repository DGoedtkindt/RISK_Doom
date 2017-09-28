import greenfoot.GreenfootImage;
import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Territory implements Selectable
{
    private static ArrayList<Territory> territoryList = new ArrayList<Territory>();
    private ArrayList<BlankHex> blankHexList;
    private ArrayList<TerritoryHex> terrHexList = new ArrayList<>();
    private GreenfootImage getBackground() {return MyWorld.theWorld.getBackground();}
    private MyWorld getWorld() {return MyWorld.theWorld;}
    private Continent continent = null;
    public Color continentColor = MyWorld.BASE_WORLD_COLOR;
    private int bonusPoints = 0;
    private TerrInfo trInfo;
    public ArrayList<LinkIndic> links = new ArrayList<>();
    
    
    
    //Public methods///////////////////////////////////////////////////////////////////////////////////////
    
    
    
    public Territory(ArrayList<BlankHex> hexs, BlankHex infoHex, int bonus, int id)  throws Exception {
        if(hexs.size() < 2) throw new Exception("At least 2 hexes must be selected");
        blankHexList = hexs;
        bonusPoints = bonus;
        if(id != -1) {territoryList.add(id, this);}
        else {territoryList.add(this);}
        createTerrHexs(infoHex);
        trInfo.setDisplayedBonus(bonus);
        drawTerritory();
        removeBlankHexs();
        Selector.selectableSet.add(this);
        
    }
    
    public Territory(ArrayList<BlankHex> hexs, BlankHex infoHex)  throws Exception {
        
        new Territory(hexs, infoHex, 0, -1);
        
        
        
        
    }
    
    public void destroy()
    /* reconstructs the BlankHexes
     * destroys the TerritoryHexes
     * removes itself from the territoryList
     * removes itself from the continent
     * removes itself from the selectableList
     * breaks the links with other territories
     * removes TerrInfo from world     
     */
    {
        continentColor = MyWorld.BASE_WORLD_COLOR;
        makeTransparent();
        for(BlankHex sh : blankHexList){
            getWorld().addObject(sh, sh.coordinates().rectCoord()[0], sh.coordinates().rectCoord()[1]);
            
        }
        getWorld().removeObjects(terrHexList);
        territoryList.remove(this);
        Selector.selectableSet.remove(this);
        if(continent != null) continent.removeTerritory(this);
        
        getWorld().removeObject(trInfo);
        
        for(LinkIndic link: links) {
            link.destroy();
        }
        
        
    }
    
    public static ArrayList<Territory> allTerritories() {
        return (ArrayList<Territory>)territoryList.clone();
    
    }

    public void setContinent(Continent newContinent) {
        continent = newContinent;
        if(newContinent != null){
            continentColor = newContinent.color();
            
        }else {
            continentColor = MyWorld.BASE_WORLD_COLOR;
            
        }
        drawTerritory();
        for(TerritoryHex hex : terrHexList){
                hex.drawTerrHex(continentColor);
                
        }
    }
    
    public int id() {
        return territoryList.indexOf(this);
        
    }
    
    public Continent continent() {
        return continent;
        
    }
    
    public ArrayList<TerritoryHex> composingHex() {
        return terrHexList;
        
    }
    
    public void editBonus() {
        int newBonus = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nouveaux bonus pour le territoire"));
        bonusPoints = newBonus;
        trInfo.setDisplayedBonus(newBonus);

    }
    
    public int bonus() {
        return bonusPoints;
        
    }
    
    public TerritoryHex infoHex() {
        return trInfo.linkedTerrHex();
    
    }
    
    //Selectable methods/////////////////////////////////
    
    public void makeTransparent() {  
        GreenfootImage img = Hexagon.createImage(MyWorld.BASE_WORLD_COLOR);
        for(TerritoryHex hex : terrHexList){
                
                int xPos = hex.getX() - Hexagon.RADIUS;
                int yPos = hex.getY() - Hexagon.RADIUS;
                getBackground().drawImage(img, xPos, yPos);
                
        }
        
    }
    
    public void makeGreen() {
        GreenfootImage img = Hexagon.createImage(MyWorld.SELECTION_COLOR);
        for(TerritoryHex hex : terrHexList){
                
                int xPos = hex.getX() - Hexagon.RADIUS;
                int yPos = hex.getY() - Hexagon.RADIUS;
                getBackground().drawImage(img, xPos, yPos);
                
        }
        
    }

    public void makeOpaque() {   
        drawTerritory();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void drawTerritory()
    //dessine le territoire sur le monde
    {
        drawHexs();
        drawAllHexsLinks();
    }
    
    private void createTerrHexs(BlankHex infoHex)
    //crée tous les territoryHex de ce territoire
    {
        for(BlankHex hex : blankHexList) {
            int[] rectCoord = hex.coordinates().rectCoord();
            TerritoryHex trHex = new TerritoryHex(this, continentColor, hex.coordinates().hexCoord[0], hex.coordinates().hexCoord[1]);
            terrHexList.add(trHex);
            getWorld().addObject(trHex, rectCoord[0], rectCoord[1]);
            if(hex == infoHex) {
                trInfo = new TerrInfo(trHex);
                getWorld().addObject(trInfo,rectCoord[0], rectCoord[1]);
            }
        }
        
    }
    
    private void drawHexs(){
        GreenfootImage img = Hexagon.createImageWBorder(continentColor);
        for(TerritoryHex hex : terrHexList){
            int xPos = hex.getX() - Hexagon.RADIUS;
            int yPos = hex.getY() - Hexagon.RADIUS;
            getBackground().drawImage(img, xPos, yPos);

        }
    }

    private void drawAllHexsLinks(){
        for(TerritoryHex hex : terrHexList){
                drawHexLinks(hex);

        }
    }
    
    private void drawHexLinks(TerritoryHex hex){
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
                    if(thisHex.distance(otherHex) < 2.2*Hexagon.RADIUS) { //pour ne lier que les hex adjacents
                        
                        temporary[0][2] = otherHex.getX();
                        temporary[1][2] = otherHex.getY();
                        
                        double angle = Math.atan2(otherHex.getY()-thisHex.getY(), otherHex.getX()-thisHex.getX());
                        
                        temporary[0][1] = temporary[0][0] + (int)(Hexagon.RADIUS * Math.cos(angle + Math.PI/6));
                        temporary[1][1] = temporary[1][0] + (int)(Hexagon.RADIUS * Math.sin(angle + Math.PI/6));
                        temporary[0][3] = temporary[0][0] + (int)(Hexagon.RADIUS * Math.cos(angle - Math.PI/6));
                        temporary[1][3] = temporary[1][0] + (int)(Hexagon.RADIUS * Math.sin(angle - Math.PI/6));
                        
                        linksPoly.add(new Polygon(temporary[0],temporary[1],4));
                        
                    }
                }
        }
        
        return linksPoly;
        
    }
            
    private void removeBlankHexs(){
        for(BlankHex hexToDel : blankHexList){
                getWorld().removeObject(hexToDel);

        }
    }
    
}
