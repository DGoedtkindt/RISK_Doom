import greenfoot.*;
import java.awt.Color;


public class MyWorld extends World
{
    static final int WORLDX = 1920;
    static final int WORLDY = 1080;
    
    static public int mode = Mode.DEFAULT;
    
    
    SingleHex[][] singleHex2DArray = new SingleHex[50][30];
    TerritoryHex[][] territoryHex2DArray = new TerritoryHex[50][30];
    
    MouseInfo mouse = Greenfoot.getMouseInfo();
    
    Button lastClickedButton;
    
    
    public void makeHexagon(int x, int y)
    {
        SingleHex hex = new SingleHex(x,y);
        int[] rectCoord = hex.getCoord().getRectCoord();
        addObject(hex,rectCoord[0],rectCoord[1]);
    }
    
    
    
    
    private void placeHexagonInCollumnRow(int collumn, int row)
    {
        
        for(int x = 0; x < collumn; x++) {
            
            for(int y = 0; y < row; y++) {
                
                makeHexagon(x, y);
                
            }
            
        }
        
    }
    
    
    
    
    public MyWorld()
    {    
        super(WORLDX, WORLDY, 1);
        
        //quelques trucs cosmétiques
        Greenfoot.setSpeed(60);
        getBackground().setColor(new Color(150,20,70));
        getBackground().fill();
        
        placeHexagonInCollumnRow(29, 15); // Place les hexagones dans le monde
        
    }
    
    
    
    
    private Button getPressedButton(){
        
        return (Button)(mouse.getActor());
        
    }
    
    
    
    
    public void changeMode(int newMode){
        
        mode = newMode;
        
    }
    
    
    
    
    public void selectHex(SingleHex hex){
        
        
        
    }
    
    
    
    
    public void selectTerritory(TerritoryHex territory){
        
        
        
    }
    
    
    
    
    public void init(){
        
        
        
    }
    
    
    
    
    public void saveToXML(){
        
        
        
    }
    
    
    
    
    public void escape(){
        
        
        
    }
    
    
    
    
    public void calcTerritoryLinks(){
        
        
        
    }
    
    
    
    
    public void act() 
    {
        
        
        mouse = Greenfoot.getMouseInfo();
        
        lastClickedButton = getPressedButton();
        
        
        if(lastClickedButton != null){ // Si on a appuyé quelque part
            
            
            switch(mode){
                
                default : escape();
                                break;
                
                case Mode.SELECT_HEX : 
                                break;
                
                case Mode.SELECT_TERRITORY : 
                                break;
                                
                case Mode.CHOOSE_DISPLAY_ARMIES :
                                break;
                                
                case Mode.CHOOSE_CAPITAL_TERRITORY :
                                break;
                                
                case Mode.CHOOSE_DISPLAY_INFO : 
                                break;
                                
                case Mode.SET_LINKS :
                                break;
                                
            }
            
            
        }
        
        
        
        
        lastClickedButton = null;
        
        
    }
    
    
}
