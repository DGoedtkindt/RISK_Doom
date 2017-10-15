import greenfoot.World;
import greenfoot.Greenfoot;
import firstmenu.FirstMenu;

public class MyWorld extends World {
    public MyWorld() {    
        super(100, 100, 1);
        Greenfoot.setWorld(new FirstMenu());
        
    }
    
}
