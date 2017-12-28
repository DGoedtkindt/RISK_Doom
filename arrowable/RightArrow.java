package arrowable;

import base.Button;
import greenfoot.Color;
import greenfoot.GreenfootImage;

public class RightArrow extends Button {
    private Arrowable linked;
    private int size = 40;
    
    public RightArrow(Arrowable linkedObject) {
        linked = linkedObject;
        setImage();
        
        
    }
    
    private void setImage() {
        this.setImage(new GreenfootImage(size,size));
        this.getImage().setColor(new Color(40,200,40));
        this.getImage().fillPolygon(new int[]{0,0,size}, new int[]{0,size,size/2}, 3);   
    
    }
    
    public void scale(int newSize) {
        size = newSize;
        setImage();
    
    }

    @Override
    public void clicked() {
        linked.next();
    
    }
    
}
