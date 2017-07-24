import greenfoot.*; 
import java.awt.Color;
import java.awt.geom.Area;

public class SingleHex extends Button implements Maskable
{
    private Coordinates coord = new Coordinates();
    
    
    
    public SingleHex(int xHCoord, int yHCoord)
    {
        setCoord(xHCoord,yHCoord);
        
        this.setImage(Hexagon.createHexagonImage(Color.WHITE));
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
            
            case Mode.SELECT_HEX : ((MyWorld)getWorld()).selectSingleHex(this);
                                break;
                             
                                
            default : ((MyWorld)getWorld()).escape();
                                break;
                                
                                
        }
        
        
    }
    
    
    public Area getAreaShape()
    {
        return null;
    }
    
    
    public void act() 
    {
        
    }    
    
    
    
}
