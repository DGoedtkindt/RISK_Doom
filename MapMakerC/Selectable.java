import java.awt.geom.Area;

public interface Selectable  
{

    public void setTransparent(); 
    
    public void setOpaque();
    
    public void setSelected();
    
    public void addToSelectableList();
}
