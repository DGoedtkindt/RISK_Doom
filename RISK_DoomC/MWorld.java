import greenfoot.World;
import greenfoot.Greenfoot;
import mapmaker.MyWorld;

public class MWorld extends World {
    public MWorld() {    
        super(100, 100, 1);
        Greenfoot.setWorld(new MyWorld());
        
    }
    
}
