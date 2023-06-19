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
import Result.LoadResult;
import Service.LoadService;
import Request.LoadRequest;

public class LoadHandler extends Handler { // Class Opening

    @Override
    public void handle(HttpExchange exchange) throws IOException { // HTTP Method: POST

        System.out.println("In load Handler");
        try { // Beginning of try
            // Request method matches with 'post'
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {


                // Get data from request body
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);

                // Do the Service
                LoadRequest request = (LoadRequest)gson.fromJson(reqData, LoadRequest.class);
                LoadService service = new LoadService();
                LoadResult result = service.load(request);


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
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0); // 400 status code
                exchange.getResponseBody().close(); //not gonna send back any data

            }
            if (!success) { // check for failure
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close(); // don't send response
            }

        } // End of try
         catch (IOException  | DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }

    }


} // Class Closing
