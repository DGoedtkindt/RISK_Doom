package mainObjects;

import appearance.Appearance;
import Selection.Selectable;
import Selection.Selector;
import appearance.Theme;
import base.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import greenfoot.GreenfootImage;

public class Continent implements Selectable
{
    //private static variables
    private static ArrayList<Continent> continentList = new ArrayList<Continent>();
    private static MyWorld world() {return MyWorld.theWorld;}
    
    //private variable
    private GColor continentColor;
    private ArrayList<Territory> territoriesContained = new ArrayList<>();
    private int bonus;
    
    //public methods
    public Continent(ArrayList<Territory> territories) throws Exception{
        editColor();
        editBonus();
        
        continentList.add(this);
        Selector.selectableSet.add(this);
        territoriesContained.addAll(0,territories);
        territoriesContained.forEach((Territory t) -> {t.setContinent(this);});
        
        updateBonusDisplay();
    }
    
    public Continent(ArrayList<Territory> territories, GColor color, int points) throws Exception{
        
        continentColor = color;
        bonus = points;
        
        continentList.add(this);
        Selector.selectableSet.add(this);
        territoriesContained.addAll(0,territories);
        
        //to Update the territories continent and color
        territoriesContained.forEach((Territory t) -> {t.setContinent(this);});
        
         updateBonusDisplay();
        
    }
    
    public void editColor() throws Exception {
        continentColor = ColorChooser.getColor();
        territoriesContained.forEach((Territory t) -> {t.setContinent(this);});

        updateBonusDisplay();
        
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
    
    public void destroy() {
        continentList.remove(this);
        
        territoriesContained.forEach((Territory t) -> {t.setContinent(null);});
        
        Selector.selectableSet.remove(this);
        updateBonusDisplay();
               
    }
    
    public void editBonus() throws Exception {
        int newBonus = 0;
        String bonusString = JOptionPane.showInputDialog("Entrez le nouveau bonus pour le continent");
        if(!bonusString.isEmpty()){newBonus = Integer.parseInt(bonusString);}
        if(newBonus <= 0){newBonus = 0;}
        bonus = newBonus;
        updateBonusDisplay();
    }
    
    public int bonus() {
        return bonus;
    
    }
    
    //Private methods////////////////////////////////////////////////
    
    private static void updateBonusDisplay() {
        //représente un tableau 2D pour l'arrangement des Bonus
        ArrayList<Continent[]> arrangedContinents = new ArrayList<>();
        
        //calculer le nombre de colonne pour l'affichage des bonus
        //le nombre de colonne devrais tendre vers 2x le nombre de lignes
        int xAxis = (int)Math.sqrt(2*continentList.size());
        
        //rajouter les continents lignes par lignes avec xAxis continents par lignes
        Continent[] row = new Continent[xAxis];
        for(int i = 0; i < continentList().size(); i++) {
            if(i % xAxis == 0) {
                row = new Continent[xAxis];
                arrangedContinents.add(row);
                
            }
            row[i % xAxis] = continentList().get(i);
            
        }
        
        //le nombre de colonnes pour l'affichage des bonus
        int yAxis = arrangedContinents.size();
        
        //Détermine les coordonnées qui serviront à créer une image de la bonne taille
        int[] firstHexPos = Hexagon.hexToRectCoord(new int[]{Appearance.CONTINENT_BONUS_X_POSITION, 
                                                            Appearance.CONTINENT_BONUS_Y_POSITION});
        
        int[] secondHexPos = Hexagon.hexToRectCoord(new int[]{Appearance.CONTINENT_BONUS_X_POSITION + Appearance.CONTINENT_BONUS_ZONE_WIDTH, 
                                                            Appearance.CONTINENT_BONUS_Y_POSITION + Appearance.CONTINENT_BONUS_ZONE_HEIGHT});
        
        int[] dimensions = new int[]{secondHexPos[0] - firstHexPos[0], secondHexPos[1] - firstHexPos[1]};
        
        //créer l'image sur laquelle les bonus vont s'afficher
        GreenfootImage background = new GreenfootImage(dimensions[0], dimensions[1]);
        background.setColor(Theme.used.backgroundColor);
        background.fill();
        
        //dessiner 
        for(int y = 0; y < yAxis; y++) {
            for(int x = 0; x < xAxis; x++) {
               Continent c = arrangedContinents.get(y)[x];
               if(c != null) {
                   GreenfootImage img = c.bonusImage();
                   int xMultiplier = (int) (background.getWidth() / (xAxis+1));
                   int yMultiplier = (int) (background.getHeight() / (yAxis+1));
                   background.drawImage(img, (x+1) * xMultiplier, (y+1) * yMultiplier);
                   
               }
            
            }
        
        }
        
        world().getBackground().drawImage(background, firstHexPos[0] - Hexagon.RADIUS + 3, firstHexPos[1]);
    }

    private GreenfootImage bonusImage() {
        GreenfootImage img = new GreenfootImage(60, 30);
        img.setColor(continentColor);
        img.fill();
        GreenfootImage txt = new GreenfootImage("" + bonus, 18, GColor.BLACK, continentColor);
        img.drawImage(txt, 30 - txt.getWidth()/2, 15 - txt.getHeight()/2);
        
        return img;
    
    }
    
    //Public Static methods////////////////////////////////////////////////
    
    public static ArrayList<Continent> continentList(){
        return (ArrayList<Continent>)continentList.clone();
        
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
