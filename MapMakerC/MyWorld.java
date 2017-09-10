import greenfoot.World;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.GreenfootImage;
import java.awt.Color;

public class MyWorld extends World
{
    static final Color BASE_WORLD_COLOR = new Color(135, 135, 155);
    static final Color SELECTION_COLOR = Color.GREEN;
    static final Color MENU_COLOR = new Color(121, 163, 200);
    
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
    
    ModeButton createTerritory      = new ModeButton("newTerritory.png",        Mode.CREATE_TERRITORY,      Selector.IS_SINGLEHEX);
    ModeButton createContinent      = new ModeButton("addContinent.png",        Mode.CREATE_CONTINENT,      Selector.IS_TERRITORY_NOT_IN_CONTINENT);
    ModeButton editContinentBonus   = new ModeButton("ContinentBonus.png",    Mode.EDIT_CONTINENT_BONUS,  Selector.IS_CONTINENT);
    ModeButton editContinentColor   = new ModeButton("ContinentColor.png",    Mode.EDIT_CONTINENT_COLOR,  Selector.IS_CONTINENT);
    ModeButton editTerritoryBonus   = new ModeButton("editTerritoryBonus.png",    Mode.EDIT_TERRITORY_BONUS,  Selector.IS_TERRITORY);
    ModeButton createLink           = new ModeButton("newLink.png",             Mode.SET_LINK,              Selector.IS_TERRITORY);
    ModeButton deleteTerritory      = new ModeButton("deleteTerritory.png",        Mode.DELETE_TERRITORY,      Selector.IS_TERRITORY);
    ModeButton deleteContinent      = new ModeButton("deleteContinent.png",        Mode.DELETE_CONTINENT,      Selector.IS_CONTINENT);
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
        addObject(createLink, MyWorld.WORLD_WIDTH - 70, 160);
        addObject(editTerritoryBonus, MyWorld.WORLD_WIDTH - 130, 160);
        addObject(deleteTerritory, MyWorld.WORLD_WIDTH - 100, 220);
        addObject(createContinent, MyWorld.WORLD_WIDTH - 100, 300);
        addObject(editContinentBonus, MyWorld.WORLD_WIDTH - 70, 360);
        addObject(editContinentColor, MyWorld.WORLD_WIDTH - 130, 360);
        addObject(deleteContinent, MyWorld.WORLD_WIDTH - 100, 420);
        addObject(okButton, MyWorld.WORLD_WIDTH - 100, 510);
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
