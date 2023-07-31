package Result;

/**
 * This class is the response object returned from Load API endpoint call
 */
public class LoadResult {

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
    public LoadResult() {
    }



    /**
     * Error Response Body
     * @param message
     * @param success
     */
    public LoadResult(String message, Boolean success) {
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

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
