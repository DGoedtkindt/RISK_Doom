import greenfoot.GreenfootImage;
import java.awt.Color;

public class SimpleButton extends Button {
    
    Action actionToTrigger;
    
    public SimpleButton(String text, Action action){
        
        //createImageFromText to make an image
        //set an action
        
        createImageFromText(text);
        actionToTrigger = action;
        
    }
    
    public void clicked() {
        actionToTrigger.trigger();
    }
    
    private void createImageFromText(String textToShow){
        
        GreenfootImage image = new GreenfootImage(textToShow, 25, Color.BLACK, Color.WHITE);
        this.setImage(image);
        
    }
    
    ///Final Actions///////////////////////////////////////
    
    static public final Action CREATE_TERRITORY_BUTTON_ACTION = () -> {
        
        if(MyWorld.theWorld.getCurrentMode() == Mode.DEFAULT){
            
            MyWorld.theWorld.changeMode(Mode.SELECT_HEX);
            Selector.setValidator(Selector.IS_SINGLEHEX);
            
        }else{
            
            MyWorld.theWorld.escape();
            
        }
        
        
    };
    
    static public final Action CREATE_CONTINENT_BUTTON_ACTION = () -> {
        
        if(MyWorld.theWorld.getCurrentMode() == Mode.DEFAULT){
            
            MyWorld.theWorld.changeMode(Mode.SELECT_TERRITORY);
            Selector.setValidator(Selector.IS_TERRITORY_NOT_IN_CONTINENT);
            
        }else{
                
            MyWorld.theWorld.escape();
            
        }
        
        
    };
    
    static public final Action EDIT_CONTINENT_BONUS_BUTTON_ACTION = () -> {
    
        if(MyWorld.theWorld.getCurrentMode() == Mode.DEFAULT){
            
            MyWorld.theWorld.changeMode(Mode.EDIT_CONTINENT_BONUS);
            Selector.setValidator(Selector.IS_CONTINENT);
            
        }else{
                
            MyWorld.theWorld.escape();
            
        }
                             
    
    };
    
    static public final Action EDIT_CONTINENT_COLOR_BUTTON_ACTION = () -> {
    
        if(MyWorld.theWorld.getCurrentMode() == Mode.DEFAULT){
            
            MyWorld.theWorld.changeMode(Mode.EDIT_CONTINENT_COLOR);
            Selector.setValidator(Selector.IS_CONTINENT);
            
        }else{
                
            MyWorld.theWorld.escape();
            
        }
                               
    
    };
    
    static public final Action CHOOSE_CAPITAL_TERRITORY_BUTTON_ACTION = () -> {
    
        if(MyWorld.theWorld.getCurrentMode() == Mode.DEFAULT){
            
            MyWorld.theWorld.changeMode(Mode.CHOOSE_CAPITAL_TERRITORY);
            Selector.setValidator(Selector.IS_TERRITORY);
            
        }else{
                
            MyWorld.theWorld.escape();
            
        }
                               
    
    };
    
    static public final Action CREATE_LINK_BUTTON_ACTION = () -> {
    
        if(MyWorld.theWorld.getCurrentMode() == Mode.DEFAULT){
            
            MyWorld.theWorld.changeMode(Mode.SET_LINKS);
            Selector.setValidator(Selector.IS_TERRITORY);
            
        }else{
                
            MyWorld.theWorld.escape();
            
        }
                               
    
    };
    
    static public final Action DELETE_TERRITORY_BUTTON_ACTION = () -> {
    
        if(MyWorld.theWorld.getCurrentMode() == Mode.DEFAULT){
            
            MyWorld.theWorld.changeMode(Mode.DELETE_TERRITORY);
            Selector.setValidator(Selector.IS_TERRITORY);
            
        }else{
                
            MyWorld.theWorld.escape();
            
        }
                               
    };
    
    static public final Action DELETE_CONTINENT_BUTTON_ACTION = () -> {
    
        if(MyWorld.theWorld.getCurrentMode() == Mode.DEFAULT){
            
            MyWorld.theWorld.changeMode(Mode.DELETE_CONTINENT);
            Selector.setValidator(Selector.IS_CONTINENT);
            
        }else{
                
            MyWorld.theWorld.escape();
            
        }
        
    };
    
    
}
