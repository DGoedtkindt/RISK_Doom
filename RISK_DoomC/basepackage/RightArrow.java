package basepackage;



public class RightArrow extends Button {
    private final Arrowable linked;
    
    public RightArrow(Arrowable linked, int xSize, int ySize) {
        this.linked = linked;
        
    }
    
    @Override
    public void clicked() {
        linked.previous();
    
    }
    
}
