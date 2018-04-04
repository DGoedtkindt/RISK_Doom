package selector;

import base.Map;
import base.MyWorld;
import game.Turn;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.function.Predicate;
import base.BlankHex;
import java.util.Set;
import territory.Continent;
import territory.Territory;

/**
 * The Selector is the Class that selects and manages the selection of the Selectable
 * Objects.
 * 
 */
public class Selector{
    
    private static HashSet<Selectable> selection = new HashSet<>();
    public static Predicate validator = (Object o) -> {return true;};
    
    private static MyWorld world() {
        return MyWorld.theWorld;
    }
    
    /**
     * Obtains a Set of every Selectable in the World.
     * @return The Set of all Selectables in the the stateManager's Map.
     */
    public static Set<Selectable> selectables() {
        Map map = world().stateManager.map();
        
        HashSet selectables = new HashSet<>();
        selectables.addAll(map.continents);
        selectables.addAll(map.territories);
        selectables.addAll(BlankHex.BLANK_HEXS);
    
        return selectables;
    }
    
    /**
     * Adds a Selectable to the current selection if it's currently allowed 
     * to be selected.
     * @param selectedObject The selected Object.
     */
    public static void select(Selectable selectedObject) {
        if(validator.test(selectedObject)) {
            if(!selection.contains(selectedObject)) {
                selection.add(selectedObject);
                selectedObject.makeSelected();
            } else {
                selection.remove(selectedObject);
                updateAppearance();
            }
            
        
        }
    }
    
    //Getters for BlankHex///////////////////////////////////////////////////

    /**
     * Obtains a Set of the Selected BlankHexes.
     * @return The Set of the selected BlankHexes.
     * @throws Exception If the selection is empty or if a Selectable that is 
     *         not a BlankHex has been added to the selection.
     */
    public static  Set<BlankHex> getSelectedHexes() throws Exception{
        if(selection.isEmpty()) throw new Exception("No hex selected.");
        
        Set<BlankHex> blankhexSelectedSet = new HashSet<>();
        for(Selectable select : selection) {
            try{BlankHex hex;
                hex = (BlankHex)select;
                blankhexSelectedSet.add(hex);
            }  catch(ClassCastException cce) {
                throw new ClassCastException("Selectable of the wrong type selected.");}
        }
        
        return blankhexSelectedSet;
    }
    
    //Getters for Territory///////////////////////////////////////////////////

    /**
     * Obtains a list of the Selected Territories.
     * @return The list of the selected Territories..
     * @throws Exception If the selection is empty or if a Selectable that is 
     *         not a Territory has been added to the selection.
     */
    public static Set<Territory> getSelectedTerritories() throws Exception {
        if(selection.isEmpty()) throw new Exception("No Territory selected.");
        
        Set<Territory> territorySelectedSet = new HashSet<>();
        for(Selectable select : selection) {
            try{Territory terr;
                terr = (Territory)select;
                territorySelectedSet.add(terr);
            }  catch(ClassCastException cce) {
                throw new ClassCastException("Selectable of the wrong type selected\n" + cce);}
        }
        return territorySelectedSet;

    }
    
    /**
     * Obtains a single selected Territory.
     * @return The selected Territory.
     * @throws Exception If more than one Territory has been selected.
     */
    public static Territory getSelectedTerritory() throws Exception {
        ArrayList<Territory> territorySelectedList = new ArrayList<>(getSelectedTerritories());
        
        if(territorySelectedList.size() > 1) throw new Exception("Too many Territories selected.");
        
        return territorySelectedList.get(0);
    }
    
    //Getters for Continent///////////////////////////////////////////////////
    
    /**
     * Obtains a Set of the Selected Continents.
     * @return The Set of the selected Continents.
     * @throws Exception If the selection is empty or if a Selectable that is 
     *         not a Continent has been added to the selection.
     */
    public static Set<Continent> selectedContinents() throws Exception {
        if(selection.size() < 1) throw new Exception("No Continent selected.");
        
        Set<Continent> continentSelectedSet = new HashSet<>();
        for(Selectable select : selection) {
            try{Continent cont;
                cont = (Continent)select;
                continentSelectedSet.add(cont);
            }catch(ClassCastException cce) {
                throw new ClassCastException("Selectable of the wrong type selected\n" + cce);
            }
        }
        
        return continentSelectedSet;
    
    }
    
    ///////////////////////////////////////////////////////////

    /**
     * Clears the Selection.
     */
    public static void clear(){
        selection.clear();
        validator = EVERYTHING;
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
            select.makeSelected();
        }

    }
    
    //Validators//////////////////////////////////////////////
    
    public static final Predicate IS_BLANKHEX = (Object o) -> {return o instanceof BlankHex;};
    public static final Predicate IS_CONTINENT = (Object o) -> {return o instanceof Continent;};
    public static final Predicate IS_TERRITORY = (Object o) -> {return o instanceof Territory;};
    public static final Predicate IS_TERRITORY_NOT_IN_CONTINENT = (Object o) -> {
        return o instanceof Territory && ((Territory)(o)).continent() == null;
    };
    public static final Predicate NOTHING = (Object o) -> {return false;};
    public static final Predicate EVERYTHING = (Object o) -> {return true;};
    public static final Predicate IS_OWNED_TERRITORY = (Object o) -> {
        return o instanceof Territory  && ((Territory)(o)).owner() == Turn.currentTurn.player;
    };
    
}
