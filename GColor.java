import greenfoot.Color;

/**
 *
 * @author Dario
 */
public class GColor extends Color{
    
    public static GColor fromRGB(String rgb) {
        int r = Integer.parseInt(rgb.substring(1, 3));
        int g = Integer.parseInt(rgb.substring(4, 6));
        int b = Integer.parseInt(rgb.substring(7, 9));
        return new GColor(r,g,b);
        
    }
    
    public GColor(int r, int g, int b) {
        super(r, g, b);
    }
 
    public GColor(java.awt.Color jColor) {
        super(jColor.getRed(),jColor.getGreen(),jColor.getBlue());
    
    }
    
    public String toRGB() {
        return 1000000*this.getRed()+1000*this.getGreen()+this.getBlue() + "";
    
    }
    
    public java.awt.Color getAWTColor() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();
        return new java.awt.Color(r,g,b);
    
    }
    
}
