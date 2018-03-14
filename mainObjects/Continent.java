package mainObjects;

import appearance.Appearance;
import appearance.MessageDisplayer;
import appearance.Theme;
import base.GColor;
import input.InputPanelUser;
import base.Map;
import base.MyWorld;
import input.InputPanel;
import selector.Selectable;
import greenfoot.Actor;
import java.util.ArrayList;
import greenfoot.GreenfootImage;

/**
 * The Class that represents the Continents.
 * 
 */
public class Continent implements Selectable, InputPanelUser{
    
    public static final BonusDisplay display = new BonusDisplay();
    
    //private variable
    private MyWorld world() {return MyWorld.theWorld;}
    private Map map() {return world().stateManager.map();}
    private GColor continentColor = Theme.used.territoryColor;
    private ArrayList<Territory> territoriesContained = new ArrayList<>();
    private int bonus = 0;
    
    /**
     * Creates a Continent.
     * @param territories The Territories contained in this Continent.
     */
    public Continent(ArrayList<Territory> territories){
        
        territoriesContained.addAll(0,territories);
        
        InputPanel.showInsertionPanel("Enter a new Bonus for this Continent.", 100, "bonus", this, Appearance.WORLD_WIDTH / 2, 2 * Appearance.WORLD_HEIGHT / 3);
        InputPanel.showColorPanel("Enter a new Color for this Continent.", 100, "color", this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 3);
        
    }
    
    /**
     * Creates a Continent.
     * @param territories The Territories contained in this Continent.
     * @param color The Color of this Continent.
     * @param points The bonus given by this Continent.
     */
    public Continent(ArrayList<Territory> territories, GColor color, int points){
        
        continentColor = color;
        bonus = points;
        
        territoriesContained.addAll(0,territories);
        
    }
    
    /**
     * Adds the Continent to the World and updates its Territories.
     */
    public void addToWorld(){
        territoriesContained.forEach((Territory t) -> {t.setContinent(this);});
        map().continents.add(this);
        
        display.update();
    
    }
    
    /**
     * Lets the User edit the Color of this Continent.
     */
    public void editColor(){
        InputPanel.showColorPanel("Enter a new Color for this Continent.", 100, "color", this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        
    }
    
    /**
     * Gets the Color of this Continent.
     * @return The Color of this Continent.
     */
    public GColor color(){
        return continentColor;
        
    }
    
    /**
     * Gets the Territories contained in this Continent.
     * @return The Territories contained in this Continent.
     */
    public ArrayList<Territory> containedTerritories(){
        return (ArrayList<Territory>)territoriesContained.clone();
        
    }
    
    /**
     * Removes a Territory from this Continent.
     * @param territoryToRemove The Territory to remove.
     */
    public void removeTerritory(Territory territoryToRemove){
        territoriesContained.remove(territoryToRemove);
        if(territoriesContained.isEmpty()) this.destroy();
        
    }
    
    /** 
     * Removes it from the World and updates its Territories.
     */
    public void destroy() {
        map().continents.remove(this);
        
        territoriesContained.forEach((Territory t) -> {t.setContinent(null);});
        
        display.update();
               
    }
    
    /**
     * Lets the User edit the Bonus given by this Continent.
     */
    public void editBonus(){
        InputPanel.showInsertionPanel("Enter a new bonus for this Continent.", 100, "bonus", this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        
    }
    
    /**
     * Gets the bonus given by this Continent.
     * @return The bonus given by this Continent.
     */
    public int bonus() {
        return bonus;
    }
    
    //Selectable methods/////////////////////////////////

    @Override
    public void makeGreen() {
        for(Territory terr : territoriesContained) {
            terr.makeGreen();
        
        }
    }

    @Override
    public void makeTransparent() {
        for(Territory terr : territoriesContained) {
            terr.makeTransparent();
        
        }
    }
 
    @Override
    public void makeOpaque() {
        for(Territory terr : territoriesContained) {
            terr.makeOpaque();
        
        }
    }

    //InputPanelUser////////////////////////////////////////7
    
    @Override
    public void useInformations(String information, String type) {
        
        if(type.equals("bonus")){
            
            if(information.matches("\\d+")){
                
                int newBonus = Integer.parseInt(information);

                ((Continent)this).bonus = newBonus;
                Continent.display.update();
                
            }else{
                MessageDisplayer.showMessage("Invalid entry.");
            }
            
        }else if(type.equals("color")){
            
            ((Continent)this).continentColor = GColor.fromRGB(information);
            ((Continent)this).territoriesContained.forEach((Territory t) -> {t.setContinent(this);});
            Continent.display.update();
            
        }
        
    }
            
}

/**
 * This Class represents the Actor that displays the Continents' bonuses.
 */
class BonusDisplay extends Actor {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 200;
    
    private MyWorld world() {return MyWorld.theWorld;}
    private Map map() {return world().stateManager.map();}
    
    /**
     * Creates a BonusDisplay.
     */
    BonusDisplay() {
        this.setImage(new GreenfootImage(WIDTH, HEIGHT));
    
    }
    
    /**
     * Updates the image of a BonusDisplay.
     */
    void update() {
        this.setImage(new GreenfootImage(WIDTH, HEIGHT));
        
        //représente un tableau 2D pour l'arrangement des Bonus
        ArrayList<Continent[]> arrangedContinents = new ArrayList<>();
        
        //calculer le nombre de colonne pour l'affichage des bonus
        //le nombre de colonne devrait tendre vers 2x le nombre de lignes
        int xAxis = (int)Math.sqrt(2*map().continents.size());
        
        //rajouter les continents lignes par lignes avec xAxis continents par lignes
        Continent[] row = new Continent[xAxis];
        for(int i = 0; i < map().continents.size(); i++) {
            if(i % xAxis == 0) {
                row = new Continent[xAxis];
                arrangedContinents.add(row);
                
            }
            row[i % xAxis] = map().continents.get(i);
            
        }
        
        //le nombre de colonnes pour l'affichage des bonus
        int yAxis = arrangedContinents.size();
        
        //dessiner 
        for(int y = 0; y < yAxis; y++) {
            for(int x = 0; x < xAxis; x++) {
               Continent c = arrangedContinents.get(y)[x];
               if(c != null) {
                   GreenfootImage img = bonusImage(c);
                   int xMultiplier = (int) (getImage().getWidth() / (xAxis+1));
                   int yMultiplier = (int) (getImage().getHeight() / (yAxis+1));
                   getImage().drawImage(img, (x+1) * xMultiplier, (y+1) * yMultiplier);
                   
               }
            
            }
        
        }
        
    }
    
    /**
     * Creates a small image in which the bonus of a Continent is written.
     * @param c The said Continent.
     * @return The said image.
     */
    private GreenfootImage bonusImage(Continent c) {
        GreenfootImage img = new GreenfootImage(60, 30);
        img.setColor(c.color());
        img.fill();
        
        GColor bonusTextColor;
        
        if(c.color().luminosity() > 128) {
            bonusTextColor = new GColor(0, 0, 0);
        }else{
            bonusTextColor = new GColor(255, 255, 255);
        }
        
        GreenfootImage txt = new GreenfootImage("" + c.bonus(), 18, bonusTextColor, c.color());
        img.drawImage(txt, 30 - txt.getWidth()/2, 15 - txt.getHeight()/2);
        
        return img;
    
    }


}
