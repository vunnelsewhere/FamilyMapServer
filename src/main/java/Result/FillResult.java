package Result;

/**
 * This class is the response object returned from Fill API endpoint call
 */
public class FillResult {

    // Variable Declaration

    /**
     * Message if the clear succeeded or not
     */
    private String message;

    /**
     * Result if the call was successful or not
     */
    private Boolean success;

    /**
     * A default constructor
     */
    public FillResult() {
    }

    public FillResult(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    // Getters and Setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
