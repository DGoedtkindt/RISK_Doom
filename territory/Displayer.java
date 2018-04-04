package territory;

import base.Action;
import base.Hexagon;
import base.MyWorld;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import java.awt.Polygon;
import java.util.ArrayList;
import base.BlankHex;
import java.util.HashSet;
import java.util.Set;

/**
 * A Displayer Listens to changes to a Territory and makes sure that the
 * representation of it matches the model.
 */
public abstract class Displayer {
    protected Territory territory;
    protected Set<TerrButton> terrHexs = new HashSet<>();
    protected TerrButton terrInfo;
    protected Action onArmiesChange;
    protected Action onBonusChange;
    protected Action onContinentChange;
    protected Action onLinksChange;
    protected Action onOwnerChange;
    protected Action onStatusChange;
    
    private Color drawingColor;
    private Set<Links> links = new HashSet<>(); //To know what linkIndic to remove or add
    private MyWorld world() {return MyWorld.theWorld;}
    private GreenfootImage background() {return world().getBackground();}
    
    public Displayer(Territory territory) {
        this.territory = territory;
        for(BlankHex bh : territory.blankHexSet) {
            TerrButton newHex = new TerrButton(null);
            newHex.setLocation(bh.rectCoord());
            terrHexs.add(newHex);
        
        }
        terrInfo = new TerrButton(null);
        terrInfo.setLocation(territory.infoHex.rectCoord());
    
    }
    
    public void show() {
        territory.blankHexSet.forEach(BlankHex::removeFromWorld);
        addTerrHexs();
        addListeners();
    
    }
    
    public void hide() {
        removeListeners();
        territory.blankHexSet.forEach(BlankHex::addToWorld);
        removeTerrButtons();
        paint(appearance.Theme.used.backgroundColor);
        
    }
    
    /**
     * Draws this Displayer's Territory onto the world with a specified Color.
     * @param color The specified Color.
     */
    protected void paint(Color color) {
        drawingColor = color;
        drawHexs();
        drawAllHexsLinks();
    
    }
    
    protected Color getTerritoryColor() {
        if(territory.continent() != null) return territory.continent().color();
        else return appearance.Theme.used.territoryColor;
        
    }
    
    /**
     *  Adds the TerrButtons to the world.
     */
    protected void addTerrHexs() {
        terrHexs.forEach(TerrButton::addToWorld);
        
    }
    
    /**
     *  Removes the TerrButtons from the world.
     */
    protected void removeTerrButtons() {
        terrHexs.forEach(TerrButton::removeFromWorld);
        terrInfo.removeFromWorld();
    
    
    }
    
    /**
     *  Adds the listeners to the Territory and act() them.
     */
    protected void addListeners() {
        //add Listener
        territory.armiesListener.add(onArmiesChange);
        territory.bonusListener.add(onBonusChange);
        territory.continentListener.add(onContinentChange);
        territory.linksListener.add(onLinksChange);
        territory.ownerListener.add(onOwnerChange);
        territory.statusListener.add(onStatusChange);
        
        //execute listener action to init
        if(onArmiesChange != null) onArmiesChange.act();
        if(onBonusChange != null) onBonusChange.act();
        if(onContinentChange != null) onContinentChange.act();
        if(onLinksChange != null) onLinksChange.act();
        if(onOwnerChange != null) onOwnerChange.act();
        if(onStatusChange != null) onStatusChange.act();
    
    }
    
    protected void removeListeners() {
        territory.armiesListener.remove(onArmiesChange);
        territory.bonusListener.remove(onBonusChange);
        territory.continentListener.remove(onContinentChange);
        territory.linksListener.remove(onLinksChange);
        territory.ownerListener.remove(onOwnerChange);
        territory.statusListener.remove(onStatusChange);
    
    }
    
    /**
     * Show and updates the LinkIndic of this Displayer's Territory's Links.
     */
    protected void showLinkIndics() {
        Set<Links> added = territory.links();
        added.removeAll(links);
        Set<Links> deleted = links;
        deleted.removeAll(territory.links());
        added.forEach((links)->{
            links.terrIndicMap().get(territory).addToWorld();
        
        });
        
    }
    
    protected void removeLinkIndics() {
        Set<Links> terrLinks = territory.links();
        terrLinks.forEach((links)->{
            links.terrIndicMap().get(territory).removeFromWorld();
        
        });
    
    }
    
    
    //Draws the hexagonal parts of this Territory on the background.
    private void drawHexs(){
        GreenfootImage img = Hexagon.createImageWBorder(drawingColor);
        for(BlankHex hex : territory.blankHexSet){
            int xPos = hex.rectCoord()[0] - Hexagon.RADIUS;
            int yPos = hex.rectCoord()[1] - Hexagon.RADIUS;
            background().drawImage(img, xPos, yPos);

        }
    }
    
    //Ends the drawing by erasing some useless lines in the image of the Territory.
    private void drawAllHexsLinks(){
        for(BlankHex hex : territory.blankHexSet){
                drawHexLinks(hex);

        }
    }
    
    //Draws links between TerritoryHexes to erase useless lines between them.
    private void drawHexLinks(BlankHex hex){
        ArrayList<Polygon> linkingDiamonds = getLinkingDiamonds(hex);
        background().setColor(drawingColor);
        for(Polygon diamond : linkingDiamonds){
                background().fillPolygon(diamond.xpoints,diamond.ypoints,diamond.npoints);

        }
        
    }
    
    /**
     * Returns a list of Polygons used to erase some useless lines in the 
     * Territory's image in the neighbourhood of a given TerritoryHex.
     * @param thisHex The TerritoryHex around which useless lines will be erased.
     */
    private ArrayList<Polygon> getLinkingDiamonds(BlankHex thisHex){
        ArrayList<Polygon> LinkingDiamonds = new ArrayList<>();
        
        int N_DIMENSION = 2;
        int N_POINTS = 4;
        int[][] diamPoints = new int[N_DIMENSION][N_POINTS]; 
        //ce tableau
        //stoque temporairement les coordonée d'un logange
        //le temps d'être transformé en Polygon
        
        //point 1: le centre du premier TerritoryHex
        diamPoints[0][0] = thisHex.rectCoord()[0];
        diamPoints[1][0] = thisHex.rectCoord()[1];
        
        //Va check pour tous les TerritoryHex adjacent s'il faut faire un lien
        //si oui, rajoute les 3 points restants et stoque le losange dans la liste
        for(BlankHex otherHex : territory.blankHexSet){
            if(thisHex.neighbours().contains(otherHex)){ //pour ne lier que les hex adjacents

                //point 3: le centre du deuxième TerritoryHex
                diamPoints[0][2] = otherHex.rectCoord()[0];
                diamPoints[1][2] = otherHex.rectCoord()[1];

                //l'angle entre les deux TerrHex par rapport à l'horrizontale
                int X_DISTANCE = otherHex.rectCoord()[1]-thisHex.rectCoord()[1];
                int Y_DISTANCE = otherHex.rectCoord()[0]-thisHex.rectCoord()[0];
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
        
        return LinkingDiamonds;
        
    }

}
