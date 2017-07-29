import greenfoot.*; 
import java.awt.Color;
import java.awt.geom.Area;

public class SingleHex extends Button implements Selectable
{
    private Coordinates coord = new Coordinates();
    public static SingleHex[][] array2D = new SingleHex[50][30];
    
    static public Color SELECTION_COLOR = Color.GREEN;
    static public Color BASE_COLOR = Color.WHITE;
    
    
    public SingleHex(int xHCoord, int yHCoord)
    {
        setCoord(xHCoord,yHCoord);
        
        array2D[xHCoord][yHCoord] = this;
        
        addToSelectableList();
        
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
            
            case Mode.SELECT_HEX : setImage(Hexagon.createHexagonImage(SELECTION_COLOR));
                                   Selector.selectSingleHex(this);
                                break;
                             
            default : ((MyWorld)getWorld()).escape();
                                break;
                                
        }
        
    }
     
    ////////////////////////////////////////////////////
    
    public void addToSelectableList() 
    {
        Selector.selectableList.add(this);
    }    
    
    public void setOpaque()
    {
        this.getImage().setTransparency(Selector.OPAQUE);
    }
    
    public void setTransparent()
    {
        this.getImage().setTransparency(Selector.TRANSPARENT);
    }
    
    public void setSelected()
    {
    
    }
}
