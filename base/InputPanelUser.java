package base;

/**
 * This Interface is implemented by the classes that use the InputPanel.
 * 
 */
public interface InputPanelUser {
    
    /**
     * This method obtains the informations of an InputPanel and processes them.
     * @param information The information returned by the InuptPanel.
     * @param type The type of this information.
     * @throes Exception If an Exception occurs.
     */
    public void useInformations(String information, String type) throws Exception;
    
}
