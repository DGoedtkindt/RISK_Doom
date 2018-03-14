package mainObjects;

import appearance.Appearance;
import appearance.MessageDisplayer;
import base.Button;
import base.Hexagon;
import base.MyWorld;
import base.NButton;
import mode.Mode;
import greenfoot.GreenfootImage;

/**
 * This Class is an Object that represents a Link between Territories.
 * 
 */
public class LinkIndic extends Button{
    
    public static NButton destroyLink = new NButton(() -> {});
    public static NButton destroySpot = new NButton(() -> {});
    public static NButton extendLink = new NButton(() -> {});
    public static NButton nothing = new NButton(() -> {});
    
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
        
        if(Mode.mode() == Mode.DEFAULT){
            destroyLink = new NButton(() -> {
                world().removeObject(nothing);
                world().removeObject(destroyLink);
                world().removeObject(destroySpot);
                world().removeObject(extendLink);
                links.destroy();
                Mode.setMode(Mode.DEFAULT);
            }, "Destroy the Link");
            
            destroySpot = new NButton(() -> {
                world().removeObject(nothing);
                world().removeObject(destroyLink);
                world().removeObject(destroySpot);
                world().removeObject(extendLink);
                destroy();
                Mode.setMode(Mode.DEFAULT);
            }, "Destroy this spot");
            
            extendLink = new NButton(() -> {
                world().removeObject(nothing);
                world().removeObject(destroyLink);
                world().removeObject(destroySpot);
                world().removeObject(extendLink);
                Mode.setMode(Mode.SET_LINK);
                Links.newLinks = this.links;
            }, "Extend the Link");
            
            nothing = new NButton(() -> {
                world().removeObject(nothing);
                world().removeObject(destroyLink);
                world().removeObject(destroySpot);
                world().removeObject(extendLink);
                Mode.setMode(Mode.DEFAULT);
            }, "Nothing");
            
            world().addObject(destroyLink, Appearance.WORLD_WIDTH / 2, 80);
            world().addObject(destroySpot, Appearance.WORLD_WIDTH / 2, 160);
            world().addObject(extendLink, Appearance.WORLD_WIDTH / 2, 240);
            world().addObject(nothing, Appearance.WORLD_WIDTH / 2, 320);
            Mode.setMode(Mode.ACTION_ON_LINK);
            
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
