package base;

import greenfoot.Color;

public class GColor extends Color{
    
    public static GColor fromRGB(String rgbString) {
        String[] rgbArray = rgbString.split(",");
        int r = Integer.parseInt(rgbArray[0]);
        int g = Integer.parseInt(rgbArray[1]);
        int b = Integer.parseInt(rgbArray[2]);
        return new GColor(r,g,b);
        
    }
    
    public GColor(int r, int g, int b) {
        super(r, g, b);
    }
    
    public GColor(int r, int g, int b,int alpha) {
        super(r, g, b, alpha);
    }
    public GColor(java.awt.Color jColor) {
        super(jColor.getRed(),jColor.getGreen(),jColor.getBlue());
    
    }
    
    public String toRGB() {
        return getRed()+","+getGreen()+","+getBlue();
    
    }
    
    public java.awt.Color getAWTColor() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();
        return new java.awt.Color(r,g,b);
    
    }
    
}
