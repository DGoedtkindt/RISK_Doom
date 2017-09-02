import java.util.HashSet;
import java.util.ArrayList;

public class Selector
{
    
    public static HashSet<Selectable> selectableSet = new HashSet<>();
    
    private static HashSet<Selectable> selection = new HashSet<>();
    public static Validator validator = (Object o) -> {return true;};
    
    
    public static boolean select(Selectable selectedObject) {
        if(validator.isValid(selectedObject)) {
            selection.add(selectedObject);
            updateAppearance();
            return true;
        } else { return false; }  
    }
    
    
    //Getters for SingleHex///////////////////////////////////////////////////
    
    public static ArrayList<SingleHex> getSelectedHexes() throws Exception
    {
        if(selection.isEmpty()) throw new Exception("no hex selected");
        
        ArrayList<SingleHex> singleHexSelectedList = new ArrayList<>();
        for(Selectable select : selection) {
            try{SingleHex hex;
                hex = (SingleHex)select;
                singleHexSelectedList.add(hex);
            }  catch(ClassCastException cce) {throw new Exception("selectable of the wrong type selected\n" + cce);}
        }
        
        return singleHexSelectedList;
    }
    
    //Getters for Territory///////////////////////////////////////////////////
    
    public static Territory getSelectedTerritory() throws Exception
    {
        if(selection.size() > 1) throw new Exception("too many Territories selected");
        if(selection.size() < 1) throw new Exception("no Territory selected");
        
        ArrayList<Territory> territorySelectedList = new ArrayList<>();
        for(Selectable select : selection) {
            try{Territory terr;
                terr = (Territory)select;
                territorySelectedList.add(terr);
            }  catch(ClassCastException cce) {throw new Exception("selectable of the wrong type selected\n" + cce);}
        }
        
        return territorySelectedList.get(0);
    }
    
    public static Territory[] getSelectedTerritoryPair() throws Exception
    {
        
        if(selection.size() > 2) throw new Exception("too many Territories selected");
        if(selection.size() < 2) throw new Exception("not enough Territory selected");
        
        Territory[] territoryArray = new Territory[2];
        int i = 0;
        for(Selectable select : selection){
        
            
            try{Territory terr;
                terr = (Territory)select;
                territoryArray[i] = terr;
            }  catch(ClassCastException cce) {throw new Exception("selectable of the wrong type selected\n" + cce);}
            i++;
            
        }
        
        return territoryArray;

    }

    public static ArrayList<Territory> getSelectedTerritories() throws Exception
    {
        
        if(selection.isEmpty()) throw new Exception("no Territory selected");
        
        ArrayList<Territory> territorySelectedList = new ArrayList<>();
        for(Selectable select : selection) {
            try{Territory terr;
                terr = (Territory)select;
                territorySelectedList.add(terr);
            }  catch(ClassCastException cce) {throw new Exception("selectable of the wrong type selected\n" + cce);}
        }
        return territorySelectedList;

    }
    
    //Getters for Continent///////////////////////////////////////////////////
    
    public static Continent getSelectedContinent() throws Exception
    {
        if(selection.size() > 1) throw new Exception("too many Continent selected");
        if(selection.size() < 1) throw new Exception("no Continent selected");
        
        ArrayList<Continent> continentSelectedList = new ArrayList<>();
        for(Selectable select : selection) {
            try{Continent cont;
                cont = (Continent)select;
                continentSelectedList.add(cont);
            }  catch(ClassCastException cce) {throw new Exception("selectable of the wrong type selected\n" + cce);}
        }
        
        return continentSelectedList.get(0);
    }
    
    ///////////////////////////////////////////////////////////
    
    public static void clear()
    {
        selection.clear();
        validator = (Object o) -> {return true;};
        updateAppearance();
    }
    
    public static void updateAppearance() {    
        makeAllTransparent();
        makeValidOpaque();
        makeSelectedGreen();
    }
    
    public static void setValidator(Validator newValidator) {
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
         if(validator.isValid(select)) {
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
    
    public static final Validator IS_SINGLEHEX = (Object o) -> {return o instanceof SingleHex;};
    public static final Validator IS_CONTINENT = (Object o) -> {return o instanceof Continent;};
    public static final Validator IS_TERRITORY = (Object o) -> {return o instanceof Territory;};
    public static final Validator IS_TERRITORY_NOT_IN_CONTINENT = (Object o) -> 
                    {if(o instanceof Territory) {
                        return ((Territory)(o)).getContinent() == null;
                    } else return false;
                        };
    public static final Validator NOTHING = (Object o) -> {return false;};
    public static final Validator EVERYTHING = (Object o) -> {return true;};
    
}
