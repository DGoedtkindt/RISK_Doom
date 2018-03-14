package input;

import appearance.Appearance;
import appearance.MessageDisplayer;
import appearance.Theme;
import base.Button;
import base.GColor;
import base.Hexagon;
import base.InputPanelUser;
import base.MyWorld;
import base.NButton;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;
import java.util.Arrays;
import java.util.List;

/**
 * An InputPanel lets the User enter informations during processes that require it.
 * 
 */
public class InputPanel extends Button {
    
    /**
     * An enum containing the two types of inputs.
     */
    private enum InputType{
        INSERT,
        CONFIRM,
        COLOR;
    }
    
    public static InputPanel usedPanel = null;
    
    public static String YES_OPTION = "yes";
    public static String NO_OPTION = "no";
    
    private static final int COLOR_CHOOSER_HEIGHT = 300;
    
    private final InputPanelUser source;
    private final InputType inputType;
    
    private String returnedString = "";
    private String name = "";
    private final String type;
    private int width;
    private int lineHeight;
    
    private static Slider red = new Slider(0, 0, new GColor(0, 0, 0));
    private static Slider green = new Slider(0, 0, new GColor(0, 0, 0));
    private static Slider blue = new Slider(0, 0, new GColor(0, 0, 0));
    
    private static final NButton YES = new NButton(() -> {
                                                usedPanel.returnedString = YES_OPTION;
                                                usedPanel.close();
                                            }, "Yes");
    
    private static final NButton NO = new NButton(() ->  {
                                                usedPanel.returnedString = NO_OPTION;
                                                usedPanel.close();
                                            }, "No");
    
    private static final NButton SELECT = new NButton(() -> {
                                                usedPanel.returnedString = (new GColor(red.value(), green.value(), blue.value()).toRGB());
                                                usedPanel.close();
                                            }, "Select");
    
    public static List<NButton> InputPanelButtons = Arrays.asList(new NButton[]{YES, NO, SELECT});
    
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
    
    /**
     * Types the given letter on the used InputPanel, if it's not a null Panel 
     * and if it's type is IputType.INSERT.
     * @param letter The letter to type.
     */
    public static void typeOnUsedPanel(String letter){
        if(usedPanel != null){
            if(usedPanel.inputType == InputType.INSERT){
               usedPanel.type(letter); 
            }
        }
        
    }
    
    /**
     * Types the given letter on this InputPanel.
     * @param letter The letter to type.
     */
    private void type(String letter){
        
        if(letter.equals("backspace") && returnedString.length() != 0){
            returnedString = returnedString.substring(0, returnedString.length() - 1);
        }else if(letter.equals("enter")){
            close();
        }else if(letter.equals("space")){
            returnedString += " ";
        }else if(letter.length() == 1){
            returnedString += letter;
        }

        resetImage();
        
    }
    
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
        MyWorld.theWorld.removeObject(this);
        MyWorld.theWorld.removeObjects(InputPanelButtons);
        MyWorld.theWorld.removeObject(red);
        MyWorld.theWorld.removeObject(green);
        MyWorld.theWorld.removeObject(blue);
        usedPanel = null;
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
        
        red = new Slider(x - panel.getImage().getWidth() / 6 + (2 * panel.getImage().getWidth() / 3 - 3 * Slider.WIDTH) / 4 + (int)(0.4 * Slider.WIDTH),
                         y + panel.getImage().getHeight() / 2 - (COLOR_CHOOSER_HEIGHT - 256) / 2, new GColor(255, 0, 0));
        green = new Slider(x - panel.getImage().getWidth() / 6 + 2 * (2 * panel.getImage().getWidth() / 3 - 3 * Slider.WIDTH) / 4 + (int)(1.4 * Slider.WIDTH),
                           y + panel.getImage().getHeight() / 2 - (COLOR_CHOOSER_HEIGHT - 256) / 2, new GColor(255, 0, 0));
        blue = new Slider(x - panel.getImage().getWidth() / 6 + 3 * (2 * panel.getImage().getWidth() / 3 - 3 * Slider.WIDTH) / 4 + (int)(2.4 * Slider.WIDTH),
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
    
}

/**
 * A Slider is a slider that allows the User to select a certain Color value.
 */
class Slider extends Actor{
    
    public static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    
    private final GColor color;
    public final int x;
    private final int baseY;
    public int y;
    
    /**
     * Creates a Slider.
     * @param xPos The x coordinate of this Slider.
     * @param yPos The starting y coordinate of this Slider.
     * @param c The Color represented by this Slider.
     */
    public Slider(int xPos, int yPos, GColor c){
        x = xPos;
        baseY = yPos;
        y = baseY;
        color = c;
        createImage();
    }
    
    /**
     * Adds this Slider to the World.
     */
    public void show(){
        MyWorld.theWorld.addObject(this, x, y);
    }
    
    /**
     * Removes this Slider form the World.
     */
    public void remove(){
        MyWorld.theWorld.removeObject(this);
    }
    
    /**
     * Creates the image of this Slider.
     */
    private void createImage() {
        
        GreenfootImage img = new GreenfootImage(WIDTH, HEIGHT);
        img.setColor(color);
        img.fillPolygon(new int[]{0, 2 * WIDTH / 3, WIDTH     , 2 * WIDTH / 3, 0     }, 
                        new int[]{0, 0            , HEIGHT / 2, HEIGHT       , HEIGHT}, 5);
        
        img.setColor(GColor.BLACK);
        
        img.drawPolygon(new int[]{0, 2 * WIDTH / 3, WIDTH     , 2 * WIDTH / 3, 0     }, 
                        new int[]{0, 0            , HEIGHT / 2, HEIGHT       , HEIGHT}, 5);
        
        setImage(img);
        
    }
    
    /**
     * Computes the Color value represented by this Slider.
     * @return The Color value represented by this Slider, from 0 to 255.
     */
    public int value(){
        return baseY - y;
    }
    
    @Override
    public void act(){
        
        if(Greenfoot.mouseDragged(this)){
            
            int newY = Greenfoot.getMouseInfo().getY();
            
            if(baseY - newY > 0 && baseY - newY < 256){
                y = newY;
                InputPanel.updateEveryImage();
            }
            
        }
        
        setLocation(x, y);
        
    }
    
}
