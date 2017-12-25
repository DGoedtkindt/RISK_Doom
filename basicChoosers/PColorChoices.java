package basicChoosers;

import greenfoot.GreenfootImage;


public class PColorChoices extends ChoiceList{

    @Override
    protected void next() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void previous() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected GreenfootImage choiceImage() {
        //just for testing
        return new GreenfootImage(10,10);
    }

    @Override
    public String choiceValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void liberateChoice() {
       //still needs to do something
    }

}
