import greenfoot.*; 
import java.awt.Color;

public class SingleHex extends Button
{
    private Coordinates coord = new Coordinates();
    
    
    
    public SingleHex(int xHCoord, int yHCoord)
    {
        setCoord(xHCoord,yHCoord);
        GreenfootImage img = new GreenfootImage(2*Hexagon.getSize(), 2*Hexagon.getSize());
        
        int[][] array = Hexagon.getHexagonCoord(1);
        int[][] array2 = Hexagon.getHexagonCoord(0.95);
        
        img.setColor(Color.black);
        img.fillPolygon(array[0], array[1], 6);
        img.setColor(Color.white);
        img.fillPolygon(array2[0], array2[1], 6);
        
        this.setImage(img);
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
                            
            case Mode.SELECT_HEX : ((MyWorld)getWorld()).selectHex(this);
                                   getWorld().addObject(new TerritoryHex(), coord.getHexCoord()[0], coord.getHexCoord()[1]);
                                   getWorld().removeObject(this);
                                break;
                                
                                
            default : ((MyWorld)getWorld()).escape();
                                break;
                                
                                
        }
        
        
    }
    
    
    
    
    
    public void act() 
    {
        
    }    
    
    
    
}
