import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    static final int HEXAGON_SIZE = 40;
    static final int WORLDX = 1920;
    static final int WORLDY = 1080;
    
    public int[][] getHexagonCoordinate(float rad)
    //crée les coordinées des points d'un hexagone3
    {
        int[][] arr = {{
            (int)rad + HEXAGON_SIZE,
            (int)(Math.cos(Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)(Math.cos(2*Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)-rad + HEXAGON_SIZE,
            (int)(-Math.cos(Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)(-Math.cos(2*Math.PI/3) * rad) + HEXAGON_SIZE
            },{
            (int)HEXAGON_SIZE,
            (int)(Math.sin(Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)(Math.sin(2*Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)HEXAGON_SIZE,
            (int)(-Math.sin(Math.PI/3) * rad) + HEXAGON_SIZE,
            (int)(-Math.sin(2*Math.PI/3) * rad) + HEXAGON_SIZE
            }};
        return arr;
    }
    
    public void makeHexagon(int xPos, int yPos)
    {
        Hexagon hex = new Hexagon();
        addObject(hex,xPos,yPos);
        GreenfootImage img = new GreenfootImage(2*HEXAGON_SIZE, 2*HEXAGON_SIZE);
        
        int[][] array = getHexagonCoordinate(HEXAGON_SIZE);
        int[][] array2 = getHexagonCoordinate(HEXAGON_SIZE*(float)0.95);
        
        img.setColor(Color.black);
        img.fillPolygon(array[0], array[1], 6);
        img.setColor(Color.white);
        img.fillPolygon(array2[0], array2[1], 6);
        
        hex.setImage(img);
    }
    
    void placeHexagonInCollumnRow(int collumn, int row)
    {
        for(int x = 0; x < collumn; x++) {
            for(int y = 0; y < row; y++) {
                int[] hexCoord = {x,y};
                int[] pixCoord = coordConvert(hexCoord);
                makeHexagon(pixCoord[0], pixCoord[1]);
            }
        }
    }
    
    public int[] coordConvert(int[] hexCoord)
    //convertit les coordonées hexagonales en coordonée en pixel
    {
        int[] converted = new int[2];
        double h = Math.sin(Math.PI/3) * (HEXAGON_SIZE-1);
        int DECALAGE_Y = 15;
        converted[0] = (HEXAGON_SIZE-1) + (int)(1.5 * hexCoord[0] * (HEXAGON_SIZE-1));
        converted[1] = DECALAGE_Y + (int)(h + 2 * hexCoord[1] * h);
        if(hexCoord[0] % 2 == 1) converted[1] += h;
        
        return converted;
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
    
    public void act() 
    {
        
    }
}
