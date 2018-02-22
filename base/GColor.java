package base;

import greenfoot.Color;

/**
 * Color class replacing the greenfoot.Color class in our code.
 * 
 */
public class GColor extends Color{
    
    /**
     * Creates a GColor with red, green and blue values.
     * @param r The red value.
     * @param g The green value.
     * @param b The blue value.
     */
    public GColor(int r, int g, int b) {
        super(r, g, b);
    }
    
    /**
     * Creates a GColor with red, green, blue and alpha values.
     * @param r The red value.
     * @param g The green value.
     * @param b The blue value.
     * @param alpha The alpha value.
     */
    public GColor(int r, int g, int b, int alpha) {
        super(r, g, b, alpha);
    }
    
    /**
     * creates a GColor from a java.awt.Color.
     * @param jColor The java.awt.Color to transform.
     */
    public GColor(java.awt.Color jColor) {
        super(jColor.getRed(), jColor.getGreen(), jColor.getBlue());
    }
    
    /**
     * Returns a String representation of a GColor.
     * @return A String representing this GColor.
     */
    public String toRGB() {
        return getRed() + "," + getGreen() + "," + getBlue();
    }
    
    /**
     * Creates a GColor from a String representation.
     * @param rgbString A String representing a GColor.
     * @return The created GColor.
     */
    public static GColor fromRGB(String rgbString) {
        String[] rgbArray = rgbString.split(",");
        int r = Integer.parseInt(rgbArray[0]);
        int g = Integer.parseInt(rgbArray[1]);
        int b = Integer.parseInt(rgbArray[2]);
        return new GColor(r,g,b);
        
    }
    
    /**
     * Converts a GColor into a java.awt.Color.
     * @return The created java.awt.Color.
     */
    public java.awt.Color getAWTColor() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();
        return new java.awt.Color(r,g,b);
    
    }
    
}
