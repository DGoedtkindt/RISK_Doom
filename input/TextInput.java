package input;

import appearance.Appearance;
import base.Button;
import base.GColor;
import base.MyWorld;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

/**
 *  TextInputs are input for text...
 * 
 */
public class TextInput extends Input {
    
    private final String TITLE;
    private final InputActor inputActor;
    private String returnString = "";
    private TextInput thisTextInput;
    
    /** Create a new TextInput
     * 
     * @param title The text that will be displayed as Title 
     */
    public TextInput(String title) {
        TITLE = title;
        thisTextInput = this;
        inputActor = new InputActor(WIDTH,HEIGHT);
    
    }

    @Override
    void addToWorld(int xPos, int yPos) {
        MyWorld.theWorld.addObject(inputActor, xPos, yPos);
        
    }

    @Override
    void removeFromWorld() {
        MyWorld.theWorld.removeObject(inputActor);
        if(activeInput == this) activeInput = null;
        
    }

    @Override
    public String value() {
        return returnString;
    }
    
    @Override
    protected void submit() {
        super.submit();
        inputActor.resetImage();
        
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
                this.getImage().setFont(Appearance.GREENFOOT_FONT);
            }
            
            void resetImage() {
                //fill the background
                Color backgroundColor = appearance.Theme.used.backgroundColor;
                this.getImage().setColor(backgroundColor);
                this.getImage().fill();
                
                //add border if active
                if(thisTextInput == Input.activeInput) {
                    this.getImage().setColor(GColor.WHITE);
                    this.getImage().drawRect(1, 1, WIDTH-2, HEIGHT-2);
                }
                
                //add the texts
                //title
                int titleXPos = WIDTH/2 - 12*TITLE.length();
                int titleYPos = HEIGHT/4;
                getImage().drawString(TITLE, titleXPos,titleYPos );
                
                //returnString
                int rsXPos = WIDTH/2 - 12*returnString.length();
                int rsYPos = HEIGHT/2;
                getImage().drawString(returnString, rsXPos,rsYPos );
                
            }
            
            @Override
            public void act() {
                if(thisTextInput == Input.activeInput) {
                    manageKeyPressed();
                    resetImage();
                }
            
            }
            
            private void manageKeyPressed() {
                String keyPressed = Greenfoot.getKey();
                if(keyPressed != null){
                    if(keyPressed.equals("backspace") && returnString.length() != 0)
                        returnString = returnString.substring(0, returnString.length() - 1);
                    else if(keyPressed.equals("enter")) 
                        submit();
                    else if(keyPressed.equals("space"))
                        returnString += " ";
                    else if(keyPressed.length() == 1)
                        returnString += keyPressed;
                }
            
            }

            @Override
            public void clicked() {
                Input.activeInput.submit();
                Input.activeInput = thisTextInput;
            }


        }

}