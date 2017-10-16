package basepackage;




public class LeftArrow extends Button {
    private final Arrowable linked;
    
    public LeftArrow(Arrowable linked, int xSize, int ySize) {
        this.linked = linked;
        
    }
    
    @Override
    public void clicked() {
        linked.previous();
    
    }
    
}
