package Handler;

// From Java HTTP Server
import com.sun.net.httpserver.*;

// From Java serialization/deserialization library

// From other Java Library
import java.io.*;
import java.net.*;

// From other packages
import DataAccess.DataAccessException;
import Result.FillResult;
import Service.FillService;


public class FillHandler extends Handler { // Class Opening

    @Override
    public void handle(HttpExchange exchange) throws IOException { // HTTP Method: POST

        System.out.println("In fill Handler");

        try { // Beginning of try
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                // get URl
                String[] URL = exchange.getRequestURI().toString().split("/");
                String username = URL[2]; // index 2, 3rd element

                // generation: default 4
                int numGeneration = 4;

                // host/fill/susan/3
                if (URL.length == 4) {
                    numGeneration = Integer.parseInt(URL[3]); // index 3, 4th element
                }

                // Do the Service
                FillService service = new FillService();
                FillResult result = service.fill(username, numGeneration);


                // Send response back (including the code)
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);


                // Get response body output stream
                OutputStream resBody = exchange.getResponseBody();
                String resData = gson.toJson(result);
                writeString(resData, resBody);
                resBody.close();
                success = true;

            }
            // request method not post
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

