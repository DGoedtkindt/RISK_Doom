import greenfoot.*;
import java.awt.Color;
import java.util.*;


public class MyWorld extends World
{
    static final int WORLDX = 1920;
    static final int WORLDY = 1080;
    
    static MyWorld theWorld; //pour accéder au monde depuis un non-acteur
    
    static private int currentMode = Mode.DEFAULT;
    
    MouseInfo mouse = Greenfoot.getMouseInfo();
    
    Button lastClickedButton;
    
  
    public MyWorld()
    {    
        super(WORLDX, WORLDY, 1);
        
        //quelques trucs cosmétiques
        Greenfoot.setSpeed(60);
        getBackground().setColor(new Color(135,135,155));
        getBackground().fill();
        
        theWorld = this;
        
        placeHexagonInCollumnRow(29, 15);
        
        //test de création de territoire
        testTerritoryCreation();
        testContinentChange();
        
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
        
        return (Button)(mouse.getActor());
        
    }
    
    //////////////////////////////////////////////////Zone de tests
    
    Territory testTerritory;
    
    private void testTerritoryCreation()
    {
        HashSet<Coordinates> hexs = new HashSet<Coordinates>();
        hexs.add(new Coordinates(new int[]{5,5}));
        hexs.add(new Coordinates(new int[]{5,4}));
        hexs.add(new Coordinates(new int[]{4,5}));
        hexs.add(new Coordinates(new int[]{4,4}));
        hexs.add(new Coordinates(new int[]{6,7}));
        hexs.add(new Coordinates(new int[]{6,5}));
        
        try {
            
            testTerritory = new Territory(hexs);
            
        }   catch(Exception e) {
            
            System.out.println(e);
        
        }
        
    }
    
    
    private void testContinentChange()
    {
        //must create continent first
        //testTerritory.setContinent(continent);

    }
    
    ////////////////////////////////////////////////
    
    
    public void changeMode(int newMode){
        
        currentMode = newMode;
        
    }
    
    
    public void escape(){
        
        Selector.clear();
        
        //must still clear the buttons
        
        changeMode(Mode.DEFAULT);
        
    }
    
    
    
    public void calcTerritoryLinks(){
        
        
        
    }
    
    
    
    public void act() 
    {
        
        mouse = Greenfoot.getMouseInfo();
        
        if(Greenfoot.mouseClicked(null)){ // Si on a appuyé quelque part
            
            lastClickedButton = getPressedButton();
            
            lastClickedButton.clicked(currentMode);
            
        }
        
        lastClickedButton = null;
        
    }
    
    
}
