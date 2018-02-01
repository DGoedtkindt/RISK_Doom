package basicChoosers;

import appearance.Appearance;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import appearance.Theme;
import base.Hexagon;

public class ThemeChooser extends BasicChooser {
    
    public ThemeChooser() {
        super(new ThemeChoices());
    }
    

}

class ThemeChoices extends ChoiceList {
    private int  themeNumber = 0;

    @Override
    protected void next() {
        if(themeNumber < Theme.values().length-1) themeNumber++; 
        else themeNumber = 0;
    }

    @Override
    protected void previous() {
        if(themeNumber > 0) themeNumber--;
        else themeNumber = Theme.values().length-1;
    }

    @Override
    protected GreenfootImage choiceImage() {
        Theme currentTheme = Theme.values()[themeNumber];
        
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
        
        return img;
        
    }

    /**
     *
     * @return the position of the selected Theme in the Theme.values() Array
     */
    @Override
    public String choiceValue() {
        return "" + (themeNumber);
        
    }

}
