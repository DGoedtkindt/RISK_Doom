import greenfoot.*; 

public class SingleHex extends Button
{
    private int[] hexCoordinate = new int[2];
    
    public int[] setHexCoord(int x, int y)
    {
        hexCoordinate[0] = x;
        hexCoordinate[1] = y;
        return hexCoordinate;
    }
    
    public int[] getHexCoord()
    {
        return hexCoordinate;
    }
    
    public int[] getRectCoord(){
        
        return null;
        
    }
    
    public void clicked(int mode){
        
        
        
    }
    
    public void act() 
    {
        //if(Greenfoot.mouseClicked(this)) {System.out.println("redfacedpinapleofjustice");}
    }    
}
