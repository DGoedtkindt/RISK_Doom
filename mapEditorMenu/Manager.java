package mapEditorMenu;

import appearance.Appearance;
import appearance.MessageDisplayer;
import base.Action;
import base.Map;
import base.MyWorld;
import base.NButton;
import base.StateManager;
import input.InputPanel;
import xmlChoosers.MapChooser;

/**
 * This type of Manager lets the User choose a Map and start the Map Editor.
 * 
 */
public class Manager extends StateManager {
    
    private MapChooser mapC;
    private NButton editMapB;

    /**
     * Creates a Manager.
     */
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
        
        if(InputPanel.usedPanel == null){
            InputPanel.showConfirmPanel("Do you want to return to the main Menu?", 100, "escape", this, Appearance.WORLD_WIDTH / 2, Appearance.WORLD_HEIGHT / 2);
        }else{
            InputPanel.usedPanel.destroy();
        }
        
    }

    private Action editSelectedMap = () -> {
        try{
            Map map = mapC.getSelectedMap();
            mapEditor.Manager newManager = new mapEditor.Manager(map);
            world().load(newManager);

        } catch(Exception ex) {
            String message = "Cannot edit selected Map";
            MessageDisplayer.showException(new Exception(message, ex));

        }

    };

    @Override
    public void useInformations(String information, String type) throws Exception {
        
        if(type.equals("escape")){
            
            if(information.equals(InputPanel.YES_OPTION)){
                MyWorld.theWorld.load(new menu.Manager());

            }
            
        }
        
    }

}
