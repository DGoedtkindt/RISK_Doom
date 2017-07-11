//pour éviter les duplicates, les méthodes relatives aux coordonées Hexagonales/Rectangles sont ici

public class Coordinates  
{
    private int[] hexCoord = new int[2];
    
    public Coordinates() {}
    
    public static int[] hexToRectCoord(int[] hexCoord)
    {
        int[] converted = new int[2];
        double h = Math.sin(Math.PI/3) * (Hexagon.HEXAGON_SIZE-1);
        int DECALAGE_Y = 15;
        converted[0] = (Hexagon.HEXAGON_SIZE-1) + (int)(1.5 * hexCoord[0] * (Hexagon.HEXAGON_SIZE-1));
        converted[1] = DECALAGE_Y + (int)(h + 2 * hexCoord[1] * h);
        if(hexCoord[0] % 2 == 1) converted[1] += h;
        
        return converted;
    }
    
    public void setHexCoord(int[] hexCoord)
    {
        this.hexCoord = hexCoord;
    }
    
    public int[] getHexCoord()
    {
        return hexCoord;
    }
    
    public int[] getRectCoord()
    {
        return  hexToRectCoord(hexCoord);
    }
}
