package mode;

import appearance.Theme;
import base.Action;
import base.MyWorld;
import greenfoot.Actor;
import greenfoot.Font;
import greenfoot.GreenfootImage;


public class ModeMessageDisplay extends Actor{
    final int WIDTH = 182;
    final int HEIGHT = 330;
    
    private MyWorld world() {return MyWorld.theWorld;}
    
    private Action updateThis = () -> {display(Mode.mode().message);};
    
    public ModeMessageDisplay() {
        display("");
        
    }
    
    public void addToWorld(int xPos, int yPos) {
        world().addObject(this, xPos, yPos);
        Mode.addModeChangeListener(updateThis);
        
    }
    
    public void removeFromWorld() {
        world().removeObject(this);
        Mode.removeModeChangeListener(updateThis);
        
    }

    private void display(String message){
        setImage(new GreenfootImage(WIDTH, HEIGHT));
        Font font = new Font("Monospaced", 17);
        String textToDisplay = wrapText(message, 16);
        getImage().setColor(Theme.used.textColor);
        getImage().setFont(font);
        getImage().drawString(textToDisplay, 0, 0);

    }

    private String wrapText(String strToWrap, int maxLineLength) {

        String[] words = strToWrap.split(" ");
        String finalString = "\n";

        String currentLine = "";
        for(String currentWord : words){
            if((currentLine + currentWord).length() > maxLineLength){
                finalString += currentLine + "\n";
                currentLine = "";

            }
            currentLine += " " + currentWord;

        }
        finalString += currentLine;

        return finalString;

    }

}

