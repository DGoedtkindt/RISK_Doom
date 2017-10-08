import java.awt.Color;
import java.util.ArrayList;

public class BlankHex extends Button implements Selectable
{
    private Coordinates coord = new Coordinates();
    
    static public Color BASE_COLOR = Color.BLACK;
    private static final BlankHex[][] BLANK_HEX_ARRAY = new BlankHex[40][20]; 
    
    private static MyWorld world() {return MyWorld.theWorld;}
    
    public static BlankHex blankHexAt(int hexX, int hexY) 
    //this method makes sure we only create new BlankHex when necessary  
    {
        BlankHex returnHex;
        if(BLANK_HEX_ARRAY[hexX][hexY] != null) returnHex = BLANK_HEX_ARRAY[hexX][hexY];
            else returnHex = new BlankHex(hexX,hexY);
        return returnHex;
    }
    
    private BlankHex(int xHCoord, int yHCoord) {
        coord.hexCoord = new int[]{xHCoord,yHCoord};
        Selector.selectableSet.add(this);
        if(BLANK_HEX_ARRAY[coord.hexCoord[0]][coord.hexCoord[1]] == null) {
           BLANK_HEX_ARRAY[coord.hexCoord[0]][coord.hexCoord[1]] = this;
        } 
        this.setImage(Hexagon.createImageWBorder(BASE_COLOR));
    }
    
    public Coordinates coordinates() {
        return coord;
    }
    
    public void clicked() {
        Mode mode = Mode.currentMode();
        
        switch (mode) {
            case CREATE_TERRITORY :
                Selector.select(this);
                break;
                
            case SELECT_INFO_HEX :
                try{
                    if(Selector.getSelectedHexes().contains(this)) {
                        ArrayList<BlankHex> selectedHexes;
                        selectedHexes = Selector.getSelectedHexes();
                        new Territory(selectedHexes, this);
                        world().escape();
                        
                    }
                    
                } catch(Exception e){
                    e.printStackTrace(System.err);
                    world().escape();
                }
                break;
                
            default:
                world().escape();
                break;
        }
        
    }
     
    //Selectable methods/////////////////////////////////////
    
    public void makeGreen() {
        this.setImage(Hexagon.createImageWBorder(MyWorld.SELECTION_COLOR));
        this.getImage().setTransparency(MyWorld.OPAQUE);
    }
    
    public void makeOpaque() {
        this.setImage(Hexagon.createImageWBorder(BASE_COLOR));
        this.getImage().setTransparency(MyWorld.OPAQUE);
    }
    
    public void makeTransparent() {
        this.getImage().setTransparency(MyWorld.TRANSPARENT);
    }
}
