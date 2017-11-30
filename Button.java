import greenfoot.Actor;

public abstract class Button extends Actor {
    public abstract void clicked();
    
    static final int TRANSPARENT = 30;
    static final int OPAQUE = 255;
    
    public void makeTransparent() {
        getImage().setTransparency(TRANSPARENT);
    
    }
    
    public void makeOpaque() {
        getImage().setTransparency(OPAQUE);
    
    }
    
}
