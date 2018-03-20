package base;

import greenfoot.GreenfootImage;

/**
 * The class of the Buttons that aren't ModeButtons.
 */
public class NButton extends Button{
    
    private Action action;
    
    /**
     * NButton with no image.
     * @param act The Action this Button performs when clicked.
     */
    public NButton(Action act) {
        action = act;
        
    }
    
    /**
     * NButton with a custom image and scale.
     * 
     * @param act The action this Button performs when clicked.
     * @param img The image of this Button.
     * @param width The width to which the image will be scaled.
     * @param height The height to which the image will be scaled.
     */
    public NButton(Action act, GreenfootImage img, int width, int height){
        this.setImage(img);
        this.getImage().scale(width, height);
        action = act;
        
    }
    
    /**
     * NButton initialized with an image scaled to default size : 80x80.
     * 
     * @param act The Action this Button performs when clicked.
     * @param img The image of this Button.
     */
    public NButton(Action act, GreenfootImage img){
        this(act,img,80,80);
        
    }
    
    /**
     * NButton with a long image background (175x80) and a small text 
     * 
     * @param act The Action this Button performs when clicked.
     * @param txt The text displayed on this Button.
     */
    public NButton(Action act, String txt){
        GreenfootImage img = createImage(txt);
        this.setImage(img);
        action = act;
        
    }
    
    /**
     * Creates an image with a given text.
     * @param text The specified text.
     */
    private GreenfootImage createImage(String text) {
        GreenfootImage img = new GreenfootImage("button11-5.png");
        this.setImage(img);
        this.getImage().scale(175, 80);
        GreenfootImage txtImg = new GreenfootImage(text,20,GColor.BLACK,new GColor(0,0,0,0));
        int xPos = (175-txtImg.getWidth())/2;
        int yPos = (80-txtImg.getHeight())/2;
        this.getImage().drawImage(txtImg, xPos, yPos);
        return img;
    }
    
    @Override
    public void clicked() {
        if(action != null && isUsable()){
            action.act();
        }
        
    }
    
}
