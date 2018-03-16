package input;

import appearance.Appearance;
import appearance.MessageDisplayer;
import appearance.Theme;
import base.GColor;
import base.Hexagon;
import base.MyWorld;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import java.util.List;

/**
 * An Input is an Object that allow to enter some information. It can be 
 * added to a Form that will display it and use it's information.
 * 
 */
public abstract class Input {
    
    private static Input activeInput;
    public static final int HEIGHT = 180;
    public static final int WIDTH = appearance.Appearance.WORLD_WIDTH;
    
    
    
    /**
     * Creates an InputPanel.
     * @param infoName The name of the information that the User needs to give.
     * @param size The width of the Panel.
     * @param type The type of the required information.
     * @param source The InputpanelUser that asked for an information.
     * @param inputType The type of this input.
     */
    private InputPanel(String infoName, int size, String type, InputPanelUser source, InputType inputType){
        name = infoName;
        width = size;
        this.type = type;
        this.source = source;
        this.inputType = inputType;
        usedPanel = this;
        resetImage();
        updateEveryImage();
    }
    
    @Override
    public void clicked() {
        usedPanel = this;
        updateEveryImage();
    }
    
    abstract void addToWorld(int xPos, int yPos);
    abstract void removeFromWorld();
    
    /** gets the value this Input stores.
     * @return the value that was inputed. Must return "" if nothing
     *      was inputed
     */
    public abstract String value();
    
    
    
    /**
     * Resets the image of this InputPanel in order to update its display.
     */
    private void resetImage(){
        
        GreenfootImage img = new GreenfootImage(1, 1);
        img.setFont(Appearance.GREENFOOT_FONT);
        
        img.setColor(Theme.used.backgroundColor.brighter());
        img.fill();
        
        img.setColor(Theme.used.textColor);
        FontMetrics fm = img.getAwtImage().getGraphics().getFontMetrics(Appearance.AWT_FONT);
        
        lineHeight = fm.getMaxAscent() + fm.getMaxDescent() + 5;
        int linesNumber = 1;
        
        //If the name can't fit in the given width
        if(fm.stringWidth(name) > width){
            width = fm.stringWidth(name);
        }
        
        //If the name can't fit in the screen
        if(width > Appearance.WORLD_WIDTH){
            name = Appearance.standardTextWrapping(name, Appearance.WORLD_WIDTH);
            linesNumber += name.split("\n").length - 1;
        }
        
        //Scales the image differently according to the InputType of the Panel
        switch(inputType){
            
            case CONFIRM :
                img.scale(width + 10, (linesNumber + 1) * lineHeight + 90);
                break;
            
            //Draws the String that the User types on the INSERT Panel
            case INSERT :
                img.scale(width + 10, (linesNumber + 1) * lineHeight + 5);
                img.drawString(returnedString, 5, (linesNumber + 1) * lineHeight);
                break;
            
            //Creates a Color Chooser on the COLOR Panel
            case COLOR :
                
                int nameHeight = (linesNumber + 1) * lineHeight + 5;
                
                img.scale(width + 10, nameHeight + COLOR_CHOOSER_HEIGHT);
                
                int hexagonWidth = width / 3;
                int slidersWidth = width - hexagonWidth;
                
                int gapBetweenSliders = (slidersWidth - 3 * Slider.WIDTH) / 4;
                
                int verticalGap = (COLOR_CHOOSER_HEIGHT - 256) / 2;
                
                //Draws the red rectangle
                img.setColor(GColor.WHITE);
                img.fillRect(hexagonWidth + gapBetweenSliders, nameHeight + verticalGap, Slider.WIDTH, 256);
                img.setColor(GColor.RED);
                img.fillRect(hexagonWidth + gapBetweenSliders, nameHeight + verticalGap + 256 - red.value(), Slider.WIDTH, red.value());
                
                //Draws the green rectangle
                img.setColor(GColor.WHITE);
                img.fillRect(hexagonWidth + 2 * gapBetweenSliders + Slider.WIDTH, nameHeight + verticalGap, Slider.WIDTH, 256);
                img.setColor(GColor.GREEN);
                img.fillRect(hexagonWidth + 2 * gapBetweenSliders + Slider.WIDTH, nameHeight + verticalGap + 256 - green.value(), Slider.WIDTH, green.value());
                
                //Draws the blue rectangle
                img.setColor(GColor.WHITE);
                img.fillRect(hexagonWidth + 3 * gapBetweenSliders + 2 * Slider.WIDTH, nameHeight + verticalGap, Slider.WIDTH, 256);
                img.setColor(GColor.BLUE);
                img.fillRect(hexagonWidth + 3 * gapBetweenSliders + 2 * Slider.WIDTH, nameHeight + verticalGap + 256 - blue.value(), Slider.WIDTH, blue.value());
                
                //Draws the Hexagon
                GreenfootImage hexagonImage = Hexagon.createImage(new GColor(red.value(), green.value(), blue.value()));
                
                if(hexagonWidth - 10 < 2 * COLOR_CHOOSER_HEIGHT / 3){
                    hexagonImage.scale(hexagonWidth - 10, hexagonWidth - 10);
                }else{
                    hexagonImage.scale(2 * COLOR_CHOOSER_HEIGHT / 3, 2 * COLOR_CHOOSER_HEIGHT / 3);
                }
                
                img.drawImage(hexagonImage, 5, nameHeight + COLOR_CHOOSER_HEIGHT / 3 - hexagonImage.getHeight() / 2);
                
                break;
            
        }
        
        //Writes the name of this Panel on the image.
        img.setColor(Theme.used.textColor);
        img.drawString(name, 5, lineHeight);
        
        //Draws a Rectangle around the used Inputpanel.
        if(usedPanel == this){
            img.drawRect(1, 1, img.getWidth() - 2, img.getHeight() - 2);
        }
        
        setImage(img);
        
    }
    
