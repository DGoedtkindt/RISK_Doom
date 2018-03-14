package selector;

import base.Map;
import base.MyWorld;
import game.Turn;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.function.Predicate;
import mainObjects.BlankHex;
import mainObjects.Continent;
import mainObjects.Territory;

/**
 * The Selector is the Class that selects and manages the selection of the Selectable
 * Objects.
 * 
 */
public class Selector{
    
    private static MyWorld world() {
        return MyWorld.theWorld;
    }
    
    /**
     * Obtains a list of every Selectable in the World.
     * @return The list of all Selectables in teh World.
     */
    public static HashSet<Selectable> selectables() {
        Map map = world().stateManager.map();
        
        HashSet selectables = new HashSet<>();
        selectables.addAll(map.continents);
        selectables.addAll(map.territories);
        selectables.addAll(BlankHex.BLANK_HEXS);
    
        return selectables;
    }
    
    private static HashSet<Selectable> selection = new HashSet<>();
    public static Predicate validator = (Object o) -> {return true;};
    
    /**
     * Adds a Selectable to the current selection if it's currently allowed 
     * to be selected.
     * @param selectedObject The selected Object.
     */
    public static void select(Selectable selectedObject) {
        if(validator.test(selectedObject)) {
            if(!selection.contains(selectedObject)) {
                selection.add(selectedObject);
                selectedObject.makeGreen();
            } else {
                selection.remove(selectedObject);
                updateAppearance();
            }
            
        
        }
    }
    
    //Getters for BlankHex///////////////////////////////////////////////////

    /**
     * Obtains a list of the Selected BlankHexes.
     * @return The list of the selected BlankHexes.
     * @throws Exception If the selection is empty or if a Selectable that is 
     *         not a BlankHex has been added to the selection.
     */
    public static ArrayList<BlankHex> getSelectedHexes() throws Exception{
        if(selection.isEmpty()) throw new Exception("No hex selected.");
        
        ArrayList<BlankHex> blankhexSelectedList = new ArrayList<>();
        for(Selectable select : selection) {
            try{BlankHex hex;
                hex = (BlankHex)select;
                blankhexSelectedList.add(hex);
            }  catch(ClassCastException cce) {
                throw new ClassCastException("Selectable of the wrong type selected.");}
        }
        
        return blankhexSelectedList;
    }
    
    //Getters for Territory///////////////////////////////////////////////////

    /**
     * Obtains a list of the Selected Territories.
     * @return The list of the selected Territories..
     * @throws Exception If the selection is empty or if a Selectable that is 
     *         not a Territory has been added to the selection.
     */
    public static ArrayList<Territory> getSelectedTerritories() throws Exception {
        if(selection.isEmpty()) throw new Exception("No Territory selected.");
        
        ArrayList<Territory> territorySelectedList = new ArrayList<>();
        for(Selectable select : selection) {
            try{Territory terr;
                terr = (Territory)select;
                territorySelectedList.add(terr);
            }  catch(ClassCastException cce) {
                throw new ClassCastException("Selectable of the wrong type selected\n" + cce);}
        }
        return territorySelectedList;

    }
    
    /**
     * Obtains a single selected Territory.
     * @return The selected Territory.
     * @throws Exception If more than one Territory has benn selected.
     */
    public static Territory getSelectedTerritory() throws Exception {
        ArrayList<Territory> territorySelectedList = getSelectedTerritories();
        
        if(territorySelectedList.size() > 1) throw new Exception("Too many Territories selected.");
        
        return territorySelectedList.get(0);
    }
    
    /**
     * Gets the number of selected Territories.
     * @return The number of selected Territories.
     */
    public static int territoriesNumber(){
        
        ArrayList<Territory> territorySelectedList = new ArrayList<>();
        for(Selectable select : selection) {
            try{Territory terr;
                terr = (Territory)select;
                territorySelectedList.add(terr);
            }  catch(ClassCastException cce) {}
        }
        return territorySelectedList.size();

    }
    
    //Getters for Continent///////////////////////////////////////////////////
    
