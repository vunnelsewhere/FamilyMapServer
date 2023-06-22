package Handler;

// From Java HTTP Server
import com.sun.net.httpserver.*;

// From Java serialization/deserialization library
import com.google.gson.*;

// From other Java Library
import java.io.*;
import java.net.*;

// From other packages
import DataAccess.DataAccessException;
import Result.ClearResult;
import Service.ClearService;


public class ClearHandler extends Handler { // Class Opening

    @Override
    public void handle(HttpExchange exchange) throws IOException { // HTTP Method: POST

        System.out.println("In clear Handler");
        try { // Beginning of try
            // Request method matches with 'post'
            if(exchange.getRequestMethod().toLowerCase().equals("post")) {

                // Do the Service
                ClearService service = new ClearService();
                ClearResult result = service.clear();



                if(!result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }

                // Get response body output stream
                OutputStream respBody = exchange.getResponseBody();
                String resData = gson.toJson(result);
                // Write the JSON string to the output stream
                writeString(resData,respBody);
                // Close the output stream. THis is how Java knows we are done
                respBody.close(); // tell Java that the response is finished
                success = true;

            }
            // request method not post
            else {
               exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0); // 400 status code
                exchange.getResponseBody().close(); //not gonna send back any data
            }

            if(!success) { // check for failure
                // The HTTP request was invalid somehow, so we return a "bad request"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();

            }


        } // End of try
        catch (IOException  | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR,0);
            exchange.getResponseBody().close();
            e.printStackTrace();

        }

    }

} // Class Closing

