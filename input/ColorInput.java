package input;

import base.GColor;
import base.MyWorld;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;


public class ColorInput extends Input{

    @Override
    void addToWorld(int xPos, int yPos) {
        System.out.println("Method addToWorld() in class ColorInput is not supported yet");
    }

    @Override
    void removeFromWorld() {
        System.out.println("Method removeFromWorld() in class ColorInput is not supported yet");
    }

    @Override
    String getValue() {
        System.out.println("Method getValue() in class ColorInput is not supported yet");
        return null;
    }
    
    /**
    * A Slider is a slider that allows the User to select a certain Color value.
    */
    class Slider extends Actor{

        public static final int WIDTH = 80;
        private static final int HEIGHT = 40;

        private final GColor color;
        public final int x;
        private final int baseY;
        public int y;

        /**
         * Creates a Slider.
         * @param xPos The x coordinate of this Slider.
         * @param yPos The starting y coordinate of this Slider.
         * @param c The Color represented by this Slider.
         */
        public Slider(int xPos, int yPos, GColor c){
            x = xPos;
            baseY = yPos;
            y = baseY;
            color = c;
            createImage();
        }

        /**
         * Adds this Slider to the World.
         */
        public void show(){
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
                    InputPanel.updateEveryImage();
                }

            }

            setLocation(x, y);

        }
}
