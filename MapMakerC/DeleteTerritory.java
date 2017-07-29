import greenfoot.*;  

public class DeleteTerritory extends Button
{
    
    public void clicked(int mode){

        if(((MyWorld)(getWorld())).getCurrentMode() == Mode.DEFAULT){
            
            ((MyWorld)(getWorld())).changeMode(Mode.DELETE_TERRITORY);
            
        }else{
                
            ((MyWorld)(getWorld())).escape();
            
        }
                               
        Selector.setAllTransparent();
        
    }
}
