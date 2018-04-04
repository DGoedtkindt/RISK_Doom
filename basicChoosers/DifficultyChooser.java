package basicChoosers;

import appearance.Theme;
import base.GColor;
import game.Difficulty;
import greenfoot.GreenfootImage;

/**
 * The object used to select the Difficulty.
 * 
 */
public class DifficultyChooser extends BasicChooser {
    
    private int currentDifficulty = 0;

    @Override
    protected void updateImage() {
        GreenfootImage newImage = new GreenfootImage(selectedDifficulty().NAME,
                20, Theme.used.textColor, new GColor(0,0,0,0));
        setImage(newImage);
    }

    @Override
    public String choiceValue() {
        return selectedDifficulty().NAME;  
        
    }
    
     /**
     * Returns the chosen Difficulty.
     * @return The chosen Difficulty.
     */
    public Difficulty selectedDifficulty(){
        return Difficulty.values()[currentDifficulty];
    }

    @Override
    public void next() {
        if(currentDifficulty == Difficulty.values().length - 1){currentDifficulty = 0;}
        else{currentDifficulty ++;}
        updateImage();
    }

    @Override
    public void previous() {
        if(currentDifficulty == 0){currentDifficulty = Difficulty.values().length - 1;}
        else{currentDifficulty --;}
        updateImage();
    }
    
}
