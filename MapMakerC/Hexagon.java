 //pour éviter les duplicates toutes les méthodes relatives a la forme des Hexagones sont ici
 import greenfoot.GreenfootImage;
 import java.awt.Color;
 
public class Hexagon  
{
    static final int HEXAGON_SIZE = 40;
    
    
    public Hexagon()
    {
    }

    
    public static GreenfootImage createHexagonImage(Color hexColor){
        
        GreenfootImage img = new GreenfootImage(2*HEXAGON_SIZE, 2*HEXAGON_SIZE);
        
        int[][] array  = getHexagonCoord(1);
        int[][] array2 = getHexagonCoord(0.95);
        
        img.setColor(Color.black);
        img.fillPolygon(array[0], array[1], 6);
        img.setColor(hexColor);
        img.fillPolygon(array2[0], array2[1], 6);
        
        return img;
        
    }
    
    
    
    
    public static int[][] getHexagonCoord(double sizeMultiplier)
    //crée les coordinées des points d'un hexagone
    {
        double rad = HEXAGON_SIZE * sizeMultiplier;
        int[][] arr = {{
            (int)rad + HEXAGON_SIZE,
            (int)(Math.cos(Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)(Math.cos(2*Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)-rad + HEXAGON_SIZE,
            (int)(-Math.cos(Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)(-Math.cos(2*Math.PI/3) * rad) + HEXAGON_SIZE
            },{
            (int)HEXAGON_SIZE,
            (int)(Math.sin(Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)(Math.sin(2*Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)HEXAGON_SIZE,
            (int)(-Math.sin(Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)(-Math.sin(2*Math.PI/3) * rad) + HEXAGON_SIZE
            }};
        return arr;
    }
    
    
    
    
    
    public static int getSize()
    {
        return HEXAGON_SIZE;
    }
    
    
    
}
