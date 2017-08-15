import java.util.HashSet;
import java.util.ArrayList;

public class Selector  
{
    
    private static HashSet<SingleHex> singleHexSelection = new HashSet<SingleHex>();
    private static HashSet<Territory> territorySelection = new HashSet<Territory>();
    private static Continent continentSelection = null;
    
    public static void selectSingleHex(SingleHex selectedHex)
    //rajoute un SingleHex à la sélection
    {
        
        singleHexSelection.add(selectedHex);
        selectedHex.makeGreen();
        
    }
    
    public static ArrayList<SingleHex> getSelectedHexes() throws Exception
    {
        
        if(singleHexSelection.isEmpty()) throw new Exception("no hex selected");
        
        ArrayList<SingleHex> singleHexSelectedList = new ArrayList<SingleHex>();
        singleHexSelectedList.addAll(singleHexSelection);
        
        return singleHexSelectedList;

    }
    
    /////////////////////////////////////////////////////

    public static void selectTerritory(Territory selectedTerritory)
    //rajoute un Territory à la selection
    {
        
        territorySelection.add(selectedTerritory);
        selectedTerritory.makeGreen();
        
    }
    
    public static Territory getSelectedTerritory() throws Exception
    {
        
        if(territorySelection.size() > 1) throw new Exception("too many Territories selected");
        if(territorySelection.size() < 1) throw new Exception("no Territory selected");
        
        ArrayList<Territory> territorySelectedList = new ArrayList<Territory>();
        territorySelectedList.addAll(territorySelection);
        
        return territorySelectedList.get(0);

    }
    
    public static Territory[] getSelectedTerritoryPair() throws Exception
    {
        
        if(territorySelection.size() > 2) throw new Exception("too many Territories selected");
        if(territorySelection.size() < 2) throw new Exception("no enough Territory selected");
        
        Territory[] territoryArray = new Territory[2];
        int i = 0;
        for(Territory hex : territorySelection){
        
            territoryArray[i] = hex;
            i++;
            
        }
        
        return territoryArray;

    }

    public static ArrayList<Territory> getSelectedTerritories() throws Exception
    {
        
        if(territorySelection.isEmpty()) throw new Exception("no Territory selected");
        
        ArrayList<Territory> territorySelectedList = new ArrayList<Territory>();
        territorySelectedList.addAll(territorySelection);
        
        return territorySelectedList;

    }
    
    /////////////////////////////////////////////////////
    
    public static void selectContinent(Continent selectedContinent)
    //set le continent sélectionné
    {
        if(continentSelection == null) {
        
            continentSelection = selectedContinent;
            selectedContinent.makeGreen();
            
        }
    }
    
    public static Continent getSelectedContinent() throws Exception
    {
        
        if(continentSelection == null) throw new Exception("no continent selected");
        
        return continentSelection;

    }
    
    ///////////////////////////////////////////////////////////
    
    public static void clear()
    {
        
        if(!(singleHexSelection.isEmpty())){
            
            for(SingleHex sh : singleHexSelection){
                
                sh.setImage(Hexagon.createHexagonImage(SingleHex.BASE_COLOR));
                
            }
            
        }
        
        singleHexSelection.clear();
        territorySelection.clear();
    }
    
}
