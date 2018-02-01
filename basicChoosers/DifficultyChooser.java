package basicChoosers;

import appearance.Theme;
import base.GColor;
import game.Difficulty;
import greenfoot.GreenfootImage;

public class DifficultyChooser extends BasicChooser {
    
    
    public DifficultyChooser() {
        super(new DifficultyChoices());
    }
    
    public Difficulty selectedDifficulty() {
        return ((DifficultyChoices)choices).selectedDifficulty();
    }
    
}

class DifficultyChoices extends ChoiceList{
    
    private int currentDifficulty = 0;
    
    @Override
    protected void next() {
        if(currentDifficulty == Difficulty.values().length - 1){currentDifficulty = 0;}
        else{currentDifficulty ++;}
    }

    @Override
    protected void previous() {
        if(currentDifficulty == 0){currentDifficulty = Difficulty.values().length - 1;}
        else{currentDifficulty --;}
    }

    @Override
    protected GreenfootImage choiceImage() {
        return new GreenfootImage(selectedDifficulty().NAME, 20, Theme.used.textColor, new GColor(0,0,0,0));
        
    }

    @Override
    protected String choiceValue() {
        return selectedDifficulty().NAME; 
    }

    protected Difficulty selectedDifficulty(){
        return Difficulty.values()[currentDifficulty];
    }
    
}
