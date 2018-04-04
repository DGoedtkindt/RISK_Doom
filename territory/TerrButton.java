package territory;

import base.Button;

/**
 * A TerrButton is a Button that will tell a Controler when a Territory is clicked
 */
public class TerrButton extends Button{
    public Controler controler;
    private boolean onWorld = false;
    private int[] rectCoord = {0,0};
    
    public TerrButton(Controler controler) {
        this.controler = controler;
        
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

    @Override
    public void clicked() {
        if(controler != null) controler.clicked();
        
    }

}
