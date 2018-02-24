package base;

import appearance.Appearance;
import appearance.Theme;
import mainObjects.BlankHex;
import greenfoot.World;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import java.util.List;

/**
 * The World class of this scenario.
 * 
 */
public class MyWorld extends World {
    
    //pour accéder au monde depuis un non-acteur
    public static MyWorld theWorld;
    
    //Détection de la souris
    private MouseInfo mouse;
    
    //Le manager actuel du programme
    public StateManager stateManager;
    
    //Bouton retour
    public final NButton backButton = 
            new NButton(() -> {stateManager.escape();}, 
                    new GreenfootImage("backToHome.png"),30,30);
    
    /**
     * Creates the World.
     * Fun fact : if you call this constructor, you're demiurge.
     */
    public MyWorld() {    
        super(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT, 1);
        theWorld = this;
        load(new menu.Manager());
        Greenfoot.start();
        
    }
    
    /**
     * Loads a StateManager.
     * @param sm The StateManager to load.
     */
    public void load(StateManager sm) {
        makeSureSceneIsClear();
        stateManager = sm;
        stateManager.setupScene(); 
    
    }
    
    /**
     * Destroys all Actors and resets the background.
     */
    public void makeSureSceneIsClear() {
        List<Actor> allActors = this.getObjects(null);
        allActors.forEach((actor) -> this.removeObject(actor));
        getBackground().setColor(Theme.used.backgroundColor);
        getBackground().fill();
        addObject(backButton, getWidth() - 25, 27);
    
    }
    
    /**
     * Creates the final Blank Hex grid.
     */
    public void placeBlankHexs() {
        //créer les blankHexs
        placeHexagonInCollumnRow(Appearance.COLLUMN_NUMBER, Appearance.ROW_NUMBER);
        //trou pour les bonus de continent
        drawContinentBonusZone();
    
    
    }
    
    /**
     * Creates a full Blank Hex grid.
     * @param collumn The number of collumns.
     * @param row The number of rows.
     */
    private void placeHexagonInCollumnRow(int collumn, int row) {
        for(int x = 0; x < collumn; x++) {
            
            for(int y = 0; y < row; y++) {
                BlankHex hexToPlace = BlankHex.blankHexAt(x, y);
                int[] rectCoords = hexToPlace.rectCoord();
                addObject(hexToPlace,rectCoords[0],rectCoords[1]);
                
            }
            
        }
        
    }
    
    /**
     * Destroys Blank Hexes to clear a zone for the Continent bonuses displayer.
     */
    private void drawContinentBonusZone(){
        
        for(BlankHex bh : getObjects(BlankHex.class)){

            if(bh.hexCoord()[0] > Appearance.CONTINENT_BONUS_X_POSITION 
                    && bh.hexCoord()[0] < Appearance.CONTINENT_BONUS_X_POSITION + Appearance.CONTINENT_BONUS_ZONE_WIDTH 
                    && bh.hexCoord()[1] > Appearance.ROW_NUMBER - Appearance.CONTINENT_BONUS_ZONE_HEIGHT){
                
                removeObject(bh);
                
            }
            
        }
        
    }
    
    /**
     * Returns the Button the user pressed.
     * @return The Button, or null, if the user didn't press a Button.
     */
    private Button getPressedButton(){
        if(mouse.getActor() instanceof Button){
            return (Button)(mouse.getActor());
            
        }else{
            return null;
            
        }
        
    }
    
    @Override
    public void act() {
        
        mouse = Greenfoot.getMouseInfo();
        if(mouse != null && Greenfoot.mouseClicked(null)){ // Si on a appuyé quelque part
            
            if(getPressedButton() != null){
                getPressedButton().clicked();
                
            }
            
        }
        
        /*String pressedKey = Greenfoot.getKey();
        if(pressedKey != null){InputPanel.usedPanel.type(pressedKey);}*/
        
        CheckEscape.testForEscape();
        
    }
    
}

/**
 * Class that checks if Escape has been released.
 */
class CheckEscape{
    static boolean escapeWasClicked = false;
    
    /**
     * Checks if Escape has been released.
     */
    public static void testForEscape(){ 
        if(escapeReleased()){
            MyWorld.theWorld.stateManager.escape();
        }
        
        if(Greenfoot.isKeyDown("Escape") != escapeWasClicked){
            escapeWasClicked = !escapeWasClicked;
        }
        
    }
    
    /**
     * Checks if Escape has been released.
     */
    private static boolean escapeReleased(){
        return (!Greenfoot.isKeyDown("Escape") && escapeWasClicked);
    }
    
}