package basicChoosers;

import greenfoot.GreenfootImage;
import base.GColor;
import base.Hexagon;
import java.util.ArrayList;

public class PColorChooser extends BasicChooser {

    public PColorChooser() {
        super(new PColorChoices());
    }

}

class PColorChoices extends ChoiceList{
    
    private static enum Color {
        ZERO (new GColor(0,0,0)),
        ONE (new GColor(30,30,30)),
        TWO (new GColor(60,60,60)),
        THREE (new GColor(90,90,90)),
        FOUR (new GColor(120,120,120)),
        FIVE (new GColor(150,150,150)),
        SIX (new GColor(180,180,180)),
        SEVEN (new GColor(210,210,210)),
        EIGHT (new GColor(240,240,240));
        
        final GColor gColor;
       
        static ArrayList<Color> usedColors = new ArrayList<Color>();
        
        static Color get(int colorNum) {
            return values()[colorNum];
        
        }
        
        static int numOfColors() {
            return values().length;
        }
        
        void free() {
            usedColors.remove(this);
        }
        
        void block() {
            usedColors.add(this);
        }
        
        boolean isFree() {
            return !usedColors.contains(this);
        
        }
        
        Color(GColor color) { 
           this.gColor = color;
        }
        
        String rgbCode() {return gColor.toRGB();}
    
    }
    
    private int colorNum;
    private Color currentColor;
    
    public PColorChoices() {
        if(!enoughFreeColor()) throw new UnsupportedOperationException("To many Color Choices blocked");
        colorNum = -1;
        
        next();
    
    }
    
    @Override
    protected void next() {
        if(colorNum >= Color.numOfColors()-1) colorNum = 0;
        else colorNum++;
        if(isFree()) {
            updateColor();
        
        } else {
            next();
            
        }
        
    }

    @Override
    protected void previous() {
        if(colorNum <= 0) colorNum = Color.numOfColors()-1;
        else colorNum--;
        if(isFree()) {
            updateColor();
        
        } else {
            previous();
            
        }
    }

    @Override
    protected GreenfootImage choiceImage() {
        GColor drawColor = currentColor.gColor;
        return Hexagon.createImage(drawColor, 0.5);
    }

    @Override
    public String choiceValue() {
        return currentColor.rgbCode();
    }

    @Override
    protected void liberateChoice() {
       currentColor.free();
    }
    
    private static boolean enoughFreeColor() {
        return Color.usedColors.size() + 1 < Color.numOfColors();
    }
    
    private boolean isFree() {
        return Color.get(colorNum).isFree();
    }
    
    private void updateColor() {
        Color previousColor = currentColor;
        currentColor = Color.get(colorNum);
        if(previousColor != null) previousColor.free();
        currentColor.block();
    
    }

}
