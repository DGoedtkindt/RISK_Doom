package mainObjects;

import appearance.Theme;
import base.GColor;
import base.Hexagon;
import base.Map;
import base.MyWorld;
import game.Zombie;
import game.Player;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import input.Form;
import input.FormAction;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import selector.Selectable;

/**
 * The Class that represents the Territories, the main Objects of this Game.
 * 
 */
public class Territory implements Selectable {
    
    public static Territory actionSource = null;
    public static Territory actionTarget = null;
    
    private List<TerritoryHex> terrHexList = new ArrayList<>();
    private GreenfootImage getBackground() {return MyWorld.theWorld.getBackground();}
    private MyWorld world() {return MyWorld.theWorld;}
    private Map map() {return world().stateManager.map();}
    private Continent continent = null;
    private GColor continentColor = Theme.used.territoryColor;
    private int bonusPoints = 0;
    private BlankHex infoHex;
    private TerrInfo trInfo;
    private List<BlankHex> blankHexList;
    private int armies = 1;
    private Player owner = null;
    
    public ArrayList<LinkIndic> links = new ArrayList<>();
    
    //Public methods///////////////////////////////////////////////////////////////////////////////////////

    /**
     * Creates a Territory.
     * @param hexs The BlankHexes on which this Territory will appear.
     * @param infoHex The BlankHex on which the TerrInfo of this Territory will be placed.
     * @param bonus The bonus of this Territory.
     * @throws Exception If the number of BlankHexes of this Territory is lesser than two.
     */
    public Territory(ArrayList<BlankHex> hexs, BlankHex infoHex, int bonus)  throws Exception {
        if(hexs.size() < 2) throw new Exception("At least 2 BlankHexes must be selected.");
        blankHexList = hexs;
        bonusPoints = bonus;
        this.infoHex = infoHex;
        
    }
    
    /**
     * Creates a Territory with a bonus of zero.
     * @param hexs The BlankHexes on which this Territory will appear.
     * @param infoHex The BlankHex on which the TerrInfo of this Territory will be placed.
     * @throws Exception If the number of BlankHexes of this Territory is lesser than two.
     */
    public Territory(ArrayList<BlankHex> hexs, BlankHex infoHex)  throws Exception {
        new Territory(hexs, infoHex, 0);

    }
    
    public static void resetSourceAndTarget(){
        actionSource = null;
        actionTarget = null;
    }
    
    /**
     * Adds this Territory to the World.
     */
    public void addToWorld() {
        placeTerrHexs();
        trInfo.setDisplayedBonus(bonusPoints);
        removeBlankHexs();
        drawTerritory();
        map().territories.add(this);
        
    }
  
    /** 
     * Removes this Territory from the World, and everyinformation about it in the World.
     */
    public void destroy(){   
        continentColor = Theme.used.backgroundColor;
        makeTransparent();
        for(BlankHex bh : blankHexList){
            world().addObject(bh, bh.rectCoord()[0], bh.rectCoord()[1]);
            
        }
        world().removeObjects(terrHexList);
        map().territories.remove(this);
        if(continent != null) continent.removeTerritory(this);
        
        world().removeObject(trInfo);
        
        while(!links.isEmpty()) {
            links.get(0).destroy();
        
        }
        
        
    }
    
    /**
     * Gives a new Continent to this Territory.
     * @param newContinent The new Continent of this Territory.
     */
    public void setContinent(Continent newContinent) {
        continent = newContinent;
        if(newContinent != null){
            continentColor = newContinent.color();
            drawTerritory();
            
        }else {
            continentColor = Theme.used.territoryColor;
            
        }
        for(TerritoryHex hex : terrHexList){
            hex.drawTerrHex(continentColor);
                
        }
    }
    
    /**
     * Gets the id of this Territory, which is its position in the list.
     * @return The id of this Territory.
     */
    public int id() {
        return map().territories.indexOf(this);
        
    }
    
    /**
     * Gets the Continent of this Territory.
     * @return This Territory's Continent.
     */
    public Continent continent() {
        return continent;
        
    }
    
    /**
     * Gets the list of TerritoryHexes that this Territory contains.
     * @return The TerritoryHexes contained by this Territory.
     */
    public List<TerritoryHex> composingHex() {
        return terrHexList;
        
    }
    
    /**
     * Lets the User edit this Territory's bonus.
     */
    public void editBonus() {
        Form.inputText("Enter a new bonus for this Territory.", changeBonus);
        
    }
    
    /**
     * Gets the bonus given by this Territory.
     * @return The bonus given by this Territory.
     */
    public int bonus() {
        return bonusPoints;
        
    }
    
    /**
     * Gets the TerritoryHex on which this Territory's TerrInfo is placed.
     * @return The TerritoryHex on which this Territory's TerrInfo is placed.
     */
    public TerritoryHex infoHex() {
        return trInfo.linkedTerrHex();
    
    }
    
    /**
     * Gets the number of armies on this Territory.
     * @return The number of armies on this Territory.
     */
    public int armies(){
        return armies;
    }
    
