package arrowable;

import base.Button;
import greenfoot.GreenfootImage;

public class LeftArrow extends Button {
    private Arrowable linked;
    
    public LeftArrow(Arrowable linkedObject) {
        linked = linkedObject;
        this.setImage(new GreenfootImage("leftButton.png"));
        
    }
    
    
    @Override
    public void clicked() {
        linked.previous();
    
    }
    
}
