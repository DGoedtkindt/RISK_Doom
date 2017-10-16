package mapmaker;

//un objet de cette classe indique l'existance d'un Link entre plusieur Territory
//par sa couleur

import greenfoot.GreenfootImage;
import javax.swing.JOptionPane;

public class LinkIndic extends Button{
    private Links links;
    private Territory terr;
    private TerritoryHex terrHex;
    
    public LinkIndic(Territory territory, int xPos, int yPos) {
        //le lier au Links actif et a son territoire
        links = Links.newLinks;
        terr = territory;
        terr.links.add(this);
        links.addlink(this, terr);
        
        //créer son image et rajouter au monde
        GreenfootImage img = Hexagon.createImage(links.color());
        img.scale(10, 10);
        setImage(img);
        MyWorld.theWorld.addObject(this, xPos, yPos);
        
        //pour que quand on clique dessus hors du mode DEFAULT il fasse l'action
        //du TerritoryHex en dessous de lui
        try{
        terrHex = MyWorld.theWorld.getObjectsAt(xPos, yPos, TerritoryHex.class).get(0);
        } catch(IndexOutOfBoundsException e){
            System.err.println("new LinkIndic didn't find a terrHex at this position");
            this.destroy();
        }
    }
    
    @Override
    public void clicked() {
        if(Mode.currentMode() == Mode.DEFAULT) {
            int confirmQ = JOptionPane.showConfirmDialog(
                    null,
                    "Do you want to destroy this link?",
                    "Destroy Link ?",
                    JOptionPane.YES_NO_OPTION);
            if(confirmQ == JOptionPane.YES_OPTION) {
                this.destroy();
                
            }
        
        }else{
            terrHex.clicked();
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
