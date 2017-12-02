package arrowable;

import base.Button;
import greenfoot.GreenfootImage;

public class LeftArrow extends Button {
    private final Arrowable linked;
    
    public LeftArrow(Arrowable linkedObject, int xSize, int ySize) {
        this.linked = linkedObject;
        this.setImage(new GreenfootImage("leftButton.png"));
        this.getImage().scale(xSize, ySize);
        
    }
    
    public LeftArrow(Arrowable linkedObject) {
        this.linked = linkedObject;
        this.setImage(new GreenfootImage("leftButton.png"));
        
    }
    
    @Override
    public void clicked() {
        linked.previous();
    
    }
    
}
