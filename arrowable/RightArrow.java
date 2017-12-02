package arrowable;

import base.Button;
import greenfoot.GreenfootImage;

public class RightArrow extends Button {
    private Arrowable linked;
    
    public RightArrow(Arrowable linkedObject) {
        linked = linkedObject;
        this.setImage(new GreenfootImage("rightButton.png"));
        
    }
    
    @Override
    public void clicked() {
        linked.next();
    
    }
    
}
