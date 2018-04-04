package input;

import java.util.Map;

/**
 * Actions that uses the information from an Form
 * 
 */
public interface FormAction {
    
    /**
     * This is the action performed once the Form Closes
     * @param formInputs All the inputs of the form in a Map<String,String>.
     *      the key is the ID of the form input, the value is... the value
     */
    public void useInformations(Map<String,String> formInputs);
    
}
