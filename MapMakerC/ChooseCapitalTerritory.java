import greenfoot.*;  

public class ChooseCapitalTerritory extends Button
{
    
    public void clicked(int mode){
        
        if(((MyWorld)(getWorld())).getCurrentMode() == Mode.DEFAULT){
            
            ((MyWorld)getWorld()).changeMode(Mode.CHOOSE_CAPITAL_TERRITORY);
            
        }else{
                
            ((MyWorld)(getWorld())).escape();
            
        }
                               
        Selector.setAllTransparent();
        
    }
}
