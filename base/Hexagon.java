package base;

import appearance.Theme;
import greenfoot.Color;
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
    public static GreenfootImage createImageWBorder(Color hexColor){
        //Draws a small hexagon on a bigger one
        GreenfootImage img = createImage(Theme.used.blankHexBorderColor);
        img.drawImage(createImage(hexColor, 0.95), 0, 0);
        
        return img;
        
    }
    
    /**
     * Creates a hexagonal image without borders, scaled by some factor.
     * @param color The color of this hexagon.
     * @param sizeFactor The factor the hexagon will be scaled by.
     * @return The image.
     */
    public static GreenfootImage createImage(Color color, double sizeFactor){
        
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
    public static GreenfootImage createImage(Color color){
        //Creates a simple hexagon image
        return createImage(color, 1);
        
    }
    
    /**
     * Converts coordinates in our hexagonal grid to coordinates in Greenfoot's square grid.
     * @param hexCoord The Hexagonal coordinates.
     * @return The square coordinates.
     */
    public static int[] hexToRectCoord(int[] hexCoord){
        int[] converted = new int[2];  //Returned table
        
        double h = Math.sin(Math.PI/3) * (Hexagon.RADIUS-1); //Height of a hexagon
        
        int yShift = 15; //The shift in the y coordinate to the summit of the image
        
        //The center's x and y coordinates
        converted[0] = (Hexagon.RADIUS - 1) + (int)(1.5 * hexCoord[0] * (Hexagon.RADIUS - 1));
        converted[1] = yShift + (int)(h + 2 * hexCoord[1] * h);
        
        if(hexCoord[0] % 2 == 1) converted[1] += h; //Adds a shift in the y coordinate for every other hexagon
        
        return converted;
        
    }
    
    /**
     * Creates the coordinates of the points of a Hexagon.
     * @param sizeMultiplier The relative size of this Hexagon.
     * @return The coordinates.
     */
    private static int[][] getHexagonCoord(double sizeMultiplier){
        
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
