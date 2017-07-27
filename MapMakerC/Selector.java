import java.util.*;

public class Selector  
{
    
    private static HashSet<SingleHex> singleHexSelection = new HashSet<SingleHex>();
    private static HashSet<Territory> territorySelection = new HashSet<Territory>();
    
    public static void selectSingleHex(SingleHex selectedHex)
    //rajoute un SingleHex à la sélection
    {
        
        singleHexSelection.add(selectedHex);
        
    }
    
    public static SingleHex getSelectedHex() throws Exception
    {
        
        if(singleHexSelection.size() > 1) throw new Exception("too many hexes selected");
        if(singleHexSelection.size() < 1) throw new Exception("no hex selected");
        
        ArrayList<SingleHex> singleHexSelectedList = new ArrayList<SingleHex>();
        singleHexSelectedList.addAll(singleHexSelection);
        
        return singleHexSelectedList.get(0);

    }
    
    public static SingleHex[] getSelectedHexPair() throws Exception
    {
        
        if(singleHexSelection.size() > 2) throw new Exception("too many hexes selected");
        if(singleHexSelection.size() < 2) throw new Exception("no enough hex selected");
        
        SingleHex[] selectedHexArray = new SingleHex[2];
        int i = 0;
        for(SingleHex hex : singleHexSelection){
        
            selectedHexArray[i] = hex;
            i++;
            
        }
        
        
        
        return selectedHexArray;

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
    
    public static void clear()
    {
        singleHexSelection.clear();
        territorySelection.clear();
    }
    
}
