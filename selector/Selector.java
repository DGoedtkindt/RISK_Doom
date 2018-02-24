package selector;

import appearance.MessageDisplayer;
import base.Map;
import base.MyWorld;
import game.Turn;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.function.Predicate;
import mainObjects.BlankHex;
import mainObjects.Continent;
import mainObjects.Territory;

public class Selector
{
    private static MyWorld world() {
        return MyWorld.theWorld;
    }
    
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
        if(selection.isEmpty()) throw new Exception("No hex selected.");
        
        ArrayList<BlankHex> blankhexSelectedList = new ArrayList<>();
        for(Selectable select : selection) {
            try{BlankHex hex;
                hex = (BlankHex)select;
                blankhexSelectedList.add(hex);
            }  catch(ClassCastException cce) {
                throw new ClassCastException("Selectable of the wrong type selected");}
        }
        
        return blankhexSelectedList;
    }
    
    //Getters for Territory///////////////////////////////////////////////////
    
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
    
    public static Territory getSelectedTerritory() throws Exception {
        ArrayList<Territory> territorySelectedList = getSelectedTerritories();
        
        if(territorySelectedList.size() > 1) throw new Exception("Too many Territories selected.");
        
        return territorySelectedList.get(0);
    }
    
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
     for(Selectable select : selectables()) {
         select.makeTransparent();
     }
    }
     
    private static void makeValidOpaque() {
     for(Selectable select : selectables()) {
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
    public static final Predicate IS_OWNED_TERRITORY = (Object o) -> {
                    if(o instanceof Territory){
                        return ((Territory)(o)).owner() == Turn.currentTurn.player;
                    }else{
                        return false;
                    }
                    };
    public static final Predicate IS_ATTACKABLE = (Object o) -> {
                    if(o instanceof Territory){
                        try{
                            if(getSelectedTerritory().canAttack((Territory)o)){
                                return true;
                            }else{
                                return false;
                            }
                            
                        }catch(Exception e){
                            MessageDisplayer.showException(e);
                            return false;
                        }
                    }else{
                        return false;
                    }
                    };
    
}
