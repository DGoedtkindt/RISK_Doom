package basepackage;

import java.awt.Color;

/**
 *
 */
public abstract class BlankHex extends Button{
    private static final Color BASE_COLOR = Color.BLACK;
    protected int[] hexCoord = new int[2];
    
    protected BlankHex(int xHCoord, int yHCoord) {
        hexCoord = new int[]{xHCoord,yHCoord};
        this.setImage(Hexagon.createImageWBorder(BASE_COLOR));
    }
    
    public int[] hexCoord() {
        return hexCoord;
    }
    
    public int[] rectCoord() {
        return Hexagon.hexToRectCoord(hexCoord);
    }
    
}
