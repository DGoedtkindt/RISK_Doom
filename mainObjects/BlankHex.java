package mainObjects;

import mode.Mode;
import selector.Selector;
import selector.Selectable;
import appearance.Appearance;
import appearance.MessageDisplayer;
import appearance.Theme;
import base.Button;
import base.Hexagon;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Blank Hexes are the basic Hexagins of a Map. 
 * They represent a spot where a part of a Territory can be.
 * 
 */
public class BlankHex extends Button implements Selectable{
    public static final int MAX_COLUMN = 40;
    public static final int MAX_ROW = 40;
    
    private static final BlankHex[][] BLANK_HEX_ARRAY = new BlankHex[MAX_COLUMN][MAX_ROW];
    public static final HashSet<BlankHex> BLANK_HEXS = new HashSet<>();
    
    private int[] hexCoord = new int[2];
    
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
     */
    public static BlankHex blankHexAt(int hexX, int hexY){
        BlankHex returnHex;
        if(BLANK_HEX_ARRAY[hexX][hexY] != null) returnHex = BLANK_HEX_ARRAY[hexX][hexY];
            else returnHex = new BlankHex(hexX,hexY);
        return returnHex;
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
     * Gets the hexagonal coordinates of a BlankHex.
     * @return The hexagonal coordinates of this BlankHex.
     */
    public int[] hexCoord() {
        return hexCoord;
    }
    
    /**
     * Gets the rectangular, normal coordinates of a BlankHex.
     * @return The rectangular, normal coordinates of this BlankHex.
     */
    public int[] rectCoord() {
        return Hexagon.hexToRectCoord(hexCoord);
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
                        ArrayList<BlankHex> selectedHexes;
                        selectedHexes = Selector.getSelectedHexes();
                        Territory newTerr = new Territory(selectedHexes, this,0);
                        newTerr.addToWorld();
                        world().stateManager.escape();
                    }
                    
                } catch(Exception e){
                    MessageDisplayer.showMessage(e.getMessage());

                }
                break;
                
            default: break;
                
        }
        
    }
     
    //Selectable methods/////////////////////////////////////

    @Override
    public void makeGreen() {
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
