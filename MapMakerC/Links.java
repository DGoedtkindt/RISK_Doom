import java.awt.Color;
import java.util.ArrayList;

public class Links {
    public static Links newLinks;
    public ArrayList<Territory> linkedTerrs = new ArrayList<>(); //to check whether a terr was already linked
    private Color color;
    private ArrayList<LinkIndic> linkIndicList = new ArrayList<>();
    private static ArrayList<Links> linksList = new ArrayList<>();

    private static MyWorld world() {return MyWorld.theWorld;}
    
    public Links(Color color) {
        this.color = color;
        newLinks = this;
        linksList.add(this);
    }
    
    public void addlink(LinkIndic linkIndic, Territory linkedTerr ) {
        if(!linkedTerrs.contains(linkedTerr)){
            linkIndicList.add(linkIndic);
            linkedTerrs.add(linkedTerr);
        } else{
            linkIndic.destroy();
        } 
    }
    
    //only used through LinkIndic.destroy() 
    public void removelink(LinkIndic linkToRemove) {  
        world().removeObject(linkToRemove);
        linkIndicList.remove(linkToRemove);
        linkedTerrs.remove(linkToRemove.linkedTerr());
        
        if(linkIndicList.size() == 1) {
            linkIndicList.get(0).destroy();
            linksList.remove(this);
        }
   
    }

    public Color color() {return color;}
    
    public ArrayList<LinkIndic> LinkIndicsList() {
        return (ArrayList<LinkIndic>)linkIndicList.clone();
    
    }
    
    public static ArrayList<Links> allLinks() {
        return (ArrayList<Links>)linksList.clone();
    
    }
    
}
