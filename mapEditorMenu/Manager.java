package mapEditorMenu;

import appearance.MessageDisplayer;
import base.Action;
import base.Map;
import base.NButton;
import base.StateManager;
import javax.swing.JOptionPane;
import xmlChoosers.MapChooser;

public class Manager extends StateManager {
    
    private MapChooser mapC;
    private NButton editMapB;

    public Manager() {
        this.editMapB = new NButton(editSelectedMap, "Edit Map");
        this.mapC = new MapChooser(true);
    }

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

    private Action editSelectedMap = () -> {
        try{
            Map map = mapC.getSelectedMap();
            mapEditor.Manager newManager = new mapEditor.Manager(map);
            world().load(newManager);

        } catch(Exception ex) {
            MessageDisplayer.showMessage(ex.getMessage());

        }

    };

}
