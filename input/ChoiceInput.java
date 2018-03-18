package input;

import java.util.HashMap;
import java.util.Map;


public class ChoiceInput extends Input {
    
    private final String TITLE;
    private Map<String,String> choices = new HashMap<>();
    
    /**
     * Creates a new ChoiceInput without options
     * 
     * @param title this is the title that will be displayed
     */
    public ChoiceInput(String title) {
        TITLE = title;
        
    }
    
    /**
     * Creates a new ChoiceInput with 2 option already added. The ID and the 
     * text displayed on the option buttons are the same.
     * 
     * @param title this is the title that will be displayed
     * @param firstOption   optionID and optionText of the first option
     * @param secondOption  optionID and optionText of the second option
     */
    public ChoiceInput(String title, String firstOption, String secondOption) {
        this(title);
        addOption(firstOption,firstOption);
        addOption(secondOption,secondOption);
    
    }

    @Override
    void addToWorld(int xPos, int yPos) {
        System.out.println("Method addToWorld() in class ChoiceInput is not supported yet");
    }

    @Override
    void removeFromWorld() {
        System.out.println("Method removeFromWorld() in class ChoiceInput is not supported yet");
    }

    @Override
    public String value() {
        System.out.println("Method value() in class ChoiceInput is not supported yet");
        return "";
    }
    
    /**Add an option to this ChoiceInput
     *
     * @param optionID  is the value returned if the added option is selected 
     * @param optionText is the text displayed on the option button
     */
    public void addOption(String optionID, String optionText) {
        choices.put(optionID, optionText);
        
    }

}
