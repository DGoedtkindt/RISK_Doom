package mapmaker;

import basepackage.Hexagon;
import java.awt.Color;
import java.util.ArrayList;
import basepackage.BlankHex;

public class BlankHexMM extends BlankHex implements Selectable
{
    
    
    private static final Color BASE_COLOR = Color.BLACK;
    private static final BlankHexMM[][] BLANK_HEX_ARRAY = new BlankHexMM[40][20]; 
    
    private static MyWorld world() {return MyWorld.theWorld;}
    
    public static BlankHexMM blankHexAt(int hexX, int hexY) 
    //this method makes sure we only create new BlankHex when necessary  
    {
        BlankHexMM returnHex;
        if(BLANK_HEX_ARRAY[hexX][hexY] != null) returnHex = BLANK_HEX_ARRAY[hexX][hexY];
            else returnHex = new BlankHexMM(hexX,hexY);
        return returnHex;
    }
    
    private BlankHexMM(int xHCoord, int yHCoord) {
        super(xHCoord,yHCoord);
        Selector.selectableSet.add(this);
        BLANK_HEX_ARRAY[hexCoord[0]][hexCoord[1]] = this;
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
                        ArrayList<BlankHexMM> selectedHexes;
                        selectedHexes = Selector.getSelectedHexes();
                        new Territory(selectedHexes, this);
                        
                    }
                    
                } catch(Exception e){
                    System.err.println("Territory couldn't be created");

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
        this.setImage(Hexagon.createImageWBorder(MyWorld.SELECTION_COLOR));
        this.getImage().setTransparency(MyWorld.OPAQUE);
    }
    
    @Override
    public void makeOpaque() {
        this.setImage(Hexagon.createImageWBorder(BASE_COLOR));
        this.getImage().setTransparency(MyWorld.OPAQUE);
    }
    
    @Override
    public void makeTransparent() {
        this.getImage().setTransparency(MyWorld.TRANSPARENT);
    }
}
