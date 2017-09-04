//pour éviter les duplicates toutes les méthodes relatives a la forme des Hexagones sont ici
 import greenfoot.GreenfootImage;
 import java.awt.Color;
 
public class Hexagon  {
    static final int HEXAGON_SIZE = 40;
    
    public static GreenfootImage createImageWBorder(Color hexColor){
        
        GreenfootImage img = createImage(Color.black);
        img.drawImage(createImage(hexColor,0.95), 0, 0);
        
        return img;
        
    }

    
    public static GreenfootImage createImage(Color color, double sizeInPercent){
        GreenfootImage img = new GreenfootImage(2*HEXAGON_SIZE, 2*HEXAGON_SIZE);
        
        int[][] array  = getHexagonCoord(sizeInPercent);
        
        img.setColor(color);
        img.fillPolygon(array[0], array[1], 6);
        
        return img;
    }
    
    public static GreenfootImage createImage(Color color){
        return createImage(color, 1);
        
    }
    
    
    private static int[][] getHexagonCoord(double sizeMultiplier)
    //crée les coordinées des points d'un hexagone 
    {
        double rad = HEXAGON_SIZE * sizeMultiplier;
        
        int[][] arr = {
            {(int)rad + HEXAGON_SIZE,
            (int)(Math.cos(Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)(Math.cos(2*Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)-rad + HEXAGON_SIZE, 
            (int)(-Math.cos(Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)(-Math.cos(2*Math.PI/3) * rad) + HEXAGON_SIZE} , 
            
           {(int)HEXAGON_SIZE,
            (int)(Math.sin(Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)(Math.sin(2*Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)HEXAGON_SIZE,
            (int)(-Math.sin(Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)(-Math.sin(2*Math.PI/3) * rad) + HEXAGON_SIZE}
            };
     
        return arr;
    }
    
    
    public static int getSize(){
        return HEXAGON_SIZE;
    }
    
    
    
}
