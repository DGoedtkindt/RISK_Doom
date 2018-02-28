package base;

import appearance.Theme;
import greenfoot.GreenfootImage;
 
/**
 * The class we use to produce hexagonal images.
 * 
 */
public class Hexagon {
    
    public static final int RADIUS = 32;
    
    /**
     * Creates a hexagonal image with borders.
     * @param hexColor The color of this hexagon.
     * @return The image.
     */
    public static GreenfootImage createImageWBorder(GColor hexColor){
        //draw un plus petit hex coloré sur un Hex noir
        GreenfootImage img = createImage(Theme.used.blankHexBorderColor);
        img.drawImage(createImage(hexColor, 0.95), 0, 0);
        
        return img;
        
    }
    
    /**
     * Creates a hexagonal image without borders. scaled by a factor
     * @param color The color of this hexagon.
     * @param sizeFactor the factor the hexagon will be scaled by.
     * @return The image.
     */
    public static GreenfootImage createImage(GColor color, double sizeFactor){
        //crée l'image d'un hexagone simple avec un multiplicateur de taille
        //l'image de retour reste 2*RADIUSx2*RADIUS
        GreenfootImage img = new GreenfootImage(2*RADIUS, 2*RADIUS);
        
        int[][] array  = getHexagonCoord(sizeFactor);
        
        img.setColor(color);
        img.fillPolygon(array[0], array[1], 6);
        
        return img;
        
    }
    
    /**
     * Creates a hexagonal image without borders.
     * @param color The color of this hexagon.
     * @return The image.
     */
    public static GreenfootImage createImage(GColor color){
        //crée l'image d'un hexagone simple sans multiplicateur
        return createImage(color, 1);
        
    }
    
    /**
     * Converts coordinates in our hexagonal grid to coordinates in Greenfoot's square grid.
     * @param hexCoord The Hexagonal coordinates.
     * @return The square coordinates.
     */
    public static int[] hexToRectCoord(int[] hexCoord){
        int[] converted = new int[2];  //Tableau de retour
        
        double h = Math.sin(Math.PI/3) * (Hexagon.RADIUS-1); // Hauteur d'un Hex
        
        int DECALAGE_Y = 15; // Décalage au sommet
        
        converted[0] = (Hexagon.RADIUS-1) + (int)(1.5 * hexCoord[0] * (Hexagon.RADIUS-1));
        converted[1] = DECALAGE_Y + (int)(h + 2 * hexCoord[1] * h);            //X et Y du centre
        
        if(hexCoord[0] % 2 == 1) converted[1] += h; //Décalage vertical une fois sur deux
        
        return converted;
        
    }
    
    /**
     * Creates the coordinates of the points of a Hexagon.
     * @param sizeMultiplier The relative size of this Hexagon.
     * @return The coordinates.
     */
    private static int[][] getHexagonCoord(double sizeMultiplier)
    //crée les coordonées des points d'un hexagone 
    {
        double rad = RADIUS * sizeMultiplier;
        
        int[][] arr = {
            {(int)rad + RADIUS,
            (int)(Math.cos(Math.PI/3) * rad) + RADIUS,
            (int)(Math.cos(2*Math.PI/3) * rad) + RADIUS,
            (int)-rad + RADIUS, 
            (int)(-Math.cos(Math.PI/3) * rad) + RADIUS,
            (int)(-Math.cos(2*Math.PI/3) * rad) + RADIUS} , 
            
           {(int)RADIUS,
            (int)(Math.sin(Math.PI/3) * rad) + RADIUS,
            (int)(Math.sin(2*Math.PI/3) * rad) + RADIUS,
            (int)RADIUS,
            (int)(-Math.sin(Math.PI/3) * rad) + RADIUS,
            (int)(-Math.sin(2*Math.PI/3) * rad) + RADIUS}
            };
     
        return arr;
    }
    
}
