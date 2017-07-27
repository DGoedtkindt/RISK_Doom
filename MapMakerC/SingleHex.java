import greenfoot.*; 
import java.awt.Color;
import java.awt.geom.Area;

public class SingleHex extends Button implements Maskable
{
    private Coordinates coord = new Coordinates();
    public static SingleHex[][] array2D = new SingleHex[50][30];
    
    
    public SingleHex(int xHCoord, int yHCoord)
    {
        setCoord(xHCoord,yHCoord);
        
        array2D[xHCoord][yHCoord] = this;
        
        this.setImage(Hexagon.createHexagonImage(Color.WHITE));
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
