import greenfoot.GreenfootImage;
import javax.swing.JOptionPane;

public class LinkIndic extends Button{
    private Links links;
    private Territory terr;
    
    public LinkIndic(Territory territory,int xPos,int yPos) {
        links = Links.newLinks;
        terr = territory;
        terr.links.add(this);
        
        GreenfootImage img = Hexagon.createImage(links.color());
        img.scale(10, 10);
        setImage(img);
        
        MyWorld.theWorld.addObject(this, xPos, yPos);
        
        links.addlink(this, terr);
        
    }
    
    public void clicked() {
        if(Mode.currentMode() == Mode.DEFAULT) {
            int confirmQ = JOptionPane.showConfirmDialog(
                    null,
                    "Do you want to destroy this link?",
                    "Destroy Link ?",
                    JOptionPane.YES_NO_OPTION);
            if(confirmQ == 0) {
                this.destroy();
                
            }
        
        }
    
    }
    
    public void destroy() {
        terr.links.remove(this);
        links.removelink(this);
        MyWorld.theWorld.removeObject(this);
    
    }
    
    public Territory linkedTerr() {
        return terr;
    
    }
}
