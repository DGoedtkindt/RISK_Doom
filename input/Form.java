package input;

import appearance.MessageDisplayer;
import base.Action;
import base.MyWorld;
import base.NButton;
import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A Form is a Group of actors that allows to use (multiple) Input(s).
 * to use one follow these steps:
 *  1) Create a new Form.
 *  2) Create new Inputs according to the type of data you want to get.
 *  3) Add the inputs to the Form
 *  4) Add an action to tell the Form what to do once the data is entered.
 *  5) Add the Form to the world so that the user can use it.
 * 
 *  N.B. There can be only one Form on the world. 
 */
public class Form {
    private static Form activeForm;
    public static Form activeForm() {return activeForm;}
    
    public FormAction submitAction = null;
    public FormCancelAction cancelAction;
    
    private final Map<String,Input> INPUTS = new HashMap<>();
    private final Map<Input,Boolean> INPUTS_READY = new HashMap<>();
    private MyWorld world;
    
    private final Action SUBMIT;
    private OKCancelPanel ocPanel;
    
    public boolean hasSubmitButton = true;
    
    //for input placement
    int nextInputXPos;
    int nextInputYPos;
    
    /**
     * Shows a Form with one TextInput.
     * It will pass the input to the action with the ID "inputedText" 
     * N.B. this won't work if a Form is already on the world
     * 
     * @param title the title that will be displayed.
     * @param action the action the Form will perform
     */
    public static void inputText(String title, FormAction action) {
        Form form = new Form();
        Input input = new TextInput(title);
        form.addInput("inputedText", input, false);
        form.submitAction = action;
        form.addToWorld();

    }
    
    /**
     * Shows a Form with one ColorInput.
     * It will pass the input to the action with this ID: "inputedColor" 
     * N.B. This won't work if a Form is already on the world
     * 
     * @param title the title that will be displayed.
     * @param action the action the Form will perform
     */
    public static void inputColor(String title, FormAction action) {
        Form form = new Form();
        Input input = new ColorInput(title);
        form.addInput("inputedColor", input, false);
        form.submitAction = action;
        form.addToWorld();
    
    }
    
    /**
     * Shows a Form with a Yes/No question.
     * It will pass the input to the action with this ID: "confirmation".
     * values are either "Yes" or "No".
     * N.B. This won't work if a Form is already on the world
     * 
     * @param title the title that will be displayed.
     * @param action the action the Form will perform
     */
    public static void confirmInput(String title, FormAction action) {
        Form form = new Form();
        Input input = new ChoiceInput(title, "Yes", "No");
        form.addInput("confirmation", input, false);
        form.submitAction = action;
        form.hasSubmitButton = false;
        form.addToWorld();
    
    }
    
    public Form() {
        SUBMIT = ()->{submit();};
        cancelAction = (String s)->{};
        
    }
    
    /**
     * Adds an input to this Form. Doesn't work if the the Form is already Displayed.
     * It is also limited to 5 inputs for display space reasons.
     * @param inputID what allows to identify which Input each value is from in the
     *      FormAction
     * @param input
     * @param optional Define whether it is optional or not to complete the input
     */
    public void addInput(String inputID, Input input, boolean optional) {
        if(world == null && INPUTS.size() < 5) {
            INPUTS.put(inputID, input);
            manageOptional(input,optional);
            
        }if(input instanceof TextInput){
            Input.activeInput = input;
        }
        
    }
    
    /**
     * Adds this Form's Actors to MyWorld.theWorld.
     * Can't add to world if another Form is already added.
     */
    public void addToWorld() {
        if(activeForm == null) {
            activeForm = this;
            world = MyWorld.theWorld;
            world.lockAllButtons();
            addInputsToWorld();
            addOKCancelPanel(hasSubmitButton);
        
        } else MessageDisplayer.showMessage("Form not added because there is another Form in the World.");
    }
    
    /** 
     * Removes this Form's Actors from the World, and performs cancel actions.
     * @param cause the cause of the cancellation
     */
    public void cancel(String cause) {
        if(cancelAction != null && world != null) cancelAction.cancel(cause);
        removeFromWorld();
        
    }
    
