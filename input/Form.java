package input;

import base.Action;
import base.MyWorld;
import base.NButton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A Form is a Group of actors
 */
public class Form {
    private static Form activeForm;
    
    private Map<String,Input> inputs = new HashMap<>();
    private List<FormAction> actions = new ArrayList();
    private List<FormCancelAction> cancelActions = new ArrayList();
    private MyWorld world;
    
    private Action submit;
    private NButton submitButton = new NButton(submit, "OK");
    
    public Form() {
        submit = ()->{
            Map<String,String> values = getValues();
            actions.forEach((formAction)-> {
                formAction.useInformations(values);
            });
        };
        
    }
            
    /**
     * Adds an input to this Form. Doesn't work if the the Form is already Displayed.
     * It is also limited to 5 inputs for display space reasons.
     * @param inputID what allows to identify which Input each value is from in the
     *      FormAction
     * @param input
     */
    public void addInput(String inputID, Input input) {
        if(world == null && inputs.size() < 5) inputs.put(inputID, input);
    }
    
    /**
     * Adds this Form's Actors to MyWorld.theWorld.
     */
    public void addToWorld() {
        world = MyWorld.theWorld;
        world.lockAllButtons();
        throw new UnsupportedOperationException("not supported yet");
    }
    
    /** 
     * Removes this Form's Actors from the World, and performs cancel actions.
     * @param cause the cause of the cancelation
     */
    public void cancel(String cause) {
        removeFromWorld();
        cancelActions.forEach((formCancelAction) -> {
            formCancelAction.cancel(cause);
        });
        throw new UnsupportedOperationException("not supported yet");
    
    }
    
    /**
     * Removes this Form's Actor from the World.
     */
    public void removeFromWorld() {
        world.unlockAllButtons();
        world = null;
        throw new UnsupportedOperationException("not supported yet");

    }
    
    private Map<String,String> getValues() {
        Map<String,String> values = new HashMap<>();
        Set<Map.Entry<String, Input>> inputSet = inputs.entrySet();
        for(Map.Entry<String, Input> input : inputSet) {
            String ID = input.getKey();
            Input value = input.getValue();
            values.put(ID, value.getValue());
            
        }
        return values;
    }

}
