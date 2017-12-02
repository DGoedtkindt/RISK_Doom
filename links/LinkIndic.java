package links;

//un objet de cette classe indique l'existance d'un Link entre plusieur Territory
//par sa couleur

import base.Button;
import base.Hexagon;
import base.Mode;
import base.MyWorld;
import mainObjects.Territory;
import mainObjects.TerritoryHex;
import greenfoot.GreenfootImage;
import javax.swing.JOptionPane;

public class LinkIndic extends Button{
    private Links links;
    private Territory terr;
    private TerritoryHex terrHex;
    
    private MyWorld world(){return MyWorld.theWorld;}
    
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
        
        if(Mode.currentMode() == Mode.MAP_EDITOR_DEFAULT){
            
            String[] actions = new String[]{"Delete the entire link", "Delete this particular link", "Extend this link", "Oh, I just wanted to greet it"};
            
            int response = JOptionPane.showOptionDialog(null, 
                                                        "What do you want to do with this link?", 
                                                        "Performing an action on a link", 
                                                        JOptionPane.DEFAULT_OPTION, 
                                                        JOptionPane.PLAIN_MESSAGE, 
                                                        null, 
                                                        actions, 
                                                        actions[0]);
            
            switch(response){
                
                case 0 : this.links.destroy();
                         break;
                    
                case 1 : this.destroy();
                         break;
                    
                case 2 : Mode.changeMode(Mode.SET_LINK);
                         Links.newLinks = this.links;
                         break;
                
                default : world().escape(); 
                          break;
                
            }
            
        }else{
            terrHex.clicked();
        }
        
        
    
    }
    
    public void destroy() {
        terr.links.remove(this);
        links.removelink(this);
        world().removeObject(this);
    
    }
    
    public Territory linkedTerr() {
        return terr;
    
    }
}
