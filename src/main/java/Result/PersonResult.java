package Result;

import java.util.ArrayList;
import Model.Person;

/**
 * This class is the response object returned from Person API endpoint call
 */
public class PersonResult {


    /**
     * Array of Person objects from current user
     */
    private ArrayList<Person> data;

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
     * @param data - Array of Person objects from current user
     */
    public PersonResult(ArrayList<Person> data, Boolean success) {
        this.data = data;
        this.success = success;
    }

    /**
     * A constructor - error
     * @param message - Message if the clear succeeded or not
     */
    public PersonResult(String message,Boolean success) {
        this.message = message;
        this.success = success;
    }



    // Getter and Setter

    public ArrayList<Person> getData() {
        return data;
    }

    public void setData(ArrayList<Person> data) {
        this.data = data;
    }

    public boolean isSuccess() {
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
