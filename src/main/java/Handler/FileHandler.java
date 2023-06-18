package Handler;

// From Java HTTP Server
import com.sun.net.httpserver.HttpExchange; // Class HttpExchange
import java.net.HttpURLConnection; // Class HttpURLConnection


// From io class
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

// From nio class
import java.nio.file.Files;


/*
 * Handles all default requests
 * e.g., access the index.html file
 * Ignore everything but GET requests, Get the request URL from the exchange
 */
public class FileHandler extends Handler { // Class Opening
    @Override
    public void handle(HttpExchange exchange) throws IOException {


        try { // Beginning of try
            // Ignore everything but GET requests
            if(exchange.getRequestMethod().toLowerCase().equals("get")) {
                // Get the request URL from the exchange
                String urlPath = exchange.getRequestURI().toString();
                // If the urlPath is null or "/", set urlPath to "/index.html"
                if(urlPath == null || urlPath.equals("/") || urlPath.equals("/index.html")) {
                    urlPath = "/index.html";
                }
                // Append urlPath to a relative path (no leading slash) to the directory containing the files
                // Assumes there is a directory named "web" in the root of the project containing your server and the files are in the "web" directory
                String filePath = "web" + urlPath;
                // Create a file object and check if the file exists
                File file = new File(filePath);
                // If the file exists, read the file and write it to the HttpExchange's output stream to make the file physically present on the server
                if(file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK,0); // HTTP Status-Code 200: OK
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(file.toPath(),respBody); // copy file to an output stream
                    success = true; // filePath exists + successfully sent as response to HttpExchange
                    respBody.close();
                }

                // If the file does not exist, return a 404 (not found error)
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND,0);
                    urlPath = "/HTML/404.html";
                    filePath = "web" + urlPath;
                    file = new File(filePath);
                    OutputStream respBody = exchange.getResponseBody();
                    Files.copy(file.toPath(),respBody);
                    respBody.close();
                }
            }

            // not GET request
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_METHOD,0); // HTTP Status-Code 405: Method Not Allowed
                exchange.getResponseBody().close();
            }

            /*
             * Success remains false when
             * 1. HTTP method is not GET
             * 2. requested file does not exist
             * 3. if an exception occurs during the execution of the code e.g., IOException + the code enters catch block immediately
             */

            // Invalid Request
            if(!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0);
                exchange.getResponseBody().close();
            }


        } // End of try
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR,0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

} // Class Closing

/*
 * Class HttpURLConnection
 * List of HTTP Status-Code
 *
 */