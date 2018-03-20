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
    
    private Map<String,Input> inputs = new HashMap<>();
    private Map<Input,Boolean> inputsReady = new HashMap<>();
    private FormCancelAction cancelAction = null;
    private MyWorld world;
    
    private Action submit;
    private Action cancel;
    private OKCancelPanel ocPanel;
    
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
        form.addToWorld();
    
    }
    
    public Form() {
        submit = ()->{submit();};
        cancel = ()->{cancel("User pressed the cancel button");};
        
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
        if(world == null && inputs.size() < 5) {
            inputs.put(inputID, input);
            manageOptional(input,optional);
            
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
            addOKCancelPanel();
        
        } else MessageDisplayer.showMessage("Form not added because there is another Form in the World.");
    }
    
    /** 
     * Removes this Form's Actors from the World, and performs cancel actions.
     * @param cause the cause of the cancellation
     */
    public void cancel(String cause) {
        removeFromWorld();
        if(cancelAction != null) cancelAction.cancel(cause);
    
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
            inputsReady.put(input, true);
            //on input sumbit: submit form if it is the only input
            input.onSubmitAction = ()->{if(inputs.size()==1)
                submit();
            };

        }
        //if not optional
        else {
            inputsReady.put(input, false);
            input.onSubmitAction = ()->{
                //on input sumbit: check if value was entered
                //if so set as ready and submit form if it is the only input
                if(!input.value().equals("")) {
                    inputsReady.put(input, true);
                    if(inputs.size()==1) submit();
                }
                //else set as not ready 
                else inputsReady.put(input, false);

            };
        }
    }
    
    /**
     * Returns a map with the ID and the value entered
     */
    private Map<String,String> getValues() {
        Map<String,String> values = new HashMap<>();
        Set<Map.Entry<String, Input>> inputSet = inputs.entrySet();
        for(Map.Entry<String, Input> input : inputSet) {
            String ID = input.getKey();
            Input value = input.getValue();
            values.put(ID, value.value());
            
        }
        return values;
    }

    private void addInputsToWorld() {
        int inputNumber = inputs.size();
        nextInputXPos = world.getWidth()/2;
        nextInputYPos = world.getHeight()/2 - (inputNumber-1)*(Input.HEIGHT/2);
                
        Collection<Input> inputCollection = inputs.values();
        for(Input input : inputCollection) {
            input.addToWorld(nextInputXPos,nextInputYPos);
            nextInputYPos+=Input.HEIGHT;
            
        }
        
    
    }

    private void addOKCancelPanel() {
        if(ocPanel == null) {
            ocPanel = new OKCancelPanel();
            ocPanel.addToWorld();
        }
    }

    private void removeInputsFromWorld() {
        Collection<Input> inputCollection = inputs.values();
        inputCollection.forEach(Input::removeFromWorld);
        
    }

    private boolean allInputsReady() {
        return !(inputsReady.values().contains(false));
        
    }
    
    private class OKCancelPanel {
        private final int HEIGHT = Input.HEIGHT/2;
        private final int WIDTH = Input.WIDTH;
        private NButton submitButton = new NButton(submit, "OK");
        private NButton cancelButton = new NButton(cancel, "Cancel");
        private Background background = new Background(WIDTH,HEIGHT);
        
        void addToWorld() {
            world.addObject(background, 
                    MyWorld.theWorld.getWidth()/2, nextInputYPos);
            world.addObject(submitButton,
                    (int)(1.5*MyWorld.theWorld.getWidth()/4), nextInputYPos);
            world.addObject(cancelButton,
                    (int)(2.5* MyWorld.theWorld.getWidth()/4), nextInputYPos);
            
        }
        
        void removeFromWorld() {
            world.removeObject(submitButton);
            world.removeObject(cancelButton);
            world.removeObject(background);
            
        }
        
        /**
         * just an actor with a certain image that serves as a background.
         */
        private class Background extends Actor {

            private Background(int width, int height) {
                Color backgroundColor = appearance.Theme.used.backgroundColor.brighter();
                this.setImage(new GreenfootImage(width,height));
                this.getImage().setColor(backgroundColor);
                this.getImage().fill();
            }
        }
        
        
    }

}
