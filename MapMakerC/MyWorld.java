import greenfoot.World;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.GreenfootImage;
import java.awt.Color;

public class MyWorld extends World
{
    static final Color BASE_WORLD_COLOR = new Color(135, 135, 155);
    static final Color SELECTION_COLOR = Color.GREEN;
    static final Color MENU_COLOR = new Color(156, 93, 82);
    
    static final int TRANSPARENT = 30;
    static final int OPAQUE = 255;
    
    static final int WORLD_WIDTH = 1920;
    static final int WORLD_HEIGHT = 1080;
    
    static final int CONTINENT_BONUS_X_LEFT = 550;
    static final int CONTINENT_BONUS_X_RIGHT = 1200;
    static final int CONTINENT_BONUS_Y_UP = 900;
    
    static final int COLLUMN_NUMBER = 29;
    static final int ROW_NUMBER = 15;
    
    static MyWorld theWorld; //pour accéder au monde depuis un non-acteur
    
    private MouseInfo mouse = Greenfoot.getMouseInfo();
    
    ModeButton createTerritory      = new ModeButton("Create Territory",        Mode.CREATE_TERRITORY,      Selector.IS_SINGLEHEX);
    ModeButton createContinent      = new ModeButton("Create Continent",        Mode.CREATE_CONTINENT,      Selector.IS_TERRITORY_NOT_IN_CONTINENT);
    ModeButton editContinentBonus   = new ModeButton("Edit Continent Bonus",    Mode.EDIT_CONTINENT_BONUS,  Selector.IS_CONTINENT);
    ModeButton editContinentColor   = new ModeButton("Edit Continent Color",    Mode.EDIT_CONTINENT_COLOR,  Selector.IS_CONTINENT);
    ModeButton editTerritoryBonus   = new ModeButton("Edit Territory Bonus",    Mode.EDIT_TERRITORY_BONUS,  Selector.IS_TERRITORY);
    ModeButton createLink           = new ModeButton("Create Link",             Mode.SET_LINK,              Selector.IS_TERRITORY);
    ModeButton deleteTerritory      = new ModeButton("Delete Territory",        Mode.DELETE_TERRITORY,      Selector.IS_TERRITORY);
    ModeButton deleteContinent      = new ModeButton("Delete Continent",        Mode.DELETE_CONTINENT,      Selector.IS_CONTINENT);
    OKButton okButton               = new OKButton();
    MakeXML makeXMLButton           = new MakeXML();
    

    public MyWorld()
    {    
        super(WORLD_WIDTH, WORLD_HEIGHT, 1);
        theWorld = this;
        
        //quelques trucs cosmétiques
        Greenfoot.setSpeed(60);
        getBackground().setColor(BASE_WORLD_COLOR);
        getBackground().fill();
        
        //Hexagones bruns sur le côté
        for(int i = 29; i < 34;i++){
            for(int j = -1; j <= 15; j++){
                GreenfootImage hex;
                hex = Hexagon.createImage(MENU_COLOR,1);
                int[] rectCoord = Coordinates.hexToRectCoord(new int[]{i,j});
                int size = Hexagon.RADIUS;
                
                getBackground().drawImage(hex,rectCoord[0]-size,rectCoord[1]-size);
            }
        }
        
        // placement des boutons
        addObject(createTerritory, MyWorld.WORLD_WIDTH -100, 100);
        addObject(createLink, MyWorld.WORLD_WIDTH - 100, 130);
        addObject(editTerritoryBonus, MyWorld.WORLD_WIDTH - 100, 160);
        addObject(deleteTerritory, MyWorld.WORLD_WIDTH - 100, 190);
        addObject(createContinent, MyWorld.WORLD_WIDTH - 100, 300);
        addObject(editContinentBonus, MyWorld.WORLD_WIDTH - 100, 330);
        addObject(editContinentColor, MyWorld.WORLD_WIDTH - 100, 360);
        addObject(deleteContinent, MyWorld.WORLD_WIDTH - 100, 390);
        addObject(okButton, MyWorld.WORLD_WIDTH - 100, 500);
        addObject(makeXMLButton, MyWorld.WORLD_WIDTH - 100, 600);
        
        // placement des hexagones
        placeHexagonInCollumnRow(COLLUMN_NUMBER, ROW_NUMBER);
        
        // zone des bonus de continent
        for(SingleHex sh : getObjects(SingleHex.class)){
            
            if(sh.getX() > CONTINENT_BONUS_X_LEFT && sh.getX() < CONTINENT_BONUS_X_RIGHT && sh.getY() > CONTINENT_BONUS_Y_UP){
                
                removeObject(sh);
                
            }
            
        }
        
        Mode.changeMode(Mode.DEFAULT);
        
    }
    
    ///////////////////////////////
    
    public void makeSingleHex(int x, int y) {
        SingleHex hex = new SingleHex(x,y);
        int[] rectCoord = hex.getCoord().rectCoord();
        addObject(hex,rectCoord[0],rectCoord[1]);
        
    }
    
    private void placeHexagonInCollumnRow(int collumn, int row){
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
    
    public void escape(){
        Selector.clear();
        Mode.changeMode(Mode.DEFAULT);
        
    }   
    
    public void act() {
        mouse = Greenfoot.getMouseInfo();
        if(mouse != null && Greenfoot.mouseClicked(null)){ // Si on a appuyé quelque part
            
            if(getPressedButton() != null){
                getPressedButton().clicked();
                
            }else{
                escape();
                
            }
            
        }
        
        if(Greenfoot.isKeyDown("Escape")){
            escape();
            
        }
        
    }
    
    
}
