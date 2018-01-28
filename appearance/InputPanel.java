package appearance;

import base.Button;
import base.MyWorld;
import greenfoot.Font;
import greenfoot.GreenfootImage;

public class InputPanel extends Button {

    public static InputPanel usedPanel = new InputPanel("", 0);
    
    private String displayedString = "";
    private String name = "";
    private int width;
    
    public InputPanel(String infoName, int size){
        name = infoName + " : ";
        width = size;
        resetImage();
    }
    
    @Override
    public void clicked() {
        usedPanel = this;
    }
    
    public void type(String letter){
        
        if(letter.equals("backspace") && displayedString.length() != 0){
            displayedString = displayedString.substring(0, displayedString.length() - 1);
        }else if(letter.length() == 1){
            displayedString += letter;
        }

        resetImage();
        
    }
    
    private final Font FONT = new Font("monospaced", 30);
    
    private void resetImage(){
        
        GreenfootImage img = new GreenfootImage(width + name.length() * 20, FONT.getSize());
        
        img.setColor(Theme.used.backgroundColor.brighter());
        img.fill();
        
        img.setFont(FONT);
        img.setColor(Theme.used.textColor);
        img.drawString(name + displayedString, 10, img.getHeight() - 10);
        
        setImage(img);
        
    }
    
    public String close(){
        MyWorld.theWorld.removeObject(this);
        return displayedString;
    }
    
}
