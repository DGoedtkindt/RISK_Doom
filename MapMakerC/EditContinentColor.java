import greenfoot.*;  

public class EditContinentColor extends Button
{
    
    public void clicked(int mode){
        
        if(((MyWorld)(getWorld())).getCurrentMode() == Mode.DEFAULT){
            
            ((MyWorld)(getWorld())).changeMode(Mode.EDIT_CONTINENT_COLOR);
            
        }else{
                
            ((MyWorld)(getWorld())).escape();
            
        }
                               
        Selector.setTheseTransparent(Selector.selectableList);
        
    }
}
