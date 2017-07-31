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
    
    static private HashSet<Continent> continentList = new HashSet<Continent>();
  
    public MyWorld()
    {    
        super(WORLDX, WORLDY, 1);
        
        //quelques trucs cosmétiques
        Greenfoot.setSpeed(60);
        getBackground().setColor(new Color(135,135,155));
        getBackground().fill();
        
        for(int i = 29; i < 34;i++){
            for(int j = 0; j <= 15; j++){
                GreenfootImage hex;
                hex = Hexagon.createSimpleHexImage(new Color(110,70,130),1);
                int[] rectCoord = Coordinates.hexToRectCoord(new int[]{i,j});
                int size = Hexagon.getSize();
                
                getBackground().drawImage(hex,rectCoord[0]-size,rectCoord[1]-size);
            }
        }
        
        
        theWorld = this;
        
        //mettre les single hexs
        placeHexagonInCollumnRow(29, 15);
        
        //rajouter des boutons
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
    
    public void addContinentToList(Continent continent){
        
        continentList.add(continent);
        
    }
    
    public void removeContinentFromList(Continent continent){
        
        continentList.remove(continent);
        
    }
    
    public ArrayList<Continent> getContinentList(){
        
        ArrayList<Continent> continents = new ArrayList<Continent>();
        
        continents.addAll(0, continentList);
        
        return continents;
        
    }
    
    public void changeMode(int newMode){
        
        currentMode = newMode;
        
    }
    
    
    public void escape(){
        
        Selector.clear();
        
        //must still clear the buttons
        
        changeMode(Mode.DEFAULT);
        
    }
    
    public int getCurrentMode(){
        
        return currentMode;
        
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
