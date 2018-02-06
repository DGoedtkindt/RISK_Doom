package mode;

import appearance.Theme;
import base.MyWorld;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import greenfoot.Actor;
import greenfoot.Font;
import greenfoot.GreenfootImage;


public class ModeMessageDisplay {
    final int WIDTH = 182;
    final int HEIGHT = 330;
    
    private MyWorld world() {return MyWorld.theWorld;}
    
    private class JustAnActor extends Actor {}
    private JustAnActor panel = new JustAnActor();
    
    private ActionListener updateThis = (ActionEvent ae)-> {display(Mode.mode().message);};
    
    public ModeMessageDisplay() {
        display("");
        
    }
    
    public void addToWorld(int xPos, int yPos) {
        world().addObject(panel, xPos, yPos);
        Mode.addModeChangeListener(updateThis);
        
    }
    
    public void removeFromWorld() {
        world().removeObject(panel);
        Mode.removeModeChangeListener(updateThis);
        
    }

    private void display(String message){
        panel.setImage(new GreenfootImage(WIDTH,HEIGHT));
        Font font = new Font("Monospaced", 17);
        String textToDisplay = wrapText(message, 16);
        panel.getImage().setColor(Theme.used.textColor);
        panel.getImage().setFont(font);
        panel.getImage().drawString(textToDisplay, 0, 0);

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

