package appearance;

import base.Button;
import base.InputPanelUser;
import base.MyWorld;
import base.NButton;
import greenfoot.GreenfootImage;
import java.awt.FontMetrics;

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
        CONFIRM;
    }
    
    public static InputPanel usedPanel = null;
    
    public static String YES_OPTION = "yes";
    public static String NO_OPTION = "no";
    
    private final InputPanelUser source;
    private final InputType inputType;
    
    private String displayedString = "";
    private String name = "";
    private final String type;
    private int width;
    private int lineHeight;
    
    private NButton yes = new NButton(() -> {
                                                displayedString = YES_OPTION;
                                                close();
                                            }, "Yes");
    private NButton no = new NButton(() ->  {
                                                displayedString = NO_OPTION;
                                                close();
                                            }, "No");
    
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
        
        if(letter.equals("backspace") && displayedString.length() != 0){
            displayedString = displayedString.substring(0, displayedString.length() - 1);
        }else if(letter.equals("enter")){
            close();
        }else if(letter.equals("space")){
            displayedString += " ";
        }else if(letter.length() == 1){
            displayedString += letter;
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
        
        //Scales the image differently according to the InputType of the Panel, 
        //and draws the typed String on the image if the InputType is InputType.INSERT
        if(inputType == InputType.CONFIRM){
            
            img.scale(width + 10, (linesNumber + 1) * lineHeight + 90);
            
        }else if(inputType == InputType.INSERT){
            
            img.scale(width + 10, (linesNumber + 1) * lineHeight + 5);
            img.drawString(displayedString, 5, (linesNumber + 1) * lineHeight);
            
        }
        
        //Writes the name of this Panel on the image.
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
            source.useInformations(displayedString, type);
        } catch (Exception ex) {
            MessageDisplayer.showException(ex);
        }
    }
    
    /**
     * Removes this from the World.
     */
    public void destroy(){
        MyWorld.theWorld.removeObject(this);
        MyWorld.theWorld.removeObject(yes);
        MyWorld.theWorld.removeObject(no);
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
        MyWorld.theWorld.addObject(panel.yes, x - panel.getImage().getWidth() / 4, y + panel.getImage().getHeight() / 2 - panel.yes.getImage().getHeight() / 2);
        MyWorld.theWorld.addObject(panel.no, x + panel.getImage().getWidth() / 4, y + panel.getImage().getHeight() / 2 - panel.yes.getImage().getHeight() / 2);
    }
    
    /**
     * Calls the resetImage() method of every InputPanel.
     */
    private static void updateEveryImage(){
        for(InputPanel panel : MyWorld.theWorld.getObjects(InputPanel.class)){
            panel.resetImage();
        }
    }
    
}
