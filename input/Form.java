package input;

import appearance.MessageDisplayer;
import base.Action;
import base.MyWorld;
import base.NButton;
import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A Form is a Group of actors
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
        if(activeForm != null) {
            world = MyWorld.theWorld;
            world.lockAllButtons();
            Collection<Input> inputCollection = inputs.values();
            inputCollection.forEach(addInputToWorld);
            addOKCancelPanel();
            
            
        }
    }
    
    /** 
     * Removes this Form's Actors from the World, and performs cancel actions.
     * @param cause the cause of the cancelation
     */
    public void cancel(String cause) {
        removeFromWorld();
        cancelAction.cancel(cause);
    
    }
    
    /**
     * If all inputs are ready: 
     * Gets all the inputs and performs all actions
     */
    public void submit() {
        if(allInputsReady()) {
            removeFromWorld();
            Map<String, String> values = getValues();
            submitAction.useInformations(values);
            MessageDisplayer.showMessage("Form submited");
        } else 
            MessageDisplayer.showMessage("One or more field still need to be entered");
    
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
            inputsReady.put(input, optional);
            //on input sumbit: submit form if it is the only input
            input.onSubmitAction = ()->{if(inputs.size()==1) submit();};

        }
        //if not optional
        //on input sumbit: check if value was entered
        else input.onSubmitAction = ()->{
            //if so set as ready and submit form if it is the only input
            if(input.value()!="") {
                inputsReady.put(input, true);
                if(inputs.size()==1) submit();
            }
            //else set as not ready 
            else inputsReady.put(input, false);

        };
    
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
    
    private Consumer<Input> addInputToWorld = new Consumer<Input>() {
        int inputNumber = inputs.size();
        int xPos = world.getWidth()/2;
        int yPos = (inputNumber-1)*(Input.HEIGHT/2);
        
        @Override
        public void accept(Input input) {
            input.addToWorld(xPos,yPos);
            yPos+=Input.HEIGHT;
        }
    };

    private void addOKCancelPanel() {
        if(ocPanel != null) {
            ocPanel = new OKCancelPanel();
            ocPanel.addToWorld();
        }
    }

    private void removeInputsFromWorld() {
        Collection<Input> inputCollection = inputs.values();
        inputCollection.forEach(Input::removeFromWorld);
        
    }

    private boolean allInputsReady() {
        return !inputsReady.values().contains(false);
        
    }
    
    private class OKCancelPanel {
        private final int HEIGHT = 100;
        private final int WIDTH = appearance.Appearance.WORLD_WIDTH;
        private NButton submitButton = new NButton(submit, "OK");
        private NButton cancelButton = new NButton(cancel, "Cancel");
        private Background background = new Background(WIDTH,HEIGHT);
        
        void addToWorld() {
            world.addObject(background, 
                    MyWorld.theWorld.getWidth()/2, OKCancelPanelYPos());
            world.addObject(submitButton,
                    MyWorld.theWorld.getWidth()/3, OKCancelPanelYPos());
            world.addObject(cancelButton,
                    2* MyWorld.theWorld.getWidth()/3, OKCancelPanelYPos());
            
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
                Color backgroundColor = appearance.Theme.used.backgroundColor;
                this.setImage(new GreenfootImage(width,height));
                this.getImage().setColor(backgroundColor);
                this.getImage().fill();
            }
            
            @Override
            public void act() {
                if(Greenfoot.isKeyDown("Enter")) submit();
            
            }


        }
        
        
    }
    
    private int OKCancelPanelYPos() {
        int inputNumber = inputs.size();
        int basePos = (inputNumber-1)*(Input.HEIGHT/2);
        return basePos + (inputNumber - 1)*Input.HEIGHT+50;
        
    }

}
