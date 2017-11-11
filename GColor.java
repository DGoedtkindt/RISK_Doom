import greenfoot.Color;

public class GColor extends Color{
    
    public static GColor decode(String colorCode) {
        int code = Integer.parseInt(colorCode);
        int b = code % (256);
        int g = ((code - b)/256) % 257;
        int r = ((code - b - g*256)/(257*256));
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
    
    public String encode() {
        return 257*256*this.getRed()+256*this.getGreen()+this.getBlue() + "";
    
    }
    
    public java.awt.Color getAWTColor() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();
        return new java.awt.Color(r,g,b);
    
    }
    
}
