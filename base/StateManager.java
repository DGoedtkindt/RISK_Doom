package base;

import input.Form;

/** 
 * A StateManager manages the setup and proper destruction of a scene in a 
 * certain program state (menu, game, options,...). It also manages the 
 * Map/Game objects, if its program state requires it.
 */
public abstract class StateManager{
    
    public abstract void setupScene();
    public abstract void clearScene();
    public Map map() {return null;}
    public Game game() {return null;}
    public abstract void escape();
    protected MyWorld world() {return MyWorld.theWorld;}
    
    //codes that comes back in nearly every stateManager
    /**
     * This action asks for confirmation to go back to menu.Manager
     * @param message   the message that will appear on the confirmation dialog
     */
    protected void standardBackToMenu(String message) {
            Form.confirmInput(message,(input)->{
                if(input.get("confirmation") == "Yes") {
                    this.clearScene();
                    world().load(new menu.Manager());
                } 
            });
    
    };
    
}
