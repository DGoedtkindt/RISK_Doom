import greenfoot.GreenfootImage;

public class RightArrow extends Button {
    private Arrowable linked;
    
    public RightArrow(Arrowable linkedObject) {
        changeLinkedArrowable(linkedObject);
        this.setImage(new GreenfootImage("rightButton.png"));
        
    }
    
    public void changeLinkedArrowable(Arrowable linkedObject){
        linked = linkedObject;
    }
    
    @Override
    public void clicked() {
        linked.next();
    
    }
    
}
