package mainObjects;

import game.Zombie;
import game.Player;
import appearance.MessageDisplayer;
import selector.Selectable;
import appearance.Theme;
import base.GColor;
import base.Hexagon;
import base.Map;
import base.MyWorld;
import game.Turn;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.swing.JOptionPane;

public class Territory implements Selectable
{
    
    public static Territory actionSource = null;
    
    private List<TerritoryHex> terrHexList = new ArrayList<>();
    private GreenfootImage getBackground() {return MyWorld.theWorld.getBackground();}
    private MyWorld world() {return MyWorld.theWorld;}
    private Map map() {return world().stateManager.map();}
    private Continent continent = null;
    public GColor continentColor = Theme.used.territoryColor;
    private int bonusPoints = 0;
    private BlankHex infoHex;
    private TerrInfo trInfo;
    private List<BlankHex> blankHexList;
    private int armies = 1;
    private Player owner = null;
    
    public ArrayList<LinkIndic> links = new ArrayList<>();
    
    //Public methods///////////////////////////////////////////////////////////////////////////////////////
    
    public Territory(ArrayList<BlankHex> hexs, BlankHex infoHex, int bonus)  throws Exception {
        if(hexs.size() < 2) throw new Exception("At least 2 hexes must be selected.");
        blankHexList = hexs;
        bonusPoints = bonus;
        this.infoHex = infoHex;
        
    }
    
    public Territory(ArrayList<BlankHex> hexs, BlankHex infoHex)  throws Exception {
        new Territory(hexs, infoHex, 0);

    }
    
    public void addToWorld() {
        //pas fool proof, devrais éviter de rajouter 2 fois au monde
            placeTerrHexs();
            trInfo.setDisplayedBonus(bonusPoints);
            removeBlankHexs();
            drawTerritory();
            map().territories.add(this);
        
    }
  
    /** Removes this from the world, containing continent, Links, etc...
     * Should not be used if territory is outside of the world
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
    
    public int id() {
        return map().territories.indexOf(this);
        
    }
    
    public Continent continent() {
        return continent;
        
    }
    
    public List<TerritoryHex> composingHex() {
        return terrHexList;
        
    }
    
    public void editBonus() {
        String bonusString = JOptionPane.showInputDialog("Enter the new bonus for this Territory");
        
        if(bonusString.matches("\\d+")){
            int newBonus = Integer.parseInt(bonusString);
            bonusPoints = newBonus;
            
        }else{
            MessageDisplayer.showMessage("Invalid entry.");
        }
        
    }
    
    public int bonus() {
        return bonusPoints;
        
    }
    
    public TerritoryHex infoHex() {
        return trInfo.linkedTerrHex();
    
    }
    
    public int armies(){
        return armies;
    }
    
    public void addArmies(int howMany) {
        armies += howMany;
        drawPlayerColor();
        
        
    }
    
    public void setArmies(int newNumber) {
        armies = newNumber;
        drawPlayerColor();
    }
    
    public Player owner(){
        return owner;
    }
    
    public void setOwner(Player newOwner){
        setOwnerWithoutDrawing(newOwner);
        drawTerritory();
        if(newOwner == null) setArmies(1);
        
    }
    
    public TerrInfo terrInfo(){
        return trInfo;
    }
    
    /**
     * changes the owner of this Territory without drawing this. 
     * @param newOwner
     */
    public void setOwnerWithoutDrawing(Player newOwner) {
        owner = newOwner;
    }
    
    public void invade(Territory target) throws Exception{
        
        String invadingArmiesString = JOptionPane.showInputDialog("The number of armies you're using");
        
        if(invadingArmiesString.matches("\\d+")){
            
            int invadingArmies = Integer.parseInt(invadingArmiesString);
            
            if(invadingArmies < 2){
                throw new Exception("You can't attack a territory without at least two armies.");
            }else if(invadingArmies > armies + owner().battlecryBonus + 1){
                throw new Exception("You don't have enough armies.");
            }else{
                armies -= invadingArmies;
                target.attacked(invadingArmies, owner);
            }

            drawTerritory();
            
        }else{
            throw new Exception("Invalid entry.");
        }
        
    }
    
    public void attacked(int invadingArmies, Player invader){
        
        setArmies(armies() - invadingArmies);
        
        if(armies() == 0){
            owner = null;
        }else if(armies() < 0){
            setArmies(-armies);
            Player formerOwner = owner();
            owner = invader;
            invader.conqueredThisTurn = true;
            
            if(formerOwner instanceof Zombie){
                owner.points++;
            }
        }
        
        if(Turn.aPlayerIsDead() != null){
            ((game.Manager)(world().stateManager)).endByDeath(Turn.aPlayerIsDead());
        }else if(Turn.aPlayerWon() != null){
            ((game.Manager)(world().stateManager)).endByVictory(Turn.aPlayerWon());
        }
        
        drawTerritory();
        
    }
    
    public void moveTo(Territory destination) throws Exception{
        
        String movingArmiesString = JOptionPane.showInputDialog("The number of armies you're moving");
        
        if(movingArmiesString.matches("\\d+")){
            
            int movingArmies = Integer.parseInt(movingArmiesString);
            
            if(movingArmies > armies - 1){
                throw new Exception("You don't have enough armies.");
            }else{
                setArmies(armies() - movingArmies);
                destination.setArmies(armies() + movingArmies);
            }
            
            drawTerritory();
            
        }else{
            throw new Exception("Invalid entry.");
        }
        
    }
    
    public boolean canAttack(Territory target){
        
        return neighbours().contains(target)  && !target.owner.fortressProtection;
        
    }
    
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
    
    public void attackRandomly(){
        
        if(armies() > 2 && !neighbours().isEmpty()){
            
            Territory target = (Territory)(neighbours().toArray()[Greenfoot.getRandomNumber(neighbours().size())]);
            
            int attackingArmies = Greenfoot.getRandomNumber(armies() - 1) + 2;
            
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
        
        trInfo.makeTransparent();
        
        for(LinkIndic li : links){li.makeTransparent();}
        
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
        trInfo.makeOpaque();
        for(LinkIndic li : links){li.makeOpaque();}
    }

    ///////////////////////////////////////////////////
    
    public void drawTerritory()
    //dessine le territoire sur le monde
    {
        drawHexs();
        drawAllHexsLinks();
        drawPlayerColor();
        trInfo.updateImage();
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
    
    private void drawPlayerColor(){
        for(TerritoryHex hex : terrHexList){
                hex.drawPlayerColor(owner);

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
    
    private void placeTerrHexs()
    //crée et place tous les territoryHex de ce territoire
    {
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
            
    private void removeBlankHexs(){
        for(BlankHex hexToDel : blankHexList){
            world().removeObject(hexToDel);

        }
    }
    
}
