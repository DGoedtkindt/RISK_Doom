package mainObjects;

import appearance.Appearance;
import links.LinkIndic;
import Selection.Selectable;
import Selection.Selector;
import base.*;
import greenfoot.GreenfootImage;
import java.awt.Polygon;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Territory implements Selectable
{
    private static ArrayList<Territory> territoryList = new ArrayList<Territory>();
    private ArrayList<BlankHex> blankHexList;
    private ArrayList<TerritoryHex> terrHexList = new ArrayList<>();
    private GreenfootImage getBackground() {return MyWorld.theWorld.getBackground();}
    private MyWorld world() {return MyWorld.theWorld;}
    private Continent continent = null;
    public GColor continentColor = MyWorld.usedTheme.territoryColor;
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
    /*  repaints over itself in WORLD_COLOR
     *  add the BlankHex back to the world
     *  removes the TerritoryHexes
     *  removes itself from the territory and selectable Collections
     *      and from its continent
     *  removes the terrInfo
     *  destroys the LinkIndics */
    {   
        continentColor = MyWorld.usedTheme.backgroundColor;
        makeTransparent();
        for(BlankHex bh : blankHexList){
            world().addObject(bh, bh.rectCoord()[0], bh.rectCoord()[1]);
            
        }
        world().removeObjects(terrHexList);
        territoryList.remove(this);
        Selector.selectableSet.remove(this);
        if(continent != null) continent.removeTerritory(this);
        
        world().removeObject(trInfo);
        
        while(!links.isEmpty()) {
            links.get(0).destroy();
        
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
            continentColor = MyWorld.usedTheme.territoryColor;
            
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
        String bonusString = JOptionPane.showInputDialog("Enter the new bonus for this Territory");
        int newBonus = 0;
        if(!bonusString.isEmpty()){newBonus = Integer.parseInt(bonusString);}
        if(newBonus <= 0){newBonus = 0;}
        bonusPoints = newBonus;
        trInfo.setDisplayedBonus(bonusPoints);

    }
    
    public int bonus() {
        return bonusPoints;
        
    }
    
    public TerritoryHex infoHex() {
        return trInfo.linkedTerrHex();
    
    }
    
    //Selectable methods/////////////////////////////////
    
    @Override
    public void makeTransparent() {  
        GreenfootImage img = Hexagon.createImage(MyWorld.usedTheme.backgroundColor);
        for(TerritoryHex hex : terrHexList){
                
                int xPos = hex.getX() - Hexagon.RADIUS;
                int yPos = hex.getY() - Hexagon.RADIUS;
                getBackground().drawImage(img, xPos, yPos);
                
        }
        
        trInfo.makeTransparent();
        
        for(LinkIndic li : links){li.makeTransparent();}
        
    }
    
    @Override
    public void makeGreen() {
        GreenfootImage img = Hexagon.createImage(MyWorld.usedTheme.selectionColor);
        for(TerritoryHex hex : terrHexList){
                
                int xPos = hex.getX() - Hexagon.RADIUS;
                int yPos = hex.getY() - Hexagon.RADIUS;
                getBackground().drawImage(img, xPos, yPos);
                
        }
        
    }

    @Override
    public void makeOpaque() {   
        drawTerritory();
        trInfo.makeOpaque();
        for(LinkIndic li : links){li.makeOpaque();}
    }

    ///////////////////////////////////////////////////
    
    public void drawTerritory()
    //dessine le territoire sur le monde
    {
        drawHexs();
        drawAllHexsLinks();
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
        ArrayList<Polygon> links = getLinkingDiamonds(hex);
        getBackground().setColor(continentColor);
        for(Polygon diamond : links){
                getBackground().fillPolygon(diamond.xpoints,diamond.ypoints,diamond.npoints);

        }
        
    }
    
    private ArrayList<Polygon> getLinkingDiamonds(TerritoryHex thisHex)
    //retourne des losanges qui couvrent certains bords noirs de l'Hexagone
    //dirty code incoming!
    {
        ArrayList<Polygon> LinkingDiamonds = new ArrayList<>();
        
        int N_DIMENSION = 2;
        int N_POINTS = 4;
        int[][] diamPoints = new int[N_DIMENSION][N_POINTS]; 
        //ce tableau
        //stoque temporairement les coordonée d'un logange
        //le temps d'être transformé en Polygon
        
        //point 1: le centre du premier TerritoryHex
        diamPoints[0][0] = thisHex.getX();
        diamPoints[1][0] = thisHex.getY();
        
        //Va check pour tous les TerritoryHex adjacent s'il faut faire un lien
        //si oui, rajoute les 3 points restants et stoque le losange dans la liste
        for(TerritoryHex otherHex : terrHexList){
                if(otherHex != thisHex){ // ne pas faire de lien avec soi-même
                    if(thisHex.distance(otherHex) < 2.2*Hexagon.RADIUS) { //pour ne lier que les hex adjacents
                        
                        //point 3: le centre du deuxième TerritoryHex
                        diamPoints[0][2] = otherHex.getX();
                        diamPoints[1][2] = otherHex.getY();
                        
                        //l'angle entre les deux TerrHex par rapport à l'horrizontale
                        int X_DISTANCE = otherHex.getY()-thisHex.getY();
                        int Y_DISTANCE = otherHex.getX()-thisHex.getX();
                        double angle = Math.atan2(X_DISTANCE, Y_DISTANCE);
                        
                        //point 2: à mis-chemin entre les deux TerrHex et dévié de +30° par rapport à l'angle
                        diamPoints[0][1] = diamPoints[0][0] + (int)(Hexagon.RADIUS * Math.cos(angle + Math.PI/6));
                        diamPoints[1][1] = diamPoints[1][0] + (int)(Hexagon.RADIUS * Math.sin(angle + Math.PI/6));
                        
                        //point 4: à mis-chemin entre les deux TerrHex et dévié de -30° par rapport à l'angle
                        diamPoints[0][3] = diamPoints[0][0] + (int)(Hexagon.RADIUS * Math.cos(angle - Math.PI/6));
                        diamPoints[1][3] = diamPoints[1][0] + (int)(Hexagon.RADIUS * Math.sin(angle - Math.PI/6));
                        
                        Polygon newDiamond = new Polygon(diamPoints[0],diamPoints[1],4);
                        LinkingDiamonds.add(newDiamond);
                        
                    }
                }
        }
        
        return LinkingDiamonds;
        
    }
    
    private void createTerrHexs(BlankHex infoHex)
    //crée tous les territoryHex de ce territoire
    {
        for(BlankHex bh : blankHexList) {
            TerritoryHex trHex = new TerritoryHex(this, continentColor, bh.hexCoord()[0], bh.hexCoord()[1]);
            terrHexList.add(trHex);
            world().addObject(trHex, bh.rectCoord()[0], bh.rectCoord()[1]);
            if(bh == infoHex) {
                trInfo = new TerrInfo(trHex);
                world().addObject(trInfo,bh.rectCoord()[0], bh.rectCoord()[1]);
                
            }
        }
        
    }
            
    private void removeBlankHexs(){
        for(BlankHex hexToDel : blankHexList){
            world().removeObject(hexToDel);

        }
    }
    
}
