package input;

/**
 * Objects implementing this determine what a Form should do on cancel 
 */
public interface FormCancelAction {
    public void cancel(String cause);
    
}
