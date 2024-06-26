package Handler;

// From Java HTTP Server
import com.sun.net.httpserver.*;

// From Java serialization/deserialization library

// From other Java Library
import java.io.*;
import java.net.*;

// From other packages
import DataAccess.DataAccessException;
import Result.EventResult;
import Service.EventService;

public class EventHandler extends Handler  { // Class Opening
    @Override
    public void handle(HttpExchange exchange) throws IOException { // // HTTP Method: GET

        System.out.println("In All Event Handler");

        try { // Beginning of try

            // Determine the HTTP request type (POST).
            if (exchange.getRequestMethod().toLowerCase().equals("get")) { // expected a get request

                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();

                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {

                    // Extract the auth token from the "Authorization" header
                    String authToken = reqHeaders.getFirst("Authorization"); // authorization header contains the authtoken

                    // Do the Service (no need to extract eventID)
                    EventResult result = EventService.getAllEvent(authToken);

                    // Send response back (including the code)
                    if(!result.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }


                    // Get response body output stream
                    OutputStream resBody = exchange.getResponseBody();
                    String resData = gson.toJson(result);
                    writeString(resData, resBody);
                    resBody.close();
                    success = true;
                }
            }

            // request method not get
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0); // 400 status code
                exchange.getResponseBody().close(); //not gonna send back any data

            }

            if (!success) { // check for failure
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close(); // don't send response
            }

        } // End of try
        catch (IOException | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }

} // Class Closing
