package Result;

/**
 * This class is the response object returned from Clear API endpoint call
 */
public class ClearResult {



    // Variable Declarations
    private String message;
    private boolean success;



    // Constructor
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
