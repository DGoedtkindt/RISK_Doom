
public class CreateTerritory extends Button
{
    
    @Override
    public void clicked(int mode){
        
        if(((MyWorld)(getWorld())).getCurrentMode() == Mode.DEFAULT){
            
            ((MyWorld)(getWorld())).changeMode(Mode.SELECT_HEX);
            
        }else{
                
            ((MyWorld)(getWorld())).escape();

        }
        
        MyWorld.theWorld.setTerrHexTransparent();
        
    }
    
}
