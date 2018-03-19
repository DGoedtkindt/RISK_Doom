package basicChoosers;

import base.GColor;
import base.Hexagon;
import java.util.ArrayList;

/**
 * A Chooser designed to let the user select a Color for a Player.
 * 
 */
public class PColorChooser extends BasicChooser {
    
    private int colorNum;
    private Color currentColor;
    
    public PColorChooser() {
        if(!enoughFreeColor()) throw new UnsupportedOperationException("Too many Color Choices blocked");
        colorNum = -1;
        
        next();
        
    }
    
    /**
     * Liberates every Color choice of the Choice List.
     */
    public void clearChoices(){
        freeAll();
    }

    @Override
    protected void updateImage() {
        GColor drawColor = currentColor.gColor;
        this.setImage(Hexagon.createImage(drawColor, 0.5)); 
    }

    @Override
    public String choiceValue() {
        return currentColor.rgbCode(); 
    }

    @Override
    public void next() {
        if(colorNum >= Color.numOfColors()-1) colorNum = 0;
        else colorNum++;
        if(isFree()) {
            updateColor();
        
        } else {
            next();
            
        }    
    }

    @Override
    public void previous() {
        if(colorNum <= 0) colorNum = Color.numOfColors()-1;
        else colorNum--;
        if(isFree()) {
            updateColor();
        
        } else {
            previous();
            
        }    
    }

    @Override
    protected void liberateChoice() {
        currentColor.free();
    }
    
     /**
     * Checks whether enough colors are available.
     * @return A boolean representation of this fact.
     */
    private static boolean enoughFreeColor() {
        return Color.usedColors.size() + 1 < Color.numOfColors();
    }
    
    /**
     * Checks whether the chosen color is free.
     * @return A boolean representation of this fact.
     */
    private boolean isFree() {
        return Color.get(colorNum).isFree();
    }
    
    /**
     * Updates the chosen color.
     */
    private void updateColor() {
        Color previousColor = currentColor;
        currentColor = Color.get(colorNum);
        if(previousColor != null) previousColor.free();
        currentColor.block();
        this.updateImage();
    
    }
    
     /**
     * Liberates every Choice.
     */
    void freeAll(){
        for(Color c : Color.values()){
            c.free();
        }
    }
    
    /**
     * The actual Color list.
     */
    private static enum Color {
        ZERO (new GColor(255, 30, 30)),
        ONE (new GColor(30, 255, 30)),
        TWO (new GColor(30, 30, 255)),
        THREE (new GColor(255, 225, 30)),
        FOUR (new GColor(225, 30, 255)),
        FIVE (new GColor(30, 225, 255)),
        SIX (new GColor(245, 245, 245)),
        SEVEN (new GColor(100, 100, 100)),
        EIGHT (new GColor(180, 180, 180));
        
        final GColor gColor;
       
        static ArrayList<Color> usedColors = new ArrayList<Color>();
        
        /**
         * Gets the colorNum-th Color of the list.
         * @return The said Color.
         */
        static Color get(int colorNum) {
            return values()[colorNum];
        
        }
        
        /**
         * Calculates the size of the list.
         * @return The size of the list.
         */
        static int numOfColors() {
            return values().length;
        }
        
        /**
         * Lets a Color be used.
         */
        void free() {
            usedColors.remove(this);
        }
        
        /**
         * Blocks a Color choice of the list.
         */
        void block() {
            usedColors.add(this);
        }
        
        /**
         * Checks if the Color can be used.
         * @return A boolean representation of this fact.
         */
        boolean isFree() {
            return !usedColors.contains(this);
        
        }
        
        /**
         * Creates a choice Object.
         */
        Color(GColor color) { 
           this.gColor = color;
        }
        
        /**
         * Obtains the String representation of the Color.
         * @return The said String representation.
         */
        String rgbCode() {return gColor.toRGB();}
        
    }
    
}
