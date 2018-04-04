package basicChoosers;

import appearance.Appearance;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import appearance.Theme;
import base.Hexagon;

/**
 * A Chooser designed to let the user select a Theme.
 * 
 */
public class ThemeChooser extends BasicChooser {
    
    private int themeNumber = 0;

    @Override
    public void next() {
        if(themeNumber < Theme.values().length-1) themeNumber++; 
        else themeNumber = 0;
        updateImage();
    }

    @Override
    public void previous() {
        if(themeNumber > 0) themeNumber--;
        else themeNumber = Theme.values().length-1;
        updateImage();
    }

    @Override
    protected void updateImage() {
        Theme currentTheme = Theme.values()[themeNumber];
        
        GreenfootImage img = new GreenfootImage(Appearance.WORLD_WIDTH / 5, Appearance.WORLD_HEIGHT / 5);
        img.setColor(currentTheme.backgroundColor);
        img.fill();
        GreenfootImage bckgrdHex = Hexagon.createImage(currentTheme.blankHexBorderColor);
        GreenfootImage forgrdHex = Hexagon.createImage(currentTheme.blankHexColor, 0.95);
        img.drawImage(bckgrdHex, 
                      img.getWidth() / 2 - Hexagon.RADIUS, 
                      img.getHeight() / 2 - Hexagon.RADIUS);
        img.drawImage(forgrdHex, 
                      img.getWidth() / 2 - Hexagon.RADIUS, 
                      img.getHeight() / 2 - Hexagon.RADIUS);
        img.setColor(currentTheme.textColor);
        img.setFont(new Font("Monospaced", 20));
        img.drawString(currentTheme.name, 10, img.getHeight() - 20);
        
        setImage(img);
        
    }

    /**
     *
     * @return The position of the selected Theme in the Theme.values() Array
     */
    @Override
    public String choiceValue() {
        return "" + (themeNumber);
        
    }

}
