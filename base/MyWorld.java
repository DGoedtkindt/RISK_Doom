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


public class MyWorld extends World {
    
    //pour accéder au monde depuis un non-acteur
    public static MyWorld theWorld;
    
    //Détection de la souris
    private MouseInfo mouse;
    
    //Détecteur d'escape
    private CheckEscape escapeTester = new CheckEscape();
    
    //Le manager actuel du programme
    public StateManager stateManager;
    
    //Bouton retour
    public final NButton backButton = 
            new NButton(() -> {stateManager.escape();}, 
                    new GreenfootImage("backToHome.png"),30,30);
    
    public MyWorld() {    
        super(Appearance.WORLD_WIDTH, Appearance.WORLD_HEIGHT, 1);
        theWorld = this;
        load(new menu.Manager());
        Greenfoot.start();
        
    }
    
    public void load(StateManager sm) {
        makeSureSceneIsClear();
        stateManager = sm;
        stateManager.setupScene(); 
    
    }
    
    public void makeSureSceneIsClear() {
        List<Actor> allActors = this.getObjects(null);
        allActors.forEach((actor) -> this.removeObject(actor));
        getBackground().setColor(Theme.used.backgroundColor);
        getBackground().fill();
        addObject(backButton, getWidth() - 25, 27);
    
    }
    
    public void placeBlankHexs() {
        //créer les blankHexs
        placeHexagonInCollumnRow(Appearance.COLLUMN_NUMBER, Appearance.ROW_NUMBER);
        //trou pour les bonus de continent
        drawContinentBonusZone();
    
    
    }
    
    private void placeHexagonInCollumnRow(int collumn, int row) {
        for(int x = 0; x < collumn; x++) {
            
            for(int y = 0; y < row; y++) {
                BlankHex hexToPlace = BlankHex.blankHexAt(x, y);
                int[] rectCoords = hexToPlace.rectCoord();
                addObject(hexToPlace,rectCoords[0],rectCoords[1]);
                
            }
            
        }
        
    }

    private void drawContinentBonusZone(){
        
        for(BlankHex bh : getObjects(BlankHex.class)){

            if(bh.hexCoord()[0] > Appearance.CONTINENT_BONUS_X_POSITION 
                    && bh.hexCoord()[0] < Appearance.CONTINENT_BONUS_X_POSITION + Appearance.CONTINENT_BONUS_ZONE_WIDTH 
                    && bh.hexCoord()[1] > Appearance.ROW_NUMBER - Appearance.CONTINENT_BONUS_ZONE_HEIGHT){
                
                removeObject(bh);
                
            }
            
        }
        
    }
    
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
        
        escapeTester.testForEscape();
        
    }
    
    private class CheckEscape{
        boolean escapeWasClicked = false;
        
        public void testForEscape(){ 
            if(escapeReleased()){
                stateManager.escape();
                
            }
            if(Greenfoot.isKeyDown("Escape") != escapeWasClicked){
                escapeWasClicked = !escapeWasClicked;
                
            }
            
        }
        
        private boolean escapeReleased(){
            return (!Greenfoot.isKeyDown("Escape") && escapeWasClicked);
            
        }
        
    }

}
