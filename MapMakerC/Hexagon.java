//pour éviter les duplicates toutes les méthodes relatives a la forme des Hexagones sont ici
 import greenfoot.GreenfootImage;
 import java.awt.Color;
 
public class Hexagon  
{
    static final int HEXAGON_SIZE = 40;
    
    public static GreenfootImage createHexagonImage(Color hexColor)
    {
        
        GreenfootImage img = createSimpleHexImage(Color.black);
        img.drawImage(createSimpleHexImage(hexColor,0.95), 0, 0);
        
        return img;
        
    }

    
    public static GreenfootImage createSimpleHexImage(Color color, double sizeInPercent)
    {
        GreenfootImage img = new GreenfootImage(2*HEXAGON_SIZE, 2*HEXAGON_SIZE);
        
        int[][] array  = format(getHexagonCoord(sizeInPercent));
        
        img.setColor(color);
        img.fillPolygon(array[0], array[1], 6);
        
        return img;
    }
    
    public static GreenfootImage createSimpleHexImage(Color color)
    {
        GreenfootImage img = new GreenfootImage(2*HEXAGON_SIZE, 2*HEXAGON_SIZE);
        
        int[][] array  = format(getHexagonCoord(1));
        
        img.setColor(color);
        img.fillPolygon(array[0], array[1], 6);
        
        return img;
    }
    
    
    public static int[][] getHexagonCoord(double sizeMultiplier)
    //crée les coordinées des points d'un hexagone
    {
        double rad = HEXAGON_SIZE * sizeMultiplier;
        
        int[][] arr = {
            {(int)rad + HEXAGON_SIZE,                                   (int)HEXAGON_SIZE},
            {(int)(Math.cos(Math.PI/3) * rad) + HEXAGON_SIZE,           (int)(Math.sin(Math.PI/3) * rad) + HEXAGON_SIZE},
            {(int)(Math.cos(2*Math.PI/3) * rad) + HEXAGON_SIZE,         (int)(Math.sin(2*Math.PI/3) * rad) + HEXAGON_SIZE},
            {(int)-rad + HEXAGON_SIZE,                                  (int)HEXAGON_SIZE},
            {(int)(-Math.cos(Math.PI/3) * rad) + HEXAGON_SIZE,          (int)(-Math.sin(Math.PI/3) * rad) + HEXAGON_SIZE},
            {(int)(-Math.cos(2*Math.PI/3) * rad) + HEXAGON_SIZE,        (int)(-Math.sin(2*Math.PI/3) * rad) + HEXAGON_SIZE}
            };

            
        return arr;
    }
    
    public static int[][] getHexagonCoord(double sizeMultiplier, Coordinates coord)
    //crée les coordinées des points d'un hexagone
    {
        double rad = HEXAGON_SIZE * sizeMultiplier;
        int[] rectCoord = coord.getRectCoord();
        
        int[][] arr = {
            {(int)rad + rectCoord[0],                                   (int)rectCoord[1]},
            {(int)(Math.cos(Math.PI/3) * rad) + rectCoord[0],           (int)(Math.sin(Math.PI/3) * rad) + rectCoord[1]},
            {(int)(Math.cos(2*Math.PI/3) * rad) + rectCoord[0],         (int)(Math.sin(2*Math.PI/3) * rad) + rectCoord[1]},
            {(int)-rad + rectCoord[0],                                  (int)rectCoord[1]},
            {(int)(-Math.cos(Math.PI/3) * rad) + rectCoord[0],          (int)(-Math.sin(Math.PI/3) * rad) + rectCoord[1]},
            {(int)(-Math.cos(2*Math.PI/3) * rad) + rectCoord[0],        (int)(-Math.sin(2*Math.PI/3) * rad) + rectCoord[1]}
            };

            
        return arr;
    }
    
    public static int[][] format(int[][] toFormat)
    //transforme un array [x][2] en [2][x], format pour le constructeur de Polygon
    {
        int[][] formated = new int[2][6];
        
        for(int i = 0; i < toFormat.length; i++){
            formated[0][i] = toFormat[i][0];
            formated[1][i] = toFormat[i][1];
        }
        
        return formated;
    }
    
    
    
    public static int getSize()
    {
        return HEXAGON_SIZE;
    }
    
    
    
}
