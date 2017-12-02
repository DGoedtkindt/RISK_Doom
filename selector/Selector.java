package selector;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.function.Predicate;
import mainObjects.BlankHex;
import mainObjects.Continent;
import mainObjects.Territory;

public class Selector
{
    
    public static HashSet<Selectable> selectableSet = new HashSet<>();
    
    private static HashSet<Selectable> selection = new HashSet<>();
    public static Predicate validator = (Object o) -> {return true;};
    
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
    
    public static ArrayList<BlankHex> getSelectedHexes() throws Exception{
        if(selection.isEmpty()) throw new Exception("no hex selected");
        
        ArrayList<BlankHex> blankhexSelectedList = new ArrayList<>();
        for(Selectable select : selection) {
            try{BlankHex hex;
                hex = (BlankHex)select;
                blankhexSelectedList.add(hex);
            }  catch(ClassCastException cce) {
                throw new Exception("selectable of the wrong type selected\n" + cce);}
        }
        
        return blankhexSelectedList;
    }
    
    //Getters for Territory///////////////////////////////////////////////////
    
    public static Territory getSelectedTerritory() throws Exception {
        if(selection.size() > 1) throw new Exception("too many Territories selected");
        if(selection.size() < 1) throw new Exception("no Territory selected");
        
        ArrayList<Territory> territorySelectedList = new ArrayList<>();
        for(Selectable select : selection) {
            try{Territory terr;
                terr = (Territory)select;
                territorySelectedList.add(terr);
            }  catch(ClassCastException cce) {
                throw new Exception("selectable of the wrong type selected\n" + cce);}
        }
        
        return territorySelectedList.get(0);
    }

    public static ArrayList<Territory> getSelectedTerritories() throws Exception {
        if(selection.isEmpty()) throw new Exception("no Territory selected");
        
        ArrayList<Territory> territorySelectedList = new ArrayList<>();
        for(Selectable select : selection) {
            try{Territory terr;
                terr = (Territory)select;
                territorySelectedList.add(terr);
            }  catch(ClassCastException cce) {
                throw new Exception("selectable of the wrong type selected\n" + cce);}
        }
        return territorySelectedList;

    }
    
    //Getters for Continent///////////////////////////////////////////////////
    
    public static Continent getSelectedContinent() throws Exception {
        if(selection.size() > 1) throw new Exception("too many Continent selected");
        if(selection.size() < 1) throw new Exception("no Continent selected");
        
        ArrayList<Continent> continentSelectedList = new ArrayList<>();
        for(Selectable select : selection) {
            try{Continent cont;
                cont = (Continent)select;
                continentSelectedList.add(cont);
            }  catch(ClassCastException cce) {
                throw new Exception("selectable of the wrong type selected\n" + cce);}
        }
        
        return continentSelectedList.get(0);
    }
    
    ///////////////////////////////////////////////////////////
    
    public static void clear(){
        selection.clear();
        validator = (Object o) -> {return true;};
        updateAppearance();
        
    }
    
    public static void updateAppearance() {    
        makeAllTransparent();
        makeValidOpaque();
        makeSelectedGreen();
    }
    
    public static void setValidator(Predicate newValidator) {
        validator = newValidator;
        updateAppearance();
    }
    
    /////////////////////////////////////////////////////////
    
    private static void makeAllTransparent() {
     for(Selectable select : selectableSet) {
         select.makeTransparent();
     }
    }
     
    private static void makeValidOpaque() {
     for(Selectable select : selectableSet) {
         if(validator.test(select)) {
            select.makeOpaque();
         }
     }
    }

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
    
}
