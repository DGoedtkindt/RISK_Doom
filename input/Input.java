package input;

import base.Action;

/**
 * An Input is an Object that allow to enter some information. It can be 
 * added to a Form that will display it and use it's information.
 * 
 */
public abstract class Input {
    
    public static final int HEIGHT = 180;
    public static final int WIDTH = appearance.Appearance.WORLD_WIDTH;
    protected static Input activeInput;
    
    /**
     * action this Input is going to perform when submit is called
     */
    Action onSubmitAction;
    
    abstract void addToWorld(int xPos, int yPos);
    abstract void removeFromWorld();
    
    /** Gets the value this Input stores.
     * @return the value that was inputed. Must return "" if nothing
     *      was inputed
     */
    public abstract String value();
    
    /** Performs the on submit action attributed to this Input.
     * 
     */
    protected void submit() {
        if(onSubmitAction != null) onSubmitAction.act();
        if(activeInput == this) activeInput = null;
    
    }
    
    
    
    /**
     * Resets the image of this InputPanel in order to update its display.
     */
    /*
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
        
    }*/
    
}
