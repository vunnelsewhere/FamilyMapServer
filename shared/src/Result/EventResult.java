package Result;

import Model.Event;

import java.util.ArrayList;

/**
 * This class is the response object returned from Event API endpoint call
 */
public class EventResult {

    // Variable Declaration
    /**
     * Array of Event objects from current user
     */
    private ArrayList<Event> data;

    /**
     * Result if the call was successful or not
     */
    private Boolean success;

    /**
     * Message if the clear succeeded or not
     */
    private String message;

    /**
     * A constructor - success
     * @param data - Array of Event objects from current user
     */
    public EventResult(ArrayList<Event> data, Boolean success) {
        this.data = data;
        this.success = success;
    }

    /**
     * A constructor - error
     * @param message - Message if the clear succeeded or not
     */
    public EventResult(String message,Boolean success) {
        this.message = message;
        this.success = success;
    }

    // Getter and Setter
    public ArrayList<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
