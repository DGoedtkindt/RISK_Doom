package input;

import base.Button;
import base.GColor;
import base.Hexagon;
import base.MyWorld;
import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public class ColorInput extends Input{
    
    private final String TITLE;
    private final Background BACKGROUND;
    private final Slider redSlider;
    private final Slider greenSlider;
    private final Slider blueSlider;
    private final ColorInput thisColorInput;
    
    public ColorInput(String title) {
        TITLE = title;
        thisColorInput = this;
        BACKGROUND = new Background(WIDTH,HEIGHT);
        redSlider = new Slider(new GColor(255,0,0));
        greenSlider = new Slider(new GColor(0,255,0));
        blueSlider = new Slider(new GColor(0,0,255));
        
    }
    
    @Override
    void addToWorld(int xPos, int yPos) {
        MyWorld.theWorld.addObject(BACKGROUND, xPos, yPos);
        redSlider.addToWorld(xPos, yPos-HEIGHT/4);
        greenSlider.addToWorld(xPos, yPos);
        blueSlider.addToWorld(xPos, yPos+HEIGHT/4);
    }

    @Override
    void removeFromWorld() {
        MyWorld.theWorld.removeObject(BACKGROUND);
        redSlider.removeFromWorld();
        greenSlider.removeFromWorld();
        blueSlider.removeFromWorld();
        if(activeInput == this) activeInput = null;
    }

    @Override
    public String value() {
        GColor color = new GColor((int)(redSlider.value()*255),(int)(greenSlider.value()*255),(int)(blueSlider.value()*255));
        return color.toRGB();
    }
    
    /**
     * just an actor with a certain image that serves as a background.
     */
    private class Background extends Actor {
        
        private Background(int width, int height) {
            Color backgroundColor = appearance.Theme.used.backgroundColor.brighter();
            setImage(new GreenfootImage(width,height));
            getImage().setColor(backgroundColor);
            getImage().fill();
            getImage().setColor(appearance.Theme.used.textColor);
            getImage().setFont(appearance.Appearance.GREENFOOT_FONT);
            getImage().drawString(TITLE, 30, 30);
            
            //lines for sliders
            int xStart = WIDTH/2;
            int length = WIDTH/3;
            getImage().setColor(GColor.BLACK);
            getImage().drawLine(xStart, HEIGHT/4, xStart + length, HEIGHT/4);
            getImage().drawLine(xStart, HEIGHT/2, xStart + length, HEIGHT/2);
            getImage().drawLine(xStart, 3*HEIGHT/4, xStart + length, 3*HEIGHT/4);
        }

        @Override
        public void act() {
            Color color = GColor.fromRGB(value());
            GreenfootImage hex = Hexagon.createImage(color);
            hex.scale(120, 120);
            
            getImage().drawImage(hex, WIDTH/4 - 60, 60);
            
            if(Greenfoot.isKeyDown("Enter"))
            if(Input.activeInput == thisColorInput)submit();

        }


    }
    
    /**
    * A Slider is a slider that allows the User to select a certain Color value.
    */
    class Slider extends Button{

        static final int SIZE = 40;
        static final int LENGTH = WIDTH/3; 

        private final GColor COLOR;
        private int minX = 0;
        private int maxX = 1;
        private int x = 0;
        private int y = 1;
        
        Slider(GColor c) {
           COLOR = c;
           createImage();
            
        }

        /**
         * Creates the image of this Slider.
         */
        private void createImage() {
            GreenfootImage img = new GreenfootImage(SIZE,SIZE);
            img.setColor(GColor.BLACK);
            img.fillOval(0, 0, SIZE, SIZE);
            img.setColor(COLOR.multiply(value()));
            img.fillOval(2, 2, SIZE-4, SIZE-4);
            setImage(img);

        }
        
        void addToWorld(int xPos, int yPos) {
            minX = xPos; maxX = xPos + LENGTH;
            x = xPos; y = yPos;
            MyWorld.theWorld.addObject(this, xPos, yPos);
        
        }
        
        void removeFromWorld() {
            MyWorld.theWorld.removeObject(this);
            
        }

        /**
         * Computes the Color value represented by this Slider.
         * @return The Color value represented by this Slider, from 0. to 1.
         */
        public double value(){
            return (double)(x-minX)/(double)(maxX - minX);
            
        }

        @Override
        public void act(){

            if(Greenfoot.mouseDragged(this)){
                int newX = Greenfoot.getMouseInfo().getX();
                newX = Math.max(Math.min(newX, maxX), minX);
                x = newX;
                setLocation(x, y);
                createImage();
                
            }
        }

        @Override
        public void clicked() {
            if(Input.activeInput!= null && Input.activeInput!= thisColorInput)
                    Input.activeInput.submit();
                Input.activeInput = thisColorInput;
        }
    }

}
