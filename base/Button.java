package base;

import greenfoot.Actor;

public abstract class Button extends Actor {
    public abstract void clicked();
    
    public void makeTransparent() {
        getImage().setTransparency(Appearance.TRANSPARENT);
    
    }
    
    public void makeOpaque() {
        getImage().setTransparency(Appearance.OPAQUE);
    
    }
    
}