    /**
     * Removes this from the World and gives its informations to the source.
     */
    private void close(){
        destroy();
        try {
            source.useInformations(returnedString, type);
        } catch (Exception ex) {
            MessageDisplayer.showException(ex);
        }
    }
    
    /**
     * Removes this from the World.
     */
    public void destroy(){
        world().removeObject(this);
        
        if(inputType == InputType.COLOR){
            world().removeObject(red);
            world().removeObject(green);
            world().removeObject(blue);
            world().removeObject(SELECT);
        }else if(inputType == InputType.CONFIRM){
            world().removeObject(YES);
            world().removeObject(NO);
        }
        
        usedPanel = null;
        
        //Change the focus
        List<InputPanel> otherPanels = world().getObjects(InputPanel.class);
        
        if(!otherPanels.isEmpty()){
            usedPanel = otherPanels.get(0);
            updateEveryImage();
        }
        
    }
    
    /**
     * Shows an InputPanel with the INSERT InputType
     * @param name The name of this Panel.
     * @param width The width of this Panel.
     * @param type The type of the required information.
     * @param source The source of the request.
     * @param x The x coordinate of the Panel.
     * @param y The y coordinate of the Panel.
     */
    public static void showInsertionPanel(String name, int width, String type, InputPanelUser source, int x, int y){
        InputPanel panel = new InputPanel(name, width, type, source, InputType.INSERT);
        MyWorld.theWorld.addObject(panel, x, y);
        updateEveryImage();
        
    }
    
    /**
     * Shows an InputPanel with the CONFIRM InputType
     * @param name The name of this Panel.
     * @param width The width of this Panel.
     * @param type The type of the required information.
     * @param source The source of the request.
     * @param x The x coordinate of the Panel.
     * @param y The y coordinate of the Panel.
     */
    public static void showConfirmPanel(String name, int width, String type, InputPanelUser source, int x, int y){
        InputPanel panel = new InputPanel(name, width, type, source, InputType.CONFIRM);
        MyWorld.theWorld.addObject(panel, x, y);
        updateEveryImage();
        MyWorld.theWorld.addObject(YES, x - panel.getImage().getWidth() / 4, y + panel.getImage().getHeight() / 2 - YES.getImage().getHeight() / 2);
        MyWorld.theWorld.addObject(NO, x + panel.getImage().getWidth() / 4, y + panel.getImage().getHeight() / 2 - NO.getImage().getHeight() / 2);
    }
    
    /**
     * Shows an InputPanel with the COLOR InputType
     * @param name The name of this Panel.
     * @param width The width of this Panel.
     * @param type The type of the required information.
     * @param source The source of the request.
     * @param x The x coordinate of the Panel.
     * @param y The y coordinate of the Panel.
     */
    public static void showColorPanel(String name, int width, String type, InputPanelUser source, int x, int y){
        InputPanel panel = new InputPanel(name, width, type, source, InputType.COLOR);
        
        red = new Slider(x - panel.getImage().getWidth() / 6 + (2 * panel.getImage().getWidth() / 3 - 3 * Slider.WIDTH) / 4 + (int)(0.4 * Slider.WIDTH + 2),
                         y + panel.getImage().getHeight() / 2 - (COLOR_CHOOSER_HEIGHT - 256) / 2, new GColor(255, 0, 0));
        green = new Slider(x - panel.getImage().getWidth() / 6 + 2 * (2 * panel.getImage().getWidth() / 3 - 3 * Slider.WIDTH) / 4 + (int)(1.4 * Slider.WIDTH),
                           y + panel.getImage().getHeight() / 2 - (COLOR_CHOOSER_HEIGHT - 256) / 2, new GColor(255, 0, 0));
        blue = new Slider(x - panel.getImage().getWidth() / 6 + 3 * (2 * panel.getImage().getWidth() / 3 - 3 * Slider.WIDTH) / 4 + (int)(2.4 * Slider.WIDTH - 1),
                          y + panel.getImage().getHeight() / 2 - (COLOR_CHOOSER_HEIGHT - 256) / 2, new GColor(255, 0, 0));
        
        MyWorld.theWorld.addObject(panel, x, y);
        MyWorld.theWorld.addObject(red, red.x, red.y);
        MyWorld.theWorld.addObject(green, green.x, green.y);
        MyWorld.theWorld.addObject(blue, blue.x, blue.y);
        MyWorld.theWorld.addObject(SELECT, x - panel.width / 3 , y + 3 * panel.getImage().getHeight() / 10);
        
        updateEveryImage();
        
    }
    
    /**
     * Calls the resetImage() method of every InputPanel.
     */
    public static void updateEveryImage(){
        for(InputPanel panel : MyWorld.theWorld.getObjects(InputPanel.class)){
            panel.resetImage();
        }
    }

    abstract String getValue();
    
}
    
}
