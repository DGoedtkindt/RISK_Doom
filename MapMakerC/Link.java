import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;

public class Link {
    
    static public Link currentLink = null;
    
    public static ArrayList<Link> allLinks = new ArrayList<Link>();
    
    private Color linkColor;
    
    public HashSet<Territory> linkedTerritories = new HashSet<Territory>();
    
    public Link(Color color){
        
        linkColor = color;
        allLinks.add(this);
    }
    
    public Color color(){
        return linkColor;
        
    }
    
}
