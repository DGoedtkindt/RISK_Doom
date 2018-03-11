package base;

import appearance.Appearance;
import appearance.MessageDisplayer;
import greenfoot.GreenfootImage;
import greenfoot.Color;

/**
 * The class of the Buttons that aren't ModeButtons.
 */
public class NButton extends Button{
    
    private Action action;
    
    /**
     * NButton with no image
     * @param act the action the button performs when clicked
     */
    public NButton(Action act) {
        action = act;
        
    }
    
    /**
     * NButton with a custom image and scale
     * 
     * @param act the action the button performs when clicked
     * @param img the image of the Button
     * @param width the width to which the image will be scaled
     * @param height the height to which the image will be scaled
     */
    public NButton(Action act, GreenfootImage img, int width, int height){
        this.setImage(img);
        this.getImage().scale(width, height);
        action = act;
        
    }
    
    /**
     * NButton initialized with an image scaled to default size : 80x80 
     * 
     * @param act the action the button performs when clicked
     * @param img the image of the button
     */
    public NButton(Action act, GreenfootImage img){
        this.setImage(img);
        this.getImage().scale(80, 80);
        action = act;
        
    }
    
    /**
     * NButton with a long image background (175x80) and a small text 
     * 
     * @param act the action the button performs when clicked
     * @param txt the text displayed on the button
     */
    public NButton(Action act, String txt){
        GreenfootImage img = new GreenfootImage("button11-5.png");
        this.setImage(img);
        this.getImage().scale(175, 80);
        GreenfootImage txtImg = new GreenfootImage(txt,20,Color.BLACK,new Color(0,0,0,0));
        int xPos = (175-txtImg.getWidth())/2;
        int yPos = (80-txtImg.getHeight())/2;
        this.getImage().drawImage(txtImg, xPos, yPos);
        action = act;
        
    }
    
    @Override
    public void clicked() {
        if(action != null && isUsable()){
            action.act();
        }else MessageDisplayer.showMessage("This button does nothing");
        
    }
    
    @Override
    public void makeTransparent() {
        getImage().setTransparency(Appearance.TRANSPARENT);
    
    }
    
    @Override
    public void makeOpaque() {
        getImage().setTransparency(Appearance.OPAQUE);
    
    }
    
    /**
     * Checks if this ModeButton can be used in the current situation. If the Button 
     * is opaque, then it can be used.
     * @return A boolean representation of the fact that this ModeButton
     *         can be used in the current situation.
     */
    private boolean isUsable(){
        return this.getImage().getTransparency() == Appearance.OPAQUE;
        
    }
    
}