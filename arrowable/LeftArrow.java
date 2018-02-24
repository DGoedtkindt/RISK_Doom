package arrowable;

import base.Button;
import greenfoot.Color;
import greenfoot.GreenfootImage;

/**
 * An Arrow linked to an Arrowable. Calls its previous() method.
 * 
 */
public class LeftArrow extends Button {
    private Arrowable linked;
    private int size = 40;
    
    /**
     * Creates a LeftArrow.
     * @param linkedObject The Arrowable linked to this Arrow.
     */
    public LeftArrow(Arrowable linkedObject) {
        linked = linkedObject;
        setImage();
        
        
    }
    
    /**
     * Creates an image for the object.
     */
    private void setImage() {
        this.setImage(new GreenfootImage(size,size));
        this.getImage().setColor(new Color(40,200,40));
        this.getImage().fillPolygon(new int[]{size,size,0}, new int[]{0,size,size/2}, 3);   
    
    }
    
    /**
     * Scales the Arrow.
     * @param newSize The length of the side of the new image.
     */
    public void scale(int newSize) {
        size = newSize;
        setImage();
    
    }

    @Override
    public void clicked() {
        linked.previous();
    
    }
    
}
