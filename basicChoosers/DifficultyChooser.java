package basicChoosers;

import appearance.Theme;
import base.GColor;
import greenfoot.GreenfootImage;

public class DifficultyChooser extends BasicChooser {

    public DifficultyChooser() {
        super(new DifficultyChoices());
    }


}

class DifficultyChoices extends ChoiceList{

    @Override
    protected void next() {
        //nothing yet, there is no choice
    }

    @Override
    protected void previous() {
        //nothing yet, there is no choice
    }

    @Override
    protected GreenfootImage choiceImage() {
        return new GreenfootImage("Normal", 20, Theme.used.textColor, new GColor(0,0,0,0));
        
    }

    @Override
    public String choiceValue() {
        return "default"; //there is no Difficulty to choose from yet
    }

}
