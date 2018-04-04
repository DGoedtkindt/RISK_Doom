package mapEditorMenu;

import appearance.MessageDisplayer;
import base.Action;
import base.Map;
import base.NButton;
import base.StateManager;
import xmlChoosers.MapChooser;

/**
 * This type of Manager lets the User choose a Map and start the Map Editor.
 * 
 */
public class Manager extends StateManager {
    
    private MapChooser mapChooser;
    private final NButton EDIT_MAP_BUTTON;

    /**
     * Creates a Manager.
     */
    public Manager() {
        EDIT_MAP_BUTTON = new NButton(EDIT_SELECTED_MAP, "Edit Map");
        mapChooser = new MapChooser(true);
    }

    @Override
    public void setupScene() {
        mapChooser.addToWorld(world().getWidth() / 2, world().getHeight() / 2 );
        world().addObject(EDIT_MAP_BUTTON, world().getWidth()/2, 3 * world().getHeight() / 4);
        
    }

    @Override
    public void clearScene() {
        mapChooser.destroy();
        world().removeObject(EDIT_MAP_BUTTON);
    }

    @Override
    public void escape() {
       standardBackToMenu("Do you want to return to the main menu?");
    }

    private final Action EDIT_SELECTED_MAP = () -> {
        try{
            Map map = mapChooser.getSelectedMap();
            mapEditor.Manager newManager = new mapEditor.Manager(map);
            world().load(newManager);

        } catch(Exception ex) {
            String message = "Cannot edit selected Map";
            MessageDisplayer.showException(new Exception(message, ex));

        }

    };

}
