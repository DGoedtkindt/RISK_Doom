//pour éviter les duplicates, les méthodes relatives aux coordonées Hexagonales/Rectangles sont ici

public class Coordinates  
{
    private int[] hexCoord = new int[2];
    
    
    
    public Coordinates() {}
    
    
    
    
    public static int[] hexToRectCoord(int[] hexCoord)
    {
        int[] converted = new int[2];  //Tableau de retour
        
        double h = Math.sin(Math.PI/3) * (Hexagon.HEXAGON_SIZE-1); // Taille d'un Hex
        
        int DECALAGE_Y = 15; // Décalage au sommet
        
        converted[0] = (Hexagon.HEXAGON_SIZE-1) + (int)(1.5 * hexCoord[0] * (Hexagon.HEXAGON_SIZE-1));
        converted[1] = DECALAGE_Y + (int)(h + 2 * hexCoord[1] * h);                                         //X et Y du centre
        
        if(hexCoord[0] % 2 == 1) converted[1] += h; //Décalage vertical une fois sur deux
        
        return converted;
    }
    
    
    
    
    public void setHexCoord(int[] CoordToSet)
    {
        this.hexCoord = CoordToSet;
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
