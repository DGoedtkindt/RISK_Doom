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
    private Background background;
    private final Slider redSlider;
    private final Slider greenSlider;
    private final Slider blueSlider;
    private final ColorInput thisColorInput;
    
    public ColorInput(String title) {
        TITLE = title;
        thisColorInput = this;
        background = new Background(WIDTH,HEIGHT);
        redSlider = new Slider(GColor.RED);
        greenSlider = new Slider(GColor.GREEN);
        blueSlider = new Slider(GColor.BLUE);
        
    }
    
    @Override
    void addToWorld(int xPos, int yPos) {
        MyWorld.theWorld.addObject(background, xPos, yPos);
        MyWorld.theWorld.addObject(redSlider, xPos-80, yPos);
        MyWorld.theWorld.addObject(greenSlider, xPos, yPos);
        MyWorld.theWorld.addObject(blueSlider, xPos+80, yPos);
    }

    @Override
    void removeFromWorld() {
        MyWorld.theWorld.removeObject(background);
        MyWorld.theWorld.removeObject(redSlider);
        MyWorld.theWorld.removeObject(greenSlider);
        MyWorld.theWorld.removeObject(blueSlider);
        if(activeInput == this) activeInput = null;
    }

    @Override
    public String value() {
        GColor color = new GColor(redSlider.value(),greenSlider.value(),blueSlider.value());
        return color.toRGB();
    }
    
    /**
     * just an actor with a certain image that serves as a background.
     */
    private class Background extends Actor {
        private Background(int width, int height) {
            Color backgroundColor = appearance.Theme.used.backgroundColor.brighter();
            this.setImage(new GreenfootImage(width,height));
            this.getImage().setColor(backgroundColor);
            this.getImage().fill();
            this.getImage().setColor(appearance.Theme.used.textColor);
            this.getImage().drawString(TITLE, 30, 40);
        }

        @Override
        public void act() {
            Color color = GColor.fromRGB(value());
            GreenfootImage hex = Hexagon.createImage(color);
            hex.scale(120, 120);
            
            this.getImage().drawImage(hex, 50, 40);
            
            if(Greenfoot.isKeyDown("Enter"))
            if(Input.activeInput == thisColorInput)submit();

        }


    }
    
    /**
    * A Slider is a slider that allows the User to select a certain Color value.
    */
    class Slider extends Button{

        public static final int WIDTH = 80;
        private static final int HEIGHT = 40;

        private Color color;
        public int x;
        private int baseY;
        public int y;
        
        Slider(Color c) {
           color = c;
            
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
                }
                
                createImage();
            }

            setLocation(x, y);

        }

        @Override
        public void clicked() {
            if(Input.activeInput!= null && Input.activeInput!= thisColorInput)
                    Input.activeInput.submit();
                Input.activeInput = thisColorInput;
        }
    }

}
