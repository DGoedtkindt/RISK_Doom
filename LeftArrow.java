import greenfoot.GreenfootImage;

public class LeftArrow extends Button {
    private Arrowable linked;
    
    public LeftArrow(Arrowable linkedObject) {
        changeLinkedArrowable(linkedObject);
        this.setImage(new GreenfootImage("leftButton.png"));
        
    }
    
    public void changeLinkedArrowable(Arrowable linkedObject){
        linked = linkedObject;
    }
    
    @Override
    public void clicked() {
        linked.previous();
    
    }
    
}
