package territory;

import appearance.MessageDisplayer;
import appearance.Theme;
import base.GColor;
import base.Map;
import base.MyWorld;
import selector.Selectable;
import greenfoot.Actor;
import java.util.ArrayList;
import greenfoot.GreenfootImage;
import input.ColorInput;
import input.Form;
import input.Input;
import input.TextInput;
import java.util.Collection;
import java.util.Collections;

/**
 * The Class that represents the Continents.
 * 
 */
public class Continent implements Selectable{
    
    public static final BonusDisplay DISPLAY = new BonusDisplay();
    public final Collection<Territory> territoriesContained;
    
    //private variables
    private MyWorld world() {return MyWorld.theWorld;}
    private Map map() {return world().stateManager.map();}
    private GColor continentColor = Theme.used.territoryColor;
    private int bonus = 0;
    
    /**
     * Creates a Continent.
     * @param territories The Territories contained in this Continent.
     */
    public Continent(Collection<Territory> territories){
        territoriesContained = Collections.unmodifiableCollection(territories);
        askForBonusAndColor();
        
    }
    
    /**
     * Creates a Continent.
     * @param territories The Territories contained in this Continent.
     * @param color The Color of this Continent.
     * @param points The bonus given by this Continent.
     */
    public Continent(Collection<Territory> territories, GColor color, int points){
        continentColor = color;
        bonus = points;
        territoriesContained = Collections.unmodifiableCollection(territories);
        
    }
    
    /**
     * Adds the Continent to the World and updates its Territories.
     */
    public void addToWorld(){
        territoriesContained.forEach((Territory t) -> {t.setContinent(this);});
        map().continents.add(this);
        
        DISPLAY.update();
    
    }
    
    /**
     * Lets the User edit the Color of this Continent.
     */
    public void editColor(){
        Form.inputColor("Enter a new Color for this Continent.",
            (input)-> {
                continentColor = GColor.fromRGB(input.get("inputedColor"));
                territoriesContained.forEach((Territory t) -> {t.setContinent(this);});
                Continent.DISPLAY.update();
                world().stateManager.escape();
            });
        
    }
    
    /**
     * Gets the Color of this Continent.
     * @return The Color of this Continent.
     */
    public GColor color(){
        return continentColor;
        
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
        
        DISPLAY.update();
               
    }
    
    /**
     * Lets the User edit the Bonus given by this Continent.
     */
    public void editBonus(){
        Form.inputText("Enter a new bonus for this Continent.",
            (input)->{
                if(input.get("inputedText").matches("\\d+")){
                    bonus = Integer.parseInt(input.get("inputedText"));
                    Continent.DISPLAY.update();
                }else{
                    MessageDisplayer.showMessage("Invalid entry.");
                }
                world().stateManager.escape();
        });
        
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
    public void makeSelected() {
        for(Territory terr : territoriesContained) {
            terr.makeSelected();
        
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

    //Private methods////////////////////////////////////////
    
    private void askForBonusAndColor() {
        Form form = new Form();
        Input bonusInput = new TextInput("Enter a new Bonus for this new Continent.");
        Input colorInput = new ColorInput("Enter a new Color for this new Continent.");
        form.addInput("color", colorInput, false);
        form.addInput("bonus", bonusInput, false);
        form.submitAction = (input)->{
            continentColor = GColor.fromRGB(input.get("color"));
            territoriesContained.forEach((Territory t) -> {t.setContinent(this);});
            if(input.get("bonus").matches("\\d+")){
                bonus = Integer.parseInt(input.get("bonus"));
                Continent.DISPLAY.update();
            }else{
                MessageDisplayer.showMessage("Invalid entry.");
            }
            Continent.DISPLAY.update();
        
        };
        form.addToWorld();
    
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
        setImage(new GreenfootImage(WIDTH, HEIGHT));
    
    }
    
    /**
     * Updates the image of a BonusDisplay.
     */
    void update() {
        setImage(new GreenfootImage(WIDTH, HEIGHT));
        
        //A 2D grid to place the bonus images
        ArrayList<Continent[]> arrangedContinents = new ArrayList<>();
        
        //Computes the number of columns for the display
        //About twice the number of rows
        int xAxis = (int)Math.sqrt(2*map().continents.size());
        
        //Adds the Continents one row after another, with xAxis Continents in each row
        Continent[] row = new Continent[xAxis];
        for(int i = 0; i < map().continents.size(); i++) {
            if(i % xAxis == 0) {
                row = new Continent[xAxis];
                arrangedContinents.add(row);
                
            }
            row[i % xAxis] = map().continents.get(i);
            
        }
        
        //The number of columns
        int yAxis = arrangedContinents.size();
        
        //Drawing
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
