import java.util.ArrayList;

public class BlankHex extends Button implements Selectable{
    
    private static final GColor BASE_COLOR = new GColor(110,80,110);
    private static final BlankHex[][] BLANK_HEX_ARRAY = new BlankHex[40][20]; 
    
    private int[] hexCoord = new int[2];
    
    private static MyWorld world() {return MyWorld.theWorld;}
    
    private BlankHex(int xHCoord, int yHCoord) {
        hexCoord = new int[]{xHCoord,yHCoord};
        this.setImage(Hexagon.createImageWBorder(BASE_COLOR));
        Selector.selectableSet.add(this);
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
                        new Territory(selectedHexes, this);
                        
                    }
                    
                } catch(Exception e){
                    System.err.println(e.getMessage());

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
