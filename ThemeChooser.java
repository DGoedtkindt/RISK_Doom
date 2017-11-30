import greenfoot.Font;
import greenfoot.GreenfootImage;

public class ThemeChooser extends Button implements Arrowable{

    private int  themeNumber = 0;
    private Theme currentTheme = null;
    
    public ThemeChooser(){
        
        currentTheme = Theme.themeList.get(themeNumber);
        updateImage();
        
    }
    
    private void updateImage(){
        
        currentTheme = Theme.themeList.get(themeNumber);
        
        GreenfootImage img = new GreenfootImage(MyWorld.WORLD_WIDTH / 5, MyWorld.WORLD_HEIGHT / 5);
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
        MyWorld.usedTheme = currentTheme;
        MyWorld.theWorld.getBackground().setColor(currentTheme.backgroundColor.brighter());
        MyWorld.theWorld.getBackground().fill();
    }
    
}
