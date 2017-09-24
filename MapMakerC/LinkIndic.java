import greenfoot.GreenfootImage;
import javax.swing.JOptionPane;

public class LinkIndic extends Button{
    private Links links;
    private Territory terr;
    
    public LinkIndic(Territory territory) {
        links = Links.newLinks;
        terr = territory;
        
        GreenfootImage img = new GreenfootImage(16,16);
        img.setColor(links.color());
        img.fillOval(0, 0, 16, 16);
        this.setImage(img);
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
    
    }
}
