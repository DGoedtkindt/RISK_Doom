package mainObjects;

import base.*;
import java.util.ArrayList;


public class Links {
    
    private MyWorld world() {return MyWorld.theWorld;}
    private Map map() {return world().stateManager.map();}
    public static Links newLinks;   //c'est le Links en train d'être modifié. 
                                    //== null quand un Links n'est pas en train d'etre créé
    public ArrayList<Territory> linkedTerrs = new ArrayList<>(); //to check whether a terr was already linked
    private GColor color;
    private ArrayList<LinkIndic> linkIndicList = new ArrayList<>();
    
    public Links(GColor color) {
        this.color = color;
        newLinks = this;
    }
    
    public void addToWorld() {
        linkIndicList.forEach(LinkIndic::addToWorld);
        map().links.add(this);
        
    }
    
    public void addlink(LinkIndic linkIndic, Territory linkedTerr ) {
        linkIndicList.add(linkIndic);
        if(linkedTerrs.contains(linkedTerr)) {
            linkIndic.destroy();
        }
        linkedTerrs.add(linkedTerr);
        
        //cet ordre bizare permet efficacement d'éviter 
        //de supprimer un terr de la liste en cas de doubles
        
    }
    
    //only used through LinkIndic.destroy() 
    public void removelink(LinkIndic linkToRemove) { 
        linkedTerrs.remove(linkToRemove.linkedTerr());
        linkIndicList.remove(linkToRemove);
        
        
        //pour supprimer le link quand l'avant dernier linkIndic a été supprimé
        if(linkIndicList.size() == 1 && newLinks != this) {
            linkIndicList.get(0).destroy();
            map().links.remove(this);
        }
    }
    
    public boolean isLargeEnough() {
        return linkIndicList.size() >= 2;
        
    }
    
    public void destroy() {
        while(!linkIndicList.isEmpty()) {
            linkIndicList.get(0).destroy();
            
        }
        
        map().links.remove(this);
    
    }

    public GColor color() {return color;}
    
    public ArrayList<LinkIndic> LinkIndicsList() {
        return (ArrayList<LinkIndic>)linkIndicList.clone();
    
    }
    
}