    /**
     * Obtains a list of the Selected Continents.
     * @return The list of the selected Continents.
     * @throws Exception If the selection is empty or if a Selectable that is 
     *         not a Continent has been added to the selection.
     */
    public static ArrayList<Continent> continentSelectedList() throws Exception {
        if(selection.size() < 1) throw new Exception("No Continent selected.");
        
        ArrayList<Continent> continentSelectedList = new ArrayList<>();
        for(Selectable select : selection) {
            try{Continent cont;
                cont = (Continent)select;
                continentSelectedList.add(cont);
            }  catch(ClassCastException cce) {
                throw new ClassCastException("Selectable of the wrong type selected\n" + cce);}
        }
        
        return continentSelectedList;
    
    }
    
    ///////////////////////////////////////////////////////////

    /**
     * Clears the Selection.
     */
    public static void clear(){
        selection.clear();
        validator = (Object o) -> {return true;};
        updateAppearance();
        
    }
    
    /**
     * Updates the image of the selected Selectables.
     */
    public static void updateAppearance() {    
        makeAllTransparent();
        makeValidOpaque();
        makeSelectedGreen();
    }
    
    /**
     * Changes the test that allows only a certain type of Selectables to 
     * be selected.
     * @param newValidator A Predicate that represents a new test.
     */
    public static void setValidator(Predicate newValidator) {
        validator = newValidator;
        updateAppearance();
    }
    
    /////////////////////////////////////////////////////////
    
    /**
     * Sets the transparency of each Selectable to a transparent value.
     */
    private static void makeAllTransparent() {
     for(Selectable select : selectables()) {
         select.makeTransparent();
     }
    }
    
    /**
     * Sets the transparency of all Selectables that can be selected to an 
     * opaque one.
     */
    private static void makeValidOpaque() {
     for(Selectable select : selectables()) {
         if(validator.test(select)) {
            select.makeOpaque();
         }
     }
    }
    
    /**
     * Updates the image of the selected Selectables to the current selection Color.
     */
    private static void makeSelectedGreen() {
        for(Selectable select : selection) {
            select.makeGreen();
        }

    }
    
    //Validators//////////////////////////////////////////////
    
    public static final Predicate IS_BLANKHEX = (Object o) -> {return o instanceof BlankHex;};
    public static final Predicate IS_CONTINENT = (Object o) -> {return o instanceof Continent;};
    public static final Predicate IS_TERRITORY = (Object o) -> {return o instanceof Territory;};
    public static final Predicate IS_TERRITORY_NOT_IN_CONTINENT = (Object o) -> 
                    {if(o instanceof Territory) {
                        return ((Territory)(o)).continent() == null;
                    } else return false;
                        };
    public static final Predicate NOTHING = (Object o) -> {return false;};
    public static final Predicate EVERYTHING = (Object o) -> {return true;};
    public static final Predicate IS_OWNED_TERRITORY = (Object o) -> {
                    if(o instanceof Territory){
                        return ((Territory)(o)).owner() == Turn.currentTurn.player;
                    }else{
                        return false;
                    }
                    };
    public static final Predicate CAN_ATTACK = (Object o) -> {
                    if(o instanceof Territory){
                        return ((Territory)(o)).owner() == Turn.currentTurn.player && ((Territory)(o)).armies() > 2;
                    }else{
                        return false;
                    }
                    };
    public static final Predicate IS_ATTACKABLE = (Object o) -> {
                    if(o instanceof Territory){
                        
                        if(Territory.actionSource != null){
                            
                            boolean hasFortress = false;
                            
                            if(((Territory)o).owner() != null){
                                hasFortress = ((Territory)o).owner().fortressProtection;
                            }
                            
                            return Territory.actionSource.neighbours().contains((Territory)o) && 
                                   ((Territory)o).owner() != Territory.actionSource.owner() &&
                                   !hasFortress;
                            
                        }else{
                            for(Territory t : Turn.currentTurn.player.territories()){
                                if(t.neighbours().contains((Territory)o) && ((Territory)o).owner() != t.owner() && ((Territory)o).owner() != null){
                                    return true;
                                }
                                
                            }
                            return false;
                        }
                        
                    }else{
                        return false;
                    }
                    };
    
}
