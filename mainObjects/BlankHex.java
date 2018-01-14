package mainObjects;

import mode.Mode;
import selector.Selector;
import selector.Selectable;
import appearance.Appearance;
import appearance.Theme;
import base.*;
import java.util.ArrayList;
import java.util.HashSet;

public class BlankHex extends Button implements Selectable{
    public static final int maxNColumn = 40;
    public static final int maxNRow = 40;
    
    private static final BlankHex[][] BLANK_HEX_ARRAY = new BlankHex[maxNColumn][maxNRow];
    public static final HashSet<BlankHex> BLANK_HEXS = new HashSet<>();
    
    private int[] hexCoord = new int[2];
    
    private static MyWorld world() {return MyWorld.theWorld;}
    
    private BlankHex(int xHCoord, int yHCoord) {
        hexCoord = new int[]{xHCoord,yHCoord};
        setImage();
        BLANK_HEXS.add(this);
        BLANK_HEX_ARRAY[hexCoord[0]][hexCoord[1]] = this;
    }
    
    private void setImage() {
        this.setImage(Hexagon.createImageWBorder(Theme.used.blankHexColor));
        
    }
    
    public static BlankHex blankHexAt(int hexX, int hexY) 
    //this method makes sure we only create new BlankHex when necessary  
    {
        BlankHex returnHex;
        if(BLANK_HEX_ARRAY[hexX][hexY] != null) returnHex = BLANK_HEX_ARRAY[hexX][hexY];
            else returnHex = new BlankHex(hexX,hexY);
        return returnHex;
    }
    
    public static void updateAllImages() {
        for(int x = 0; x < maxNColumn; x++) {
            for(int y = 0; y < maxNRow; y++) {
                blankHexAt(x,y).setImage();
                
            }
            
        }
    
    }
    
    public int[] hexCoord() {
        return hexCoord;
    }
    
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
                        //créer un territoire
                        ArrayList<BlankHex> selectedHexes;
                        selectedHexes = Selector.getSelectedHexes();
                        Territory newTerr = new Territory(selectedHexes, this,0);   
                        newTerr.addToWorld();
                        
                    }
                    
                } catch(Exception e){
                    System.err.println(e);

                }
                world().stateManager.escape();; break;
                
            default:
                world().stateManager.escape();;
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
