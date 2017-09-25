import java.awt.Color;
import java.util.ArrayList;

public class SingleHex extends Button implements Selectable
{
    private Coordinates coord = new Coordinates();
    
    static public Color BASE_COLOR = Color.WHITE;
    static final SingleHex[][] SINGLE_HEX_ARRAY = new SingleHex[40][20]; 
    
    public SingleHex(int xHCoord, int yHCoord) throws Exception{
        coord.hexCoord = new int[]{xHCoord,yHCoord};
        Selector.selectableSet.add(this);
        if(SINGLE_HEX_ARRAY[coord.hexCoord[0]][coord.hexCoord[1]] == null) {
            SINGLE_HEX_ARRAY[coord.hexCoord[0]][coord.hexCoord[1]] = this;
        } else {throw new Exception("Hex already exists at X: " +
                                    coord.hexCoord[0] + " and Y: " +
                                    coord.hexCoord[1] );}
        this.setImage(Hexagon.createImageWBorder(BASE_COLOR));
    }
    
    public Coordinates coordinates() {
        return coord;
    }
    
    public void clicked() {
        Mode mode = Mode.currentMode();
        if(mode == Mode.CREATE_TERRITORY){
            Selector.select(this);
            
        } else if(mode == Mode.SELECT_INFO_HEX) {
            try{
                if(Selector.getSelectedHexes().contains(this)) {
                    ArrayList<SingleHex> selectedHexes;
                    selectedHexes = Selector.getSelectedHexes();
                    new Territory(selectedHexes, this);
                    
                }
                
            } catch(Exception e){
                e.printStackTrace(System.out);
           
         }
            MyWorld.theWorld.escape();
            
        } else{
            MyWorld.theWorld.escape();
            
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