    /**
     * If all inputs are ready: 
     * Gets all the inputs and performs all actions
     */
    public void submit() {
        //if there is an activeInput, submit the input
        //so that when OK is clicked it does not say that a field still needs to be 
        //entered because the user forgot to press 'enter'
        if(Input.activeInput != null) {
            Input activeInput = Input.activeInput;
            Input.activeInput = null;
            activeInput.submit();
        } else{
        
            if(allInputsReady()) {
                removeFromWorld();
                Map<String, String> values = getValues();
                submitAction.useInformations(values);
            } else 
                MessageDisplayer.showMessage("One or more field still need to be completed.");
        }
    }
    
    /**
     * Removes this Form's Actor from the World.
     */
    public void removeFromWorld() {
        world.unlockAllButtons();
        ocPanel.removeFromWorld();
        removeInputsFromWorld();
        world = null;
        activeForm = null;

    }
    
    private void manageOptional(Input input, boolean optional) {
        //if optional
        if(optional) {
            //set as ready in inputsReady
            INPUTS_READY.put(input, true);
            //on input sumbit: submit form if it is the only input
            input.onSubmitAction = ()->{if(INPUTS.size()==1)
                submit();
            };

        }
        //if not optional
        else {
            INPUTS_READY.put(input, false);
            input.onSubmitAction = ()->{
                //on input sumbit: check if value was entered
                //if so set as ready and submit form if it is the only input
                if(!input.value().equals("")) {
                    INPUTS_READY.put(input, true);
                    if(INPUTS.size()==1) submit();
                }
                //else set as not ready 
                else INPUTS_READY.put(input, false);

            };
        }
    }
    
    /**
     * Returns a map with the ID and the value entered
     */
    private Map<String,String> getValues() {
        Map<String,String> values = new HashMap<>();
        Set<Map.Entry<String, Input>> inputSet = INPUTS.entrySet();
        for(Map.Entry<String, Input> input : inputSet) {
            String ID = input.getKey();
            Input value = input.getValue();
            values.put(ID, value.value());
            
        }
        return values;
    }

    private void addInputsToWorld() {
        int inputNumber = INPUTS.size();
        nextInputXPos = world.getWidth()/2;
        nextInputYPos = world.getHeight()/2 - (inputNumber-1)*(Input.HEIGHT/2);
                
        Collection<Input> inputCollection = INPUTS.values();
        for(Input input : inputCollection) {
            input.addToWorld(nextInputXPos,nextInputYPos);
            nextInputYPos+=Input.HEIGHT;
            
        }
        
    
    }

    private void addOKCancelPanel(boolean hasSubmit) {
        if(ocPanel == null) {
            ocPanel = new OKCancelPanel();
            ocPanel.addToWorld(hasSubmit);
        }
    }

    private void removeInputsFromWorld() {
        Collection<Input> inputCollection = INPUTS.values();
        inputCollection.forEach(Input::removeFromWorld);
        
    }

    private boolean allInputsReady() {
        return !(INPUTS_READY.values().contains(false));
        
    }
    
    private class OKCancelPanel {
        private final int HEIGHT = Input.HEIGHT/2;
        private final int WIDTH = Input.WIDTH;
        private final NButton SUBMIT_BUTTON = new NButton(SUBMIT, "OK");
        private final NButton CANCEL_BUTTON = new NButton(() -> {activeForm.cancel("Cancelled by User.");}, "Cancel");
        private final Background BACKGROUND = new Background(WIDTH,HEIGHT);
        
        void addToWorld(boolean hasSubmit) {
            world.addObject(BACKGROUND, MyWorld.theWorld.getWidth()/2, nextInputYPos);
            
            if(hasSubmit){
                world.addObject(SUBMIT_BUTTON, (int)(3 * MyWorld.theWorld.getWidth() / 8), nextInputYPos);
                world.addObject(CANCEL_BUTTON, (int)(5 * MyWorld.theWorld.getWidth() / 8), nextInputYPos);
            }else{
                world.addObject(CANCEL_BUTTON, MyWorld.theWorld.getWidth() / 2, nextInputYPos);
            }
            
        }
        
        void removeFromWorld() {
            world.removeObject(SUBMIT_BUTTON);
            world.removeObject(CANCEL_BUTTON);
            world.removeObject(BACKGROUND);
            
        }
        
        /**
         * just an actor with a certain image that serves as a background.
         */
        private class Background extends Actor {

            private Background(int width, int height) {
                Color backgroundColor = appearance.Theme.used.backgroundColor.brighter();
                setImage(new GreenfootImage(width,height));
                getImage().setColor(backgroundColor);
                getImage().fill();
            }
        }
        
        
    }

}
