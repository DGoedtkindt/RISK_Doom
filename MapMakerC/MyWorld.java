import greenfoot.World;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.GreenfootImage;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MyWorld extends World
{
    static final Color BASE_WORLD_COLOR = new Color(135, 135, 155);
    static final Color SELECTION_COLOR = Color.GREEN;
    static final int TRANSPARENT = 30;
    static final int OPAQUE = 255;
    
    static final int WORLDX = 1920;
    static final int WORLDY = 1080;
    
    static final int COLLUMN_NUMBER = 29;
    static final int ROW_NUMBER = 15;
    
    static MyWorld theWorld; //pour accéder au monde depuis un non-acteur
    
    static private int currentMode = Mode.DEFAULT;
    
    MouseInfo mouse = Greenfoot.getMouseInfo();
    Button lastClickedButton;
  
    public MyWorld()
    {    
        super(WORLDX, WORLDY, 1);
        
        //quelques trucs cosmétiques
        Greenfoot.setSpeed(60);
        getBackground().setColor(BASE_WORLD_COLOR);
        getBackground().fill();
        
        for(int i = 29; i < 34;i++){        //Hexagones violets sur le côté
            for(int j = 0; j <= 15; j++){
                GreenfootImage hex;
                hex = Hexagon.createSimpleHexImage(new Color(110,70,130),1);
                int[] rectCoord = Coordinates.hexToRectCoord(new int[]{i,j});
                int size = Hexagon.getSize();
                
                getBackground().drawImage(hex,rectCoord[0]-size,rectCoord[1]-size);
            }
        }
        
        theWorld = this;
        
        placeHexagonInCollumnRow(COLLUMN_NUMBER, ROW_NUMBER);
        
        //Placement des boutons
        addObject(new CreateTerritory(), WORLDX - 100, 100);
        addObject(new CreateContinent(), WORLDX - 100, 150);
        addObject(new EditContinentBonus(), WORLDX - 100, 200);
        addObject(new EditContinentColor(), WORLDX - 100, 250);
        addObject(new ChooseCapitalTerritory(), WORLDX - 100, 300);
        addObject(new CreateLinks(), WORLDX - 100, 350);
        addObject(new DeleteTerritory(), WORLDX - 100, 400);
        addObject(new DeleteContinent(), WORLDX - 100, 450);
        addObject(new OKButton(), WORLDX - 100, 500);
        addObject(new MakeXML(), WORLDX - 100, 550);
        
    }
    
    ///////////////////////////////
    
    public void makeSingleHex(int x, int y)
    {
        
        SingleHex hex = new SingleHex(x,y);
        int[] rectCoord = hex.getCoord().getRectCoord();
        addObject(hex,rectCoord[0],rectCoord[1]);
        
    }
    
    private void placeHexagonInCollumnRow(int collumn, int row)
    {
        
        for(int x = 0; x < collumn; x++) {
            
            for(int y = 0; y < row; y++) {

                makeSingleHex(x, y);
                
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
    
    //////////////////////////////////////////////////
    
    
    
    public void changeMode(int newMode){
        
        currentMode = newMode;
        
    }
    
    public void escape(){
        
        Selector.clear();
        
        if(currentMode != Mode.DEFAULT){
            
            makeEverythingOpaque();
        
        }
        
        changeMode(Mode.DEFAULT);
        
    }
    
    public int getCurrentMode(){
        
        return currentMode;
        
    }
    
    public void setTerrHexTransparent(){
        
        List<TerritoryHex> TerritoryHexes = getObjects(TerritoryHex.class);
        
        for(TerritoryHex th : TerritoryHexes){
            
            th.getImage().setTransparency(MyWorld.TRANSPARENT);
            th.getTerritory().makeTransparent();
            
        }
        
    }
    
    public void setOccupiedTerritoriesTransparent(){
        
        ArrayList<Territory> allTerritories = Territory.getAllTerritories();
        ArrayList<Territory> occupiedTerritories = new ArrayList<Territory>();
        
        for(Territory t : allTerritories){
            
            if(t.getContinent() != null){
                
                occupiedTerritories.add(t);
                
            }
            
        }
        
        for(Territory t: occupiedTerritories){
            
            for(TerritoryHex th : t.getComposingHex()){
                
                th.getImage().setTransparency(MyWorld.TRANSPARENT);
                
            }
            
            t.makeTransparent();
            
        }
        
    }
    
    public void setUnoccupiedTerritoriesTransparent(){
        
        ArrayList<Territory> allTerritories = Territory.getAllTerritories();
        ArrayList<Territory> unoccupiedTerritories = new ArrayList<Territory>();
        
        for(Territory t : allTerritories){
            
            if(t.getContinent() == null){
                
                unoccupiedTerritories.add(t);
                
            }
            
        }
        
        for(Territory t: unoccupiedTerritories){
            
            for(TerritoryHex th : t.getComposingHex()){
                
                th.getImage().setTransparency(MyWorld.TRANSPARENT);
                
            }
            
            t.makeTransparent();
            
        }
        
    }
    
    public void setSingleHexTransparent(){
        
        List<SingleHex> SingleHexes = getObjects(SingleHex.class);
        
        for(SingleHex sh : SingleHexes){
            
            sh.getImage().setTransparency(MyWorld.TRANSPARENT);
            
        }
        
    }
    
    public void makeEverythingOpaque(){
        
        List<SingleHex> SingleHexes = getObjects(SingleHex.class);
        
        for(SingleHex sh : SingleHexes){
            
            sh.makeOpaque();
            
        }
        
        for(Territory terr : Territory.territoryList){
            
            terr.makeOpaque();
            
        }
        
    }
    
    public void act() 
    {
        
        mouse = Greenfoot.getMouseInfo();
        
        if(mouse != null && Greenfoot.mouseClicked(null)){ // Si on a appuyé quelque part
            
            lastClickedButton = getPressedButton();
            
            if(lastClickedButton != null){
                
                lastClickedButton.clicked(currentMode);
                
            }else{
                
                escape();
                
            }
            
        }
        
        if(Greenfoot.isKeyDown("Escape")){
            
            escape();
            
        }
        
        lastClickedButton = null;
        
    }
    
    
}
