package territory;

import base.Button;
import base.Hexagon;
import mode.Mode;
import greenfoot.GreenfootImage;
import input.ChoiceInput;
import input.Form;
import input.FormAction;
import java.util.Map;

/**
 * This Class is an Object that represents a Link between Territories.
 * 
 */
public class LinkIndic extends Button{
    
    public final Links links;
    private int[] rectCoord;
    private final Territory terr; 
    private boolean onWorld;
    
    /**
     * Creates a LinkIndic.
     * @param links The Links Object this LinkIndic will represent
     * @param terr The territory linked to the Links Object.
     * @param coordinate the coordinates of this Actor.
     */
    public LinkIndic(Links links, Territory terr, int[] coordinate) {
        this.links = links;
        this.terr = terr;
        
        //Creates an image
        GreenfootImage img = Hexagon.createImage(links.COLOR);
        img.scale(15, 15);
        setImage(img);
        
        
        //saves the posittion
        setLocation(coordinate);
        
    }
    
    @Override
    public void clicked() {
        
        if(Mode.mode() == Mode.DEFAULT){
            Form form = new Form();
            ChoiceInput choice = new ChoiceInput("You clicked on a Link. What do"
                    + " you want to do?");
            choice.addOption("destroyLink", "Delete whole Link");
            choice.addOption("destroyThis", "Delete this Link");
            choice.addOption("extendLink", "edit the Link");
            form.addInput("choice", choice, false);
            form.hasSubmitButton = false;
            form.submitAction = manageChoices();
            form.addToWorld();
            
            
        }
    
    }
    
    public void addToWorld() {
        world().addObject(this, rectCoord[0], rectCoord[1]);
        onWorld = true;
    
    }
    
    public void removeFromWorld() {
        world().removeObject(this);
        onWorld = false;
    
    }
    
    public void setLocation(int[] newCoords) {
        rectCoord = newCoords;
        if(onWorld) setLocation(rectCoord[0], rectCoord[1]);
    
    }
    
    public int[] coordinate() {
        return rectCoord;
    }
    
    private FormAction manageChoices() { 
        return (Map<String,String> input)->{
            String choice = input.get("choice");
            switch(choice) {
                case "destroyLink": 
                    links.destroy();
                    break;
                case "destroyThis": 
                    links.removeLink(terr);
                    break;
                case "extendLink": 
                    links.edit();
                    break;
        
            }
    
        };
    }
}
