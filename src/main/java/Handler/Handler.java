package Handler;


// From Java HTTP Server
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

// From io class
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/*
 * Base class of all Handlers
 */

public class Handler implements HttpHandler { // Class Opening

    // Variable Declarations
    boolean success = false;

    // Each handler will implement this method differently
    @Override
    public void handle(HttpExchange exchange) throws IOException {

    }

   /*
    * The readString method shows how to read a String from an InputStream.
    */
    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
     * The writeString method shows how to write a String to an OutputStream.
     */
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


} // Class Closing

/*
 * Implements vs extends
 * Difference: implements means you are using the elements of a Java Interface in your class
 * extends means that you are creating a subclass of the base class you are extending
 */

/*
 * IOException is the base class for exceptions throw while accessing information using streams, files, and directories
 * they occur whenever an input or output operation is failed or interpreted
 */
