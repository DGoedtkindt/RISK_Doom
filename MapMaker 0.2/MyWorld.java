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
    
    public void makeHexagon(int xPos, int yPos/*, Color continent, Color player /* à rajouter pour gérer la couleur*/)
    {
        Hexagon hex = new Hexagon();
        addObject(hex,xPos,yPos);
        GreenfootImage img = new GreenfootImage(2*HEXAGON_SIZE, 2*HEXAGON_SIZE);
        
        int[][] array = getHexagonCoordinate(HEXAGON_SIZE);
        int[][] array2 = getHexagonCoordinate(HEXAGON_SIZE*(float)0.95);
        //int[][] array3 = getHexagonCoordinate(HEXAGON_SIZE*(float)0.65);
        
        img.setColor(Color.BLACK);
        img.fillPolygon(array[0], array[1], 6);
        img.setColor(/*continent*/ Color.white);
        img.fillPolygon(array2[0], array2[1], 6);
        
        hex.setImage(img);
        //hex.getImage().setColor(player);
        //hex.getImage().fillPolygon(array3[0], array3[1], 6);
    }
    
    public int[] coordConvert(int[] hexCoord)
    //convertit les coordonées hexagonales en coordonée en pixel
    {
        int[] converted = new int[2];
        double h = Math.sin(Math.PI/3) * (HEXAGON_SIZE-1);
        converted[0] = (HEXAGON_SIZE-1) + (int)(1.5 * hexCoord[0] * (HEXAGON_SIZE-1));
        converted[1] = (int)(h + 2 * hexCoord[1] * h);
        if(hexCoord[0] % 2 == 1) converted[1] += h;
        
        return converted;
    }
    
    public MyWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(WORLDX, WORLDY, 1);
        Greenfoot.setSpeed(60);
        getBackground().setColor(new Color(150,20,70));
        getBackground().fill();
        
        for(int x = 0; x < 30; x++) {
            for(int y = 0; y < 15; y++) {
                int[] hexCoord = {x,y};
                int[] pixCoord = coordConvert(hexCoord);
                //System.out.println(hexCoord[0] + ", " + hexCoord[1] + " : " + pixCoord[0] + ", " + pixCoord[1]);
                makeHexagon(pixCoord[0], pixCoord[1] /*, Color.white, Color.white*/);
            }
        }
    
        
    }
    
    public void act() 
    {
        
    }
}