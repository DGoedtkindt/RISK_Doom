import greenfoot.*;  

public class ChooseCapitalTerritory extends Button
{
    
    public void clicked(int mode){
        
        ((MyWorld)getWorld()).changeMode(Mode.CHOOSE_CAPITAL_TERRITORY);
                               
    }
    
    
    public void act() 
    {
        
    }    
    
    
}
