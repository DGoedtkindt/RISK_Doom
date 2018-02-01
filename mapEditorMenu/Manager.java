package mapEditorMenu;

import base.Map;
import base.MyWorld;
import base.NButton;
import base.StateManager;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import xmlChoosers.MapChooser;

public class Manager extends StateManager {
    
    private MapChooser mapC = new MapChooser(true);
    private MyWorld world() {return MyWorld.theWorld;}
    private NButton editMapB = new NButton((ActionEvent ae) -> {
            try{
                Map map = mapC.getSelectedMap();
                mapEditor.Manager newManager = new mapEditor.Manager(map);
                world().load(newManager);
                 
            } catch(Exception ex) {
                ex.printStackTrace(System.err);
                
            }
            
            
            
        }, "Edit Map");

    @Override
    public void setupScene() {
        mapC.addToWorld(world().getWidth() / 2, world().getHeight() / 2 );
        world().addObject(editMapB, world().getWidth()/2, 3 * world().getHeight() / 4);
        
    }

    @Override
    public void clearScene() {
        mapC.destroy();
        world().removeObject(editMapB);
    }

    @Override
    public void escape() {
        int choice = JOptionPane.showConfirmDialog(null, "Do you want to return to the main menu?", 
                                                             "Returning to the menu", JOptionPane.YES_NO_CANCEL_OPTION);
            if(choice == JOptionPane.YES_OPTION){
                clearScene();
                world().load(new menu.Manager());

            }
    }


}
