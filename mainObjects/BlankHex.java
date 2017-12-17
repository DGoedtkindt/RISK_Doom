package mainObjects;

import selector.Selector;
import selector.Selectable;
import appearance.Appearance;
import appearance.Theme;
import base.*;
import java.util.ArrayList;
import java.util.HashSet;

public class BlankHex extends Button implements Selectable{
    
    private static final BlankHex[][] BLANK_HEX_ARRAY = new BlankHex[40][20];
    public static final HashSet<BlankHex> BLANK_HEXS = new HashSet<>();
    
    private int[] hexCoord = new int[2];
    
    private static MyWorld world() {return MyWorld.theWorld;}
    
    private BlankHex(int xHCoord, int yHCoord) {
        hexCoord = new int[]{xHCoord,yHCoord};
        this.setImage(Hexagon.createImageWBorder(Theme.used.blankHexColor));
        BLANK_HEXS.add(this);
        BLANK_HEX_ARRAY[hexCoord[0]][hexCoord[1]] = this;
    }
    
    public static BlankHex blankHexAt(int hexX, int hexY) 
    //this method makes sure we only create new BlankHex when necessary  
    {
        BlankHex returnHex;
        if(BLANK_HEX_ARRAY[hexX][hexY] != null) returnHex = BLANK_HEX_ARRAY[hexX][hexY];
            else returnHex = new BlankHex(hexX,hexY);
        return returnHex;
    }
    
    public int[] hexCoord() {
        return hexCoord;
    }
    
    public int[] rectCoord() {
        return Hexagon.hexToRectCoord(hexCoord);
    }
    
    @Override
    public void clicked() {
        Mode mode = Mode.currentMode();
        
        switch (mode) {
            case CREATE_TERRITORY :
                Selector.select(this); break;
                
            case SELECT_INFO_HEX :
                try{
                    if(Selector.getSelectedHexes().contains(this)) {
                        //créer un territoire
                        ArrayList<BlankHex> selectedHexes;
                        selectedHexes = Selector.getSelectedHexes();
                        Territory newTerr = new Territory(selectedHexes, this,0);   
                        newTerr.addToWorld();
                        
                    }
                    
                } catch(Exception e){
                    System.err.println(e);

                }
                world().escape(); break;
                
            default:
                world().escape();
                break;
                
        }
        
    }
     
    //Selectable methods/////////////////////////////////////

    @Override
    public void makeGreen() {
        this.setImage(Hexagon.createImageWBorder(Theme.used.selectionColor));
        this.getImage().setTransparency(Appearance.OPAQUE);
        
    }
    
    @Override
    public void makeOpaque() {
    this.setImage(Hexagon.createImageWBorder(Theme.used.blankHexColor));
    this.getImage().setTransparency(Appearance.OPAQUE);
    
    }
    
    @Override
    public void makeTransparent() {
    this.getImage().setTransparency(Appearance.TRANSPARENT);
    
    }
}
