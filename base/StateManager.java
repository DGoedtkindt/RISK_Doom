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
    public Map map() {throw new IllegalStateException("There is no Map in this state of the program");}
    public Game game() {throw new IllegalStateException("There is no Game in this state of the program");}
    public abstract void escape();
    protected MyWorld world() {return MyWorld.theWorld;}
    
    /**
     * This Action asks for confirmation to go back to menu.Manager.
     * @param message The message that will appear on the confirmation dialog.
     */
    protected void standardBackToMenu(String message) {
            Form.confirmInput(message,(input)->{
                if(input.get("confirmation").equals("Yes")) {
                    clearScene();
                    world().load(new menu.Manager());
                } 
            });
    
    };
    
}
