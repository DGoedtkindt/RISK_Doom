import greenfoot.*;
import java.awt.*;

public class MyWorld extends World
{
    static final int WORLDX = 1920;
    static final int WORLDY = 1080;
    
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
        
        placeHexagonInCollumnRow(29, 15);
    
        
    }
    
    
    private Button getPressedButton(){
        
        return null;
        
    }
    
    public void changeMode(int newMode){
        
        
        
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
        
    }
}
