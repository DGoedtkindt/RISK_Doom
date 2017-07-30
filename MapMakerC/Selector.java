import java.util.*;

public class Selector  
{
    static final int TRANSPARENT = 30;
    static final int OPAQUE = 255;
    
    public static ArrayList<Selectable> selectableList = new ArrayList<Selectable>();
    
    private static HashSet<SingleHex> singleHexSelection = new HashSet<SingleHex>();
    private static HashSet<Territory> territorySelection = new HashSet<Territory>();
    
    public static void selectSingleHex(SingleHex selectedHex)
    //rajoute un SingleHex à la sélection
    {
        
        singleHexSelection.add(selectedHex);
        
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
        
        if(!(singleHexSelection.isEmpty())){
            
            for(SingleHex sh : singleHexSelection){
                
                sh.setImage(Hexagon.createHexagonImage(SingleHex.BASE_COLOR));
                
            }
            
        }
        
        singleHexSelection.clear();
        territorySelection.clear();
        setTheseOpaque(selectableList);
    }
    
    ////////////////////////////////////////////////////
    
    
    public static void setTheseOpaque(ArrayList<Selectable> toOpaqueList){
        for(Selectable toOpaque : toOpaqueList){
            
            toOpaque.setOpaque();
            
        }
    }
    
    public static void setTheseTransparent(ArrayList<Selectable> toTransparentList)
    {
        for(Selectable toTransparent : toTransparentList){
            
            toTransparent.setTransparent();
            
        }
    }
    
    public static void setTheseSelected(ArrayList<Selectable> toSelectedList)
    {   
        for(Selectable toSelect : toSelectedList){
                    
              toSelect.setSelected();
                    
        }     
    }
}
