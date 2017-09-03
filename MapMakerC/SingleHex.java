import java.awt.Color;

public class SingleHex extends Button implements Selectable
{
    private Coordinates coord = new Coordinates();
    
    static public Color BASE_COLOR = Color.WHITE;
    
    public SingleHex(int xHCoord, int yHCoord)
    {
        coord.hexCoord = new int[]{xHCoord,yHCoord};
        Selector.selectableSet.add(this);
        
        this.setImage(Hexagon.createHexagonImage(BASE_COLOR));
    }
    
    public Coordinates getCoord() {
        return coord;
    }
    
    public void clicked() {
        Mode mode = Mode.currentMode();
        if(mode == Mode.CREATE_TERRITORY){
            Selector.select(this);
            
        }else{
            MyWorld.theWorld.escape();
            
        }
        
    }
     
    //Selectable methods/////////////////////////////////////
    
    public void makeGreen() {
        this.setImage(Hexagon.createHexagonImage(MyWorld.SELECTION_COLOR));
        this.getImage().setTransparency(MyWorld.OPAQUE);
    }
    
    public void makeOpaque() {
        this.setImage(Hexagon.createHexagonImage(BASE_COLOR));
        this.getImage().setTransparency(MyWorld.OPAQUE);
    }
    
    public void makeTransparent() {
        this.getImage().setTransparency(MyWorld.TRANSPARENT);
    }
}
