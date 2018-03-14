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
        GreenfootImage outerHex = createImage(Theme.used.blankHexBorderColor);
        GreenfootImage innerHex = createImage(hexColor, 0.95);
        int offset = (int)0.05*RADIUS;
        outerHex.drawImage(innerHex, offset, offset);
        
        return outerHex;
        
    }
    
    /**
     * Creates a hexagonal image without borders. scaled by a factor
     * @param color The color of this hexagon.
     * @param sizeFactor the factor the hexagon will be scaled by.
     * @return The image.
     */
    public static GreenfootImage createImage(GColor color, double sizeFactor){
        //crée l'image d'un hexagone simple avec un multiplicateur de taille
        GreenfootImage img = new GreenfootImage((int)(2*RADIUS*sizeFactor), (int)(2*RADIUS*sizeFactor));
        
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
            {(int)(2*rad),
            (int)(Math.cos(Math.PI/3) * rad + rad),
            (int)(Math.cos(2*Math.PI/3) * rad + rad),
            (int)0, 
            (int)(-Math.cos(Math.PI/3) * rad + rad),
            (int)(-Math.cos(2*Math.PI/3) * rad + rad)} , 
            
           {(int)rad,
            (int)(Math.sin(Math.PI/3) * rad + rad),
            (int)(Math.sin(2*Math.PI/3) * rad + rad),
            (int)rad,
            (int)(-Math.sin(Math.PI/3) * rad + rad),
            (int)(-Math.sin(2*Math.PI/3) * rad + rad)}
            };
     
        return arr;
    }
    
}