    /**
     * Adds armies to this Territory.
     * @param howMany The number of added armies.
     */
    public void addArmies(int howMany) {
        armies += howMany;
        drawPlayerColor();
        
        
    }
    
    /**
     * Sets the number of armies of this Territory to a given number.
     * @param newNumber The number of armies on this Territory.
     */
    public void setArmies(int newNumber) {
        armies = newNumber;
        drawPlayerColor();
    }
    
    /**
     * Gets the owner of this Territory.
     * @return The Player owning this Territory.
     */
    public Player owner(){
        return owner;
    }
    
    /**
     * Sets this Territory's owner.
     * @param newOwner The new Player who owns this Territory.
     */
    public void setOwner(Player newOwner){
        setOwnerWithoutDrawing(newOwner);
        drawTerritory();
        if(newOwner == null) setArmies(1);
        
    }
    
    /**
     * Gets the TerrInfo of this Territory.
     * @return This territory's TerrInfo.
     */
    public TerrInfo terrInfo(){
        return trInfo;
    }
    
    /**
     * Changes the owner of this Territory without drawing it. 
     * @param newOwner This territory's new owner.
     */
    public void setOwnerWithoutDrawing(Player newOwner) {
        owner = newOwner;
    }
    
    /**
     * Invades a Territory.
     * @param target The invaded Territory.
     * @throws Exception If the User enters an invalid number of armies.
     */
    public void invade(Territory target) throws Exception{
        Form.inputText("The number of armies you're using.", invade);
        
    }
    
    /**
     * To receive an attack.
     * @param invadingArmies The number of armies that invade this Territory.
     * @param invader The Player who invades this Territory.
     */
    public void attacked(int invadingArmies, Player invader){
        
        if(owner != null){invadingArmies -= owner().battlecryBonus;}
        if(invadingArmies < 0){invadingArmies = 0;}
        
        setArmies(armies() - invadingArmies);
        
        if(armies() == 0){
            owner = null;
            armies = 1;
        }else if(armies() < 0){
            setArmies(-armies);
            Player formerOwner = owner;
            owner = invader;
            invader.conqueredThisTurn = true;
            
            if(formerOwner instanceof Zombie){
                owner.points++;
            }
        }
        
        drawTerritory();
        
    }
    
    /**
     * Moves armies from this Territory to another.
     * @param destination The destination of the moving armies.
     * @throws Exception If the User enters an invalid number of armies.
     */
    public void moveTo(Territory destination) throws Exception{
        Form.inputText("The number of armies you're moving.", moveArmies);
        
    }
    
    /**
     * Checks if this Territory can attack another.
     * @param target The tested Territory.
     * @return a boolean representation of the fact that this Territory can or can't attack its target.
     */
    public boolean canAttack(Territory target){
        
        boolean fortress = false;
        
        if(target.owner != null){
            fortress = target.owner().fortressProtection;
        }
        
        return neighbours().contains(target)  && !fortress;
        
    }
    
    /**
     * Obtains a list of the neighbouring Territories of this one.
     * @return The neighbours of this Territory.
     */
    public HashSet<Territory> neighbours(){
        
        HashSet<Territory> neighboursList = new HashSet<>();
        
        for(TerritoryHex hex : terrHexList){
            
            for(TerritoryHex neighbour : hex.getBorderingHex()){
                
                neighboursList.add(neighbour.territory());
                
            }
            
        }
        
        for(LinkIndic linkindic : links){
            neighboursList.addAll(linkindic.links.linkedTerrs);
            
        }
        
        neighboursList.remove(this);
        
        return neighboursList;
        
    }
    
    /**
     * Attacks a random Territory with a random number of armies.
     */
    public void attackRandomly(){
        
        if(armies() > 2 && !neighbours().isEmpty()){
            
            Territory target = (Territory)(neighbours().toArray()[Greenfoot.getRandomNumber(neighbours().size())]);
            
            int attackingArmies = Greenfoot.getRandomNumber(armies() - 2) + 2;
            
            setArmies(armies() - attackingArmies);
            
            target.attacked(attackingArmies, owner);
            
        }
        
    }
    
    //Selectable methods/////////////////////////////////
    
    @Override
    public void makeTransparent() {  
        GreenfootImage img = Hexagon.createImage(Theme.used.backgroundColor);
        for(TerritoryHex hex : terrHexList){
                
                int xPos = hex.getX() - Hexagon.RADIUS;
                int yPos = hex.getY() - Hexagon.RADIUS;
                getBackground().drawImage(img, xPos, yPos);
                
        }
        
        trInfo.toggleUnusable();
        links.forEach(LinkIndic::toggleUnusable);
        
    }
    
    @Override
    public void makeGreen() {
        GreenfootImage img = Hexagon.createImage(Theme.used.selectionColor);
        for(TerritoryHex hex : terrHexList){
                
                int xPos = hex.getX() - Hexagon.RADIUS;
                int yPos = hex.getY() - Hexagon.RADIUS;
                getBackground().drawImage(img, xPos, yPos);
                
        }
        
    }

