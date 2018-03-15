package input;

/**
 * Objects implementing this determine what a Form should do on cancel 
 */
public interface FormCancelAction {
    /**
     * An action a Form will perform in case of cancellation
     * @param cause a brief description of the cause of the cancellation
     */
    public void cancel(String cause);
    
}
