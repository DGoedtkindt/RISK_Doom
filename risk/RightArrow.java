import greenfoot.GreenfootImage;

public class RightArrow extends Button {
    private final Arrowable linked;
    
    public RightArrow(Arrowable linkedObject, int xSize, int ySize) {
        this.linked = linkedObject;
        this.setImage(new GreenfootImage("rightButton.png"));
        
    }
    
    @Override
    public void clicked() {
        linked.next();
    
    }
    
}
