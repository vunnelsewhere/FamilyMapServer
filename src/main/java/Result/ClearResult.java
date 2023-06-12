package Result;

/**
 * This class is the response object returned from Clear API endpoint call
 */
public class ClearResult {

    // Variable Declaration

    /**
     * Message if the clear succeeded or not
     */
    private String message;

    /**
     * Result if the call was successful or not
     */
    private boolean success;


    /**
     * A constructor
     * @param message - Message if the clear succeeded or not
     * @param success - Result if the call was successful or not
     */
    public ClearResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // Getter and Setter

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
