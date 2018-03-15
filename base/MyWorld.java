package base;

import appearance.Appearance;
import appearance.Theme;
import game.ArmiesInHandDisplayer;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import greenfoot.World;
import input.Form;
import java.util.List;
import mainObjects.BlankHex;

/**
 * The World class of this scenario.
 * 
 */
public class MyWorld extends World {
    
    //The real World.
    public static MyWorld theWorld;
    
    //The Mouse.
    private MouseInfo mouse;
    
    //The current Manager.
    public StateManager stateManager;
    
    //The 'back' Button.
    public final NButton backButton = 
            new NButton(() -> {stateManager.escape();}, 
                    new GreenfootImage("backToHome.png"),30,30);
    
    /**
     * Creates the World. And initialises anything needed
     * Fun fact : if you call this constructor, you're demiurge.
     */
    public MyWorld() {    
        super(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT, 1);
        theWorld = this;
        load(new menu.Manager());
        Greenfoot.setSpeed(50);
        Greenfoot.start();
        
        //to run static block
        ArmiesInHandDisplayer.update();
        
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
        placeHexagonInCollumnRow(Appearance.COLLUMN_NUMBER, Appearance.ROW_NUMBER);
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
        
        /* TODO: à déplacer dans input.TextInput
        
        String keyPressed = Greenfoot.getKey();
        if(keyPressed != null){
        InputPanel.typeOnUsedPanel(keyPressed);
        }*/
        
        CheckEscape.testForEscape();
        
    }

    public void lockAllButtons() {
        getObjects(Button.class).forEach(Button::toggleUnusable);
        
    }
    
    public void unlockAllButtons() {
        getObjects(Button.class).forEach(Button::toggleUsable);
        
    }
    
}

/**
 * Class that checks if Escape has been released.
 */
class CheckEscape{
    
    static boolean escapeWasClicked = false;
    
    /**
     * Checks if Escape has been released and uses the escape() method of the 
     * current StateManager if it's the case.
     */
    public static void testForEscape(){ 
        if(escapeReleased()){
            if(Form.activeForm() != null) Form.activeForm().cancel("User pressed escape");
            else MyWorld.theWorld.stateManager.escape();
        }
        
        if(Greenfoot.isKeyDown("Escape") != escapeWasClicked){
            escapeWasClicked = !escapeWasClicked;
        }
        
    }
    
    /**
     * Verifies if Escape has been released.
     * @return A boolean that represents the fact that Escape has just been released.
     */
    private static boolean escapeReleased(){
        return (!Greenfoot.isKeyDown("Escape") && escapeWasClicked);
    }
    
}