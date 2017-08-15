import java.awt.Color;

public class SingleHex extends Button
{
    private Coordinates coord = new Coordinates();
    public static SingleHex[][] array2D = new SingleHex[50][30];
    
    static public Color BASE_COLOR = Color.WHITE;
    
    public SingleHex(int xHCoord, int yHCoord)
    {
        setCoord(xHCoord,yHCoord);
        
        array2D[xHCoord][yHCoord] = this;
        
        this.setImage(Hexagon.createHexagonImage(BASE_COLOR));
    }
    
    
    public void destroy()
    {
        int[] thisCoord = coord.getHexCoord();
        array2D[thisCoord[0]][thisCoord[1]] = null;
        getWorld().removeObject(this);
    }
    
    
    private void setCoord(int x, int y)
    {
        coord.setHexCoord(new int[]{x,y});
    }
    
    
    public Coordinates getCoord()
    {
        return coord;
    }
    
    
    public void clicked(int mode){
        
        switch(mode){
            
            case Mode.SELECT_HEX : Selector.selectSingleHex(this);
                                break;
                             
            default : ((MyWorld)getWorld()).escape();
                                break;
                                
        }
        
    }
     
    public void makeGreen()
    {
        this.setImage(Hexagon.createHexagonImage(MyWorld.SELECTION_COLOR));
        this.getImage().setTransparency(MyWorld.OPAQUE);
    }
    
    public void makeOpaque()
    {
        this.setImage(Hexagon.createHexagonImage(BASE_COLOR));
        this.getImage().setTransparency(MyWorld.OPAQUE);
    }
    
    public void makeTransparent()
    {
        this.getImage().setTransparency(MyWorld.TRANSPARENT);
        this.setImage(Hexagon.createHexagonImage(BASE_COLOR));
    }
}
