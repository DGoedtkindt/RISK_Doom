package base;

import mode.Mode;
import selector.Selector;
import selector.Selectable;
import appearance.Appearance;
import appearance.MessageDisplayer;
import appearance.Theme;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import territory.Territory;

/**
 * Blank Hexes are the basic Hexagons buttons of a Map. 
 * They represent a spot where a part of a Territory can be.
 * And they represent coordinates.
 * 
 */
public class BlankHex extends Button implements Selectable{
    public static final int MAX_COLUMN = 40;
    public static final int MAX_ROW = 40;
    
    private static final BlankHex[][] BLANK_HEX_ARRAY = new BlankHex[MAX_COLUMN][MAX_ROW];
    public static final HashSet<BlankHex> BLANK_HEXS = new HashSet<>();
    
    private static HashMap<BlankHex,Territory> terrsOverHexs = new HashMap<>();
    
    private final int[] hexCoord;
    
    /**
     * Creates a BlankHex Object at given coordinates.
     * @param xHCoord The x hexagonal coordinate.
     * @param yHCoord The y hexagonal coordinate.
     */
    private BlankHex(int xHCoord, int yHCoord) {
        hexCoord = new int[]{xHCoord,yHCoord};
        setImage();
        BLANK_HEXS.add(this);
        BLANK_HEX_ARRAY[hexCoord[0]][hexCoord[1]] = this;
    }
    
    /**
     * Gives an image to a BlankHex.
     */
    private void setImage() {
        this.setImage(Hexagon.createImageWBorder(Theme.used.blankHexColor));
    }
    
    /**
     * Gets the BlankHex at given coordinates.
     * @param hexX The x hexagonal coordinate.
     * @param hexY The y hexagonal coordinate.
     * @return The BlankHex at the given coordinates.
     * returns null if one of the indexes is negative or exceeds the limit.
     */
    public static BlankHex blankHexAt(int hexX, int hexY) {
        BlankHex returnHex;
        try {
            if(BLANK_HEX_ARRAY[hexX][hexY] != null) {
                returnHex = BLANK_HEX_ARRAY[hexX][hexY];
            } else {
                returnHex = new BlankHex(hexX, hexY);
            }
            return returnHex;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    
    /**
     * Updates the image of each BlankHex.
     */
    public static void updateAllImages() {
        for(int x = 0; x < MAX_COLUMN; x++) {
            for(int y = 0; y < MAX_ROW; y++) {
                blankHexAt(x,y).setImage();
                
            }
            
        }
    
    }
    
    /**
     * Gets the hexagonal coordinates of this BlankHex.
     * @return The hexagonal coordinates of this BlankHex.
     */
    public int[] hexCoord() {
        return hexCoord;
    }
    
    /**
     * Gets the rectangular, normal coordinates of this BlankHex.
     * @return The rectangular, normal coordinates of this BlankHex.
     */
    public int[] rectCoord() {
        return Hexagon.hexToRectCoord(hexCoord);
    }
    
    public void addToWorld() {
        world().addObject(this, rectCoord()[0], rectCoord()[1]);
    
    }
    
    public void removeFromWorld() {
        world().removeObject(this);
        
    
    }
    
    /**
     * Calculates the distance between this and another BlankHex.
     * @param otherHex The other BlankHex.
     * @return The distance between those two BlankHexes, in pixels.
     */
    public double distance(BlankHex otherHex) {
        return Math.sqrt(   
                (Math.pow(this.rectCoord()[0]  -  otherHex.rectCoord()[0], 2))
                +     
                (Math.pow(this.rectCoord()[1]  -  otherHex.rectCoord()[1], 2))  );
        
    }
    
    /**
     * Gets the neighbouring BlankHexes to this Hex.
     * @return the neighbouring BlankHexes to this Hex.
     */
    public HashSet<BlankHex> neighbours() {
        HashSet<BlankHex> neighbours = new HashSet<>();
        neighbours.add(blankHexAt(hexCoord[0], hexCoord[1]-1));
        neighbours.add(blankHexAt(hexCoord[0]+1, hexCoord[1]));
        neighbours.add(blankHexAt(hexCoord[0], hexCoord[1]+1));
        neighbours.add(blankHexAt(hexCoord[0]-1, hexCoord[1]));
        if(hexCoord[0]%2 == 0) {
            neighbours.add(blankHexAt(hexCoord[0]+1, hexCoord[1]-1));
            neighbours.add(blankHexAt(hexCoord[0]-1, hexCoord[1]-1));
            
        } else {
            neighbours.add(blankHexAt(hexCoord[0]+1, hexCoord[1]+1));
            neighbours.add(blankHexAt(hexCoord[0]-1, hexCoord[1]+1));
        
        }
        neighbours.remove(null);
        return neighbours;
    
    }
    
    @Override
    public void clicked() {
        
        switch (Mode.mode()) {
            case CREATE_TERRITORY :
                Selector.select(this); break;
                
            case SELECT_INFO_HEX :
                try{
                    if(Selector.getSelectedHexes().contains(this)) {
                        //Creates a Territory
                        Set<BlankHex> selectedHexes;
                        selectedHexes = Selector.getSelectedHexes();
                        Territory newTerr = new Territory(selectedHexes, this);
                        world().stateManager.map().territories.add(newTerr);
                        world().stateManager.escape();
                    }
                    
                } catch(Exception e){
                    MessageDisplayer.showMessage(e.getMessage());

                }
                break;
                
            default: break;
                
        }
        
    }
    
    /**
     * Return all the Territories from the StateManagers Map that are on the 
     * specified BlankHexes.
     * @param blankHexes The collection of BlankHexes over which to get the Territory.
     * @return All the Territories over the specified blankHexes
     * 
     */
    public static HashSet<Territory> territoriesOverHex(Collection<BlankHex> blankHexes){
        HashSet<Territory> territories = new HashSet<>();
        updateTerrsOverHexs();
        for(BlankHex bh : blankHexes) {
            territories.add(terrsOverHexs.get(bh));

        }
        territories.remove(null);
        return territories;
        
    }
    
    /**
     * Updates the HashMap of Territories over BlankHexs 
     */
    private static void updateTerrsOverHexs() {
        try {
            Map currentMap = world().stateManager.map();
            for(Territory terr : currentMap.territories) {
                for(BlankHex bh : terr.blankHexSet) {
                    terrsOverHexs.put(bh, terr);
                    
                }
                
            }
        } catch (IllegalStateException e) {
            e.printStackTrace(System.err);
            terrsOverHexs.clear();
            
        }
    
    }
     
    //Selectable methods/////////////////////////////////////

    @Override
    public void makeSelected() {
        setImage(Hexagon.createImageWBorder(Theme.used.selectionColor));
        getImage().setTransparency(Appearance.OPAQUE);
        
    }
    
    @Override
    public void makeOpaque() {
        setImage(Hexagon.createImageWBorder(Theme.used.blankHexColor));
        getImage().setTransparency(Appearance.OPAQUE);
    
    }
    
    @Override
    public void makeTransparent() {
        getImage().setTransparency(Appearance.TRANSPARENT);
    
    }
}
