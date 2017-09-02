import greenfoot.World;
import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.GreenfootImage;
import java.awt.Color;

public class MyWorld extends World
{
    static final Color BASE_WORLD_COLOR = new Color(135, 135, 155);
    static final Color SELECTION_COLOR = Color.GREEN;
    
    static final int TRANSPARENT = 30;
    static final int OPAQUE = 255;
    
    static final int WORLD_WIDTH = 1920;
    static final int WORLD_HEIGHT = 1080;
    
    static final int COLLUMN_NUMBER = 29;
    static final int ROW_NUMBER = 15;
    
    static MyWorld theWorld; //pour accéder au monde depuis un non-acteur
    
    static private Mode currentMode = Mode.DEFAULT;
    
    MouseInfo mouse = Greenfoot.getMouseInfo();
    Button lastClickedButton;
    
    SimpleButton createTerritoryButton = new SimpleButton("Create Territory", SimpleButton.CREATE_TERRITORY_BUTTON_ACTION);
    SimpleButton createContinentButton = new SimpleButton("Create Continent", SimpleButton.CREATE_CONTINENT_BUTTON_ACTION);
    SimpleButton editContinentBonus = new SimpleButton("Edit Continent Bonus", SimpleButton.EDIT_CONTINENT_BONUS_BUTTON_ACTION);
    SimpleButton editContinentColor = new SimpleButton("Edit Continent Color", SimpleButton.EDIT_CONTINENT_COLOR_BUTTON_ACTION);
    SimpleButton chooseCapital = new SimpleButton("Choose Capital", SimpleButton.CHOOSE_CAPITAL_TERRITORY_BUTTON_ACTION);
    SimpleButton createLink = new SimpleButton("Create Link", SimpleButton.CREATE_LINK_BUTTON_ACTION);
    SimpleButton deleteTerritory = new SimpleButton("Delete Territory", SimpleButton.DELETE_TERRITORY_BUTTON_ACTION);
    SimpleButton deleteContinent = new SimpleButton("Delete Continent", SimpleButton.DELETE_CONTINENT_BUTTON_ACTION);
    OKButton okButton = new OKButton();
    MakeXML makeXMLButton = new MakeXML();

    public MyWorld()
    {    
        super(WORLD_WIDTH, WORLD_HEIGHT, 1);
        
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
    
    public void changeMode(Mode newMode){
        
        currentMode = newMode;
        //currentMode.actionToTrigger.trigger();
        
    }
    
    public void escape(){
        
        Selector.clear();
        
        if(currentMode != Mode.DEFAULT){
            
           Selector.setValidator(Selector.EVERYTHING);
        
        }
        
        changeMode(Mode.DEFAULT);
        
    }
    
    public Mode getCurrentMode(){
        
        return currentMode;
        
    }
        
    
    public void act() 
    {
        
        mouse = Greenfoot.getMouseInfo();
        
        if(mouse != null && Greenfoot.mouseClicked(null)){ // Si on a appuyé quelque part
            
            lastClickedButton = getPressedButton();
            
            if(lastClickedButton != null){
                
                lastClickedButton.clicked();
                
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
