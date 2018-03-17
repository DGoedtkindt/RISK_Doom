package input;

import base.GColor;
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
    
    public ColorInput(String title) {
        TITLE = title;
        background = new Background(WIDTH,HEIGHT);
        background.container = this;
        redSlider = new Slider(GColor.RED);
        greenSlider = new Slider(GColor.GREEN);
        blueSlider = new Slider(GColor.BLUE);
        
    }
    
    @Override
    void addToWorld(int xPos, int yPos) {
        System.out.println("Method addToWorld() in class ColorInput is not supported yet");
    }

    @Override
    void removeFromWorld() {
        System.out.println("Method removeFromWorld() in class ColorInput is not supported yet");
    }

    @Override
    public String value() {
        System.out.println("Method getValue() in class ColorInput is not supported yet");
        return null;
    }
    
    /**
     * just an actor with a certain image that serves as a background.
     */
    private class Background extends Actor {
        Input container;
        
        private Background(int width, int height) {
            Color backgroundColor = appearance.Theme.used.backgroundColor;
            this.setImage(new GreenfootImage(width,height));
            this.getImage().setColor(backgroundColor);
            this.getImage().fill();
        }

        @Override
        public void act() {
            if(Greenfoot.isKeyDown("Enter"))
            if(Input.activeInput == container)submit();

        }


    }
    
    /**
    * A Slider is a slider that allows the User to select a certain Color value.
    */
    class Slider extends Actor{

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
         * Adds this Slider to the World.
         */
        public void addToWorld(int x,int BaseY){
            MyWorld.theWorld.addObject(this, x, y);
        }

        /**
         * Removes this Slider form the World.
         */
        public void remove(){
            MyWorld.theWorld.removeObject(this);
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

            }

            setLocation(x, y);

        }
}
