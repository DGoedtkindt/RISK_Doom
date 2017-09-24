import java.awt.Color;
import greenfoot.GreenfootImage;

public class LinkSpot extends Button{
    
    private Link specificLink;
    
    public Coordinates terrHexCoordinates;
    
    public TerritoryHex specificHex;
    
    public Color linkColor;
    
    public int[] relativePosition;
    
    public LinkSpot(Link link, TerritoryHex tHex, int[] relPos){
        
        specificLink = link;
        specificHex = tHex;
        terrHexCoordinates = tHex.coordinates();
        relativePosition = relPos;
        linkColor = link.color();
        GreenfootImage img = Hexagon.createImage(linkColor, 0.2);
        this.setImage(img);

    }
    
    public LinkSpot(Color color, Coordinates coords, int[] relPos){
        
        terrHexCoordinates = coords;
        relativePosition = relPos;
        linkColor = color;
        GreenfootImage img = Hexagon.createImage(linkColor, 0.2);
        this.setImage(img);

    }
    
    public void clicked(){
        
        specificHex.clicked();
        
    }
    
    public Link specificLink(){
        return specificLink;
        
    }
    
    public void remove(){
        
        getWorld().removeObject(this);
        specificHex.linksPlacedInIt.remove(this);
        
    }
    
}
