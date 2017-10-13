import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import greenfoot.GreenfootImage;

public class Continent implements Selectable
{
    
    private Color continentColor;
    private ArrayList<Territory> territoriesContained = new ArrayList<>();
    private int bonus;

    static private ArrayList<Continent> continentList = new ArrayList<Continent>();

    private static MyWorld world() {return MyWorld.theWorld;}
    
    public Continent(ArrayList<Territory> territories) throws Exception{
        editColor();
        editBonus();
        
        continentList.add(this);
        Selector.selectableSet.add(this);
        
        territoriesContained.addAll(0,territories);
        
        for(Territory t : territoriesContained){
             
                t.setContinent(this);
             
            }
        
        updateBonusDisplay();
    }
    
    public Continent(ArrayList<Territory> territories, Color color, int points) throws Exception{
        
        continentColor = color;
        bonus = points;
        
        continentList.add(this);
        Selector.selectableSet.add(this);
        
        territoriesContained.addAll(0,territories);
        
        for(Territory t : territoriesContained){
             
                t.setContinent(this);
             
            }
        
         updateBonusDisplay();
        
    }
    
    public void editColor() throws Exception {
            continentColor = ColorChooser.getColor();
            
            for(Territory t : territoriesContained){
                t.setContinent(this);

            }

            updateBonusDisplay();
    }
    
    public Color color(){
        return continentColor;
        
    }
    
    public ArrayList<Territory> containedTerritories(){
        return (ArrayList<Territory>)territoriesContained.clone();
        
    }
    
    public void removeTerritory(Territory territoryToRemove){
        territoriesContained.remove(territoryToRemove);
        
    }
    
    public void destroy() {
        continentList.remove(this);
        
        for(Territory terr : territoriesContained){
         
            terr.setContinent(null);
         
           }
        
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
        GreenfootImage background = new GreenfootImage(496, 118);
        background.setColor(MyWorld.WORLD_COLOR);
        background.fill();
        
        int xAxis = (int)Math.sqrt(2*continentList.size());
        
        ArrayList<Continent[]> arrangedContinents = new ArrayList<>();
        Continent[] row = new Continent[xAxis];
        for(int i = 0; i < continentList().size(); i++) {
            if(i % xAxis == 0) {
                row = new Continent[xAxis];
                arrangedContinents.add(row);
                
            }
            row[i % xAxis] = continentList().get(i);
            
        }
        
        int yAxis = arrangedContinents.size();
        
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
        
        world().getBackground().drawImage(background,620,900);
    }
    
    private GreenfootImage bonusImage() {
        GreenfootImage img = new GreenfootImage(60, 30);
        img.setColor(continentColor);
        img.fill();
        GreenfootImage txt = new GreenfootImage("" + bonus, 18, Color.BLACK, continentColor);
        img.drawImage(txt, 30 - txt.getWidth()/2, 15 - txt.getHeight()/2);
        
        return img;
    
    }
    
    //Public Static methods////////////////////////////////////////////////
    
    public static ArrayList<Continent> continentList(){
        return (ArrayList<Continent>)continentList.clone();
        
    }
    
    //Selectable methods/////////////////////////////////
    
    public void makeGreen() {
        for(Territory terr : territoriesContained) {
            terr.makeGreen();
        
        }
    }
    
    public void makeTransparent() {
        for(Territory terr : territoriesContained) {
            terr.makeTransparent();
        
        }
    }
    
    public void makeOpaque() {
        for(Territory terr : territoriesContained) {
            terr.makeOpaque();
        
        }
    }
            
}
