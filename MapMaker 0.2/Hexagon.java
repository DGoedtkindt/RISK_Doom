import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Image here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hexagon extends Actor
{
    private int[] hexCoordinate = new int[2];
    
    public int[] setHexCoord(int x, int y)
    {
        hexCoordinate[0] = x;
        hexCoordinate[1] = y;
        return hexCoordinate;
    }
    
    public int[] getHexCoord()
    {
        return hexCoordinate;
    }
    
    public void act() 
    {
        //if(Greenfoot.mouseClicked(this)) {System.out.println("redfacedpinapleofjustice");}
    }    
}
