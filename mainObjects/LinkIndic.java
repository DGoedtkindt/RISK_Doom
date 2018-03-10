package mainObjects;

import appearance.MessageDisplayer;
import base.Button;
import base.Hexagon;
import base.MyWorld;
import mode.Mode;
import greenfoot.GreenfootImage;
import javax.swing.JOptionPane;

/**
 * This Class is an Object that represents a Link between Territories.
 * 
 */
public class LinkIndic extends Button{
    public Links links;
    private Territory terr;
    private TerritoryHex terrHex;
    private int xPos;
    private int yPos; 
    
    /**
     * Creates a LinkIndic.
     * @param territory The Territory owning this LinkIndic.
     * @param xPos The x coordinate of this Actor.
     * @param yPos The y coordinate of this Actor.
     */
    public LinkIndic(Territory territory, int xPos, int yPos) {
        //le lier au Links actif et a son territoire
        links = Links.newLinks;
        terr = territory;
        terr.links.add(this);
        links.addlink(this, terr);
        
        //créer son image
        GreenfootImage img = Hexagon.createImage(links.color());
        img.scale(15, 15);
        setImage(img);
        
        //stoquer la position
        this.xPos = xPos;
        this.yPos = yPos;
        
    }
    
    @Override
    public void clicked() {
        
        if(Mode.mode() == Mode.MAP_EDITOR_DEFAULT){
            
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
                    
                case 2 : Mode.setMode(Mode.SET_LINK);
                         Links.newLinks = this.links;
                         break;
                
                default : break;
                
            }
            
        }else{
            terrHex.clicked();
        }
    
    }
    
    /**
     * Adds this LinkIndic to the World and verifies the existence of a TerritoryHex below it.
     */
    public void addToWorld() {
        world().addObject(this, xPos, yPos);
        
        try{
            terrHex = MyWorld.theWorld.getObjectsAt(xPos, yPos, TerritoryHex.class).get(0);
        } catch(IndexOutOfBoundsException e){
            MessageDisplayer.showMessage("The new LinkIndic didn't find a TerritoryHex at this position.");
            this.destroy();
        }
        
    }
    
    /**
     * Destroys this LinkIndic.
     */
    public void destroy() {
        terr.links.remove(this);
        links.removelink(this);
        world().removeObject(this);
    
    }
    
    /**
     * Gets the Territory that owns this LinkIndic.
     * @return The Territory that owns it.
     */
    public Territory linkedTerr() {
        return terr;
    
    }
}
