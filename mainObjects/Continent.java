package mainObjects;

import base.ColorChooser;
import base.GColor;
import base.Map;
import base.MyWorld;
import selector.Selectable;
import greenfoot.Actor;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import greenfoot.GreenfootImage;

public class Continent implements Selectable
{
    public static final Actor display = new BonusDisplay();
    
    //private variable
    private MyWorld world() {return MyWorld.theWorld;}
    private Map map() {return world().stateManager.map();}
    private GColor continentColor;
    private ArrayList<Territory> territoriesContained = new ArrayList<>();
    private int bonus;
    
    //public methods
    public Continent(ArrayList<Territory> territories) throws Exception{
        editColor();
        editBonus();
        
        territoriesContained.addAll(0,territories);
        
    }
    
    public Continent(ArrayList<Territory> territories, GColor color, int points) throws Exception{
        
        continentColor = color;
        bonus = points;
        
        territoriesContained.addAll(0,territories);
        
    }
    
    public void addToWorld() {
        //to Update the territories continent and color
        territoriesContained.forEach((Territory t) -> {t.setContinent(this);});
        map().continents.add(this);
        
        ((BonusDisplay)display).update();
    
    }
    
    public void editColor() throws Exception {
        continentColor = ColorChooser.getColor();
        territoriesContained.forEach((Territory t) -> {t.setContinent(this);});

        ((BonusDisplay)display).update();
        
    }
    
    public GColor color(){
        return continentColor;
        
    }
    
    public ArrayList<Territory> containedTerritories(){
        return (ArrayList<Territory>)territoriesContained.clone();
        
    }
    
    public void removeTerritory(Territory territoryToRemove){
        //is only used through Territory.destroy()
        territoriesContained.remove(territoryToRemove);
        if(territoriesContained.isEmpty()) this.destroy();
        
    }
    
    /** removes it from the world and from all contained territories
     * should not be used if it is outside the world.
     */
    public void destroy() {
        map().continents.remove(this);
        
        territoriesContained.forEach((Territory t) -> {t.setContinent(null);});
        
        ((BonusDisplay)display).update();
               
    }
    
    public void editBonus() throws Exception {
        int newBonus = 0;
        String bonusString = JOptionPane.showInputDialog("Entrez le nouveau bonus pour le continent");
        if(!bonusString.isEmpty()){newBonus = Integer.parseInt(bonusString);}
        if(newBonus <= 0){newBonus = 0;}
        bonus = newBonus;
        ((BonusDisplay)display).update();
    }
    
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
            
}

class BonusDisplay extends Actor {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 200;
    
    private MyWorld world() {return MyWorld.theWorld;}
    private Map map() {return world().stateManager.map();}
    
    BonusDisplay() {
        this.setImage(new GreenfootImage(WIDTH, HEIGHT));
    
    }
    
    void update() {
        this.setImage(new GreenfootImage(WIDTH, HEIGHT));
        
        //représente un tableau 2D pour l'arrangement des Bonus
        ArrayList<Continent[]> arrangedContinents = new ArrayList<>();
        
        //calculer le nombre de colonne pour l'affichage des bonus
        //le nombre de colonne devrais tendre vers 2x le nombre de lignes
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

    private GreenfootImage bonusImage(Continent c) {
        GreenfootImage img = new GreenfootImage(60, 30);
        img.setColor(c.color());
        img.fill();
        GreenfootImage txt = new GreenfootImage("" + c.bonus(), 18, GColor.BLACK, c.color());
        img.drawImage(txt, 30 - txt.getWidth()/2, 15 - txt.getHeight()/2);
        
        return img;
    
    }


}