    @Override
    public void makeOpaque() {   
        drawTerritory();
        trInfo.toggleUsable();
        links.forEach(LinkIndic::toggleUsable);
    }

    ///////////////////////////////////////////////////

    /**
     * Draws this Territory on the World.
     */
    public void drawTerritory(){
        drawHexs();
        drawAllHexsLinks();
        drawPlayerColor();
        trInfo.updateImage();
    }
    
    /**
     * Draws the hexagonal parts of this Territory on the background.
     */
    private void drawHexs(){
        GreenfootImage img = Hexagon.createImageWBorder(continentColor);
        for(TerritoryHex hex : terrHexList){
            int xPos = hex.getX() - Hexagon.RADIUS;
            int yPos = hex.getY() - Hexagon.RADIUS;
            getBackground().drawImage(img, xPos, yPos);

        }
    }
    
    /**
     * Ends the drawing by erasing some useless lines in the image of the Territory.
     */
    private void drawAllHexsLinks(){
        for(TerritoryHex hex : terrHexList){
                drawHexLinks(hex);

        }
    }
    
    /**
     * Draws the owner's Color on the hexagons.
     */
    private void drawPlayerColor(){
        for(TerritoryHex hex : terrHexList){
                hex.drawPlayerColor(owner);

        }
    }
    
    /**
     * Draws links between TerritoryHexes to erase useless lines between them.
     */
    private void drawHexLinks(TerritoryHex hex){
        ArrayList<Polygon> links = getLinkingDiamonds(hex);
        getBackground().setColor(continentColor);
        for(Polygon diamond : links){
                getBackground().fillPolygon(diamond.xpoints,diamond.ypoints,diamond.npoints);

        }
        
    }
    
    /**
     * Returns a list of Polygons used to erase some useless lines in the 
     * Territory's image in the neighbourhood of a given TerritoryHex.
     * @param thisHex The TerritoryHex around which useless lines will be erased.
     */
    private ArrayList<Polygon> getLinkingDiamonds(TerritoryHex thisHex){
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
            if(otherHex != thisHex && thisHex.distance(otherHex) < 2.2*Hexagon.RADIUS){ //pour ne lier que les hex adjacents

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
        
        return LinkingDiamonds;
        
    }
    
    /**
     * Places this Territory's TerritoryHexes in the World.
     */
    private void placeTerrHexs(){
        this.terrHexList = new ArrayList<>();
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
    
    /**
     * Removes the BlankHexes on which the Territory is being created.
     */
    private void removeBlankHexs(){
        for(BlankHex hexToDel : blankHexList){
            world().removeObject(hexToDel);

        }
    }
    
    private void attack(int withHowMany) {
        Territory invader = Territory.actionSource;
        Territory target = Territory.actionTarget;
        resetSourceAndTarget();
        if(withHowMany < 2){
            appearance.MessageDisplayer.showMessage("You can't attack a territory without at least two armies.");
        }else if(withHowMany >= invader.armies + invader.owner().battlecryBonus){
            appearance.MessageDisplayer.showMessage("You don't have enough armies.");
        }else{
            invader.armies -= withHowMany;
            target.attacked(invader.owner.battlecryBonus, invader.owner);
            if(target.owner == invader.owner){
                resetSourceAndTarget();
                moveArmies(withHowMany);
            }else{
                target.attacked(withHowMany, invader.owner);
            }
        }

        ((Territory)this).drawTerritory();
    
    
    }
    
    private void moveArmies(int howMany) {
        Territory source = Territory.actionSource;
        Territory destination = Territory.actionTarget;
        resetSourceAndTarget();
        if(howMany > source.armies - 1){
            appearance.MessageDisplayer.showMessage("You don't have enough armies.");
        }else{
            source.setArmies(source.armies() - howMany);
            destination.setArmies(destination.armies() + howMany);
        }

        source.drawTerritory();
        destination.drawTerritory();
    
    
    }
    
    //Form actions////////////////////////////////////////////////////////////
    
    private FormAction invade = (java.util.Map<String,String> input) -> {
        if(input.get("inputedText").matches("\\d+")){
            int invadingArmies = Integer.parseInt(input.get("inputedText"));
            attack(invadingArmies);
        }else{
            appearance.MessageDisplayer.showMessage("Invalid Entry.");
        }   
            
    };
    
    private FormAction changeBonus = (java.util.Map<String,String> input) -> { 
        if(input.get("inputedText").matches("\\d+")){    
            bonusPoints = Integer.parseInt(input.get("inputedText"));
            ((Territory)this).drawTerritory();
        }else{
            appearance.MessageDisplayer.showMessage("Invalid Entry.");
        }
            
    };
            
    private FormAction moveArmies = (java.util.Map<String,String> input) -> { 
        if(input.get("inputedText").matches("\\d+")){       
            moveArmies(Integer.parseInt(input.get("inputedText")));
        }else{
            appearance.MessageDisplayer.showMessage("Invalid Entry.");
        }
            
    };
    
}
