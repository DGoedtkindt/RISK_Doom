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
     * Returns a String representation of this GColor.
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
    
    /**
     * @return the luminosity of this GColor as perceived by a human eye
     */
    public int luminosity() {
        return (int)(0.21 * getRed() + 0.72 * getGreen() + 0.07 * getBlue());
    
    }
    
    /** multiplies all three composing colors by a factor
     * 
     * @param factor the factor of the multiplication
     * @return A multiplied version of this Color.
     */
    public GColor multiply(double factor) throws IllegalArgumentException {
        if(factor < 0.) throw new IllegalArgumentException("factor must be positive");
        int newRed = Math.min((int)(this.getRed()*factor),255);
        int newGreen = Math.min((int)(this.getGreen()*factor),255);
        int newBlue = Math.min((int)(this.getBlue()*factor),255);
        return new GColor(newRed, newGreen, newBlue);
    }
    
}
