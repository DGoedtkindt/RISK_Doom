package appearance;

import greenfoot.Font;
import greenfoot.GreenfootImage;
import base.*;
import arrowable.*;
import greenfoot.World;

public class ThemeChooser extends Button implements Arrowable{

    private int  themeNumber = 0;
    private Theme currentTheme = null;
    private RightArrow rightArrow;
    private LeftArrow leftArrow;
    
    public ThemeChooser(){
        
        currentTheme = Theme.themeList.get(themeNumber);
        updateImage();
        
        rightArrow = new RightArrow(this);
        leftArrow = new LeftArrow(this);
        
    }
    
    public void addToWorld(World world, int xPos, int yPos) {
        world.addObject(this, xPos, yPos);
        world.addObject(leftArrow, xPos - 310, yPos - 70);
        world.addObject(rightArrow, xPos + 310, yPos -70);
    
    }
    
    public void removeFromWorld(World world) {
        world.removeObject(this);
        world.removeObject(rightArrow);
        world.removeObject(leftArrow);
                
    }
    
    private void updateImage(){
        
        currentTheme = Theme.themeList.get(themeNumber);
        
        GreenfootImage img = new GreenfootImage(Appearance.WORLD_WIDTH / 5, Appearance.WORLD_HEIGHT / 5);
        img.setColor(currentTheme.backgroundColor);
        img.fill();
        img.drawImage(Hexagon.createImage(currentTheme.blankHexBorderColor), 
                      img.getWidth() / 2 - Hexagon.RADIUS, 
                      img.getHeight() / 2 - Hexagon.RADIUS);
        img.drawImage(Hexagon.createImage(currentTheme.blankHexColor, 0.95), 
                      img.getWidth() / 2 - Hexagon.RADIUS, 
                      img.getHeight() / 2 - Hexagon.RADIUS);
        img.setColor(currentTheme.textColor);
        img.setFont(new Font("Monospaced", 20));
        img.drawString(currentTheme.name, 10, img.getHeight() - 20);
        
        this.setImage(img);
        
    }
    
    ////////////////////////////////////////
    
    @Override
    public void next() {
        if(themeNumber < Theme.themeList.size()-1) themeNumber++;
        else themeNumber = 0;
        updateImage();
    }

    @Override
    public void previous() {
        if(themeNumber > 0) themeNumber--;
        else themeNumber = Theme.themeList.size() - 1;
        updateImage();
    }

    @Override
    public void clicked() {
        Theme.used = currentTheme;
        MyWorld.theWorld.getBackground().setColor(currentTheme.backgroundColor.brighter());
        MyWorld.theWorld.getBackground().fill();
    }
    
}
