import greenfoot.GreenfootImage;

public class LeftArrow extends Button {
    private final Arrowable linked;
    
    public LeftArrow(Arrowable linkedObject, int xSize, int ySize) {
        this.linked = linkedObject;
        this.setImage(new GreenfootImage("leftButton.png"));
        
    }
    
    @Override
    public void clicked() {
        linked.previous();
    
    }
    
}
