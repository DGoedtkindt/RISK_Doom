package input;

import base.Button;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

/**
 *  TextInputs are input for text...
 * 
 */
public class TextInput extends Input {
    
    private final String TITLE;
    private String returnedString = "";
    
    /** Create a new TextInput
     * 
     * @param title The text that will be displayed as Title 
     */
    public TextInput(String title) {
        TITLE = title;
    
    }
        
    /**
     * Types the given letter on this InputPanel.
     * @param letter The letter to type.
     */
    private void type(String letter){
        
        if(letter.equals("backspace") && returnedString.length() != 0){
            returnedString = returnedString.substring(0, returnedString.length() - 1);
        }else if(letter.equals("space")){
            returnedString += " ";
        }else if(letter.length() == 1){
            returnedString += letter;
        }

        resetImage();
        
    }

    @Override
    void addToWorld(int xPos, int yPos) {
        System.out.println("Method addToWorld() in class TextInput is not supported yet");
    }

    @Override
    void removeFromWorld() {
        System.out.println("Method removeFromWorld() in class TextInput is not supported yet");
    }

    @Override
    String getValue() {
        System.out.println("Method getValue() in class TextInput is not supported yet");
        return null;
    }

    
        /**
         * just an actor with a certain image that serves as a background.
         */
        private class InputActor extends Button {
            final int WIDTH;
            final int HEIGHT;

            private InputActor(int width, int height) {
                WIDTH = width; HEIGHT = height;
                this.setImage(new GreenfootImage(WIDTH,HEIGHT));
            }
            
            void resetImage() {
                Color backgroundColor = appearance.Theme.used.backgroundColor;
                this.getImage().setColor(backgroundColor);
                this.getImage().fill();
                int rsXPos = WIDTH/2 - 12*returnedString.length();
                int rsYPos = HEIGHT/2;
                getImage().drawString(returnedString, rsXPos,rsYPos );
                
            }
            
            @Override
            public void act() {
                if(Greenfoot.isKeyDown("Enter")) submit();
            
            }

            @Override
            public void clicked() {
                System.out.println("Method clicked() in class Background is not supported yet");
            }


        }

}
