package Handler;

// From Java HTTP Server
import com.sun.net.httpserver.*;

// From Java serialization/deserialization library

// From other Java Library
import java.io.*;
import java.net.*;

// From other packages
import DataAccess.DataAccessException;
import Request.RegisterRequest;
import Result.RegisterResult;
import Service.RegisterService;


public class RegisterHandler extends Handler { // Class Opening

    @Override
    public void handle(HttpExchange exchange) throws IOException { // HTTP Method: POST

        System.out.println("In Register Handler");
        try { // Beginning of try
            // Request method matches with 'post'
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                // Get request body info to register
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                System.out.println(reqData);

                RegisterRequest request = (RegisterRequest)gson.fromJson(reqData, RegisterRequest.class);


                // Do the Service
                RegisterService service = new RegisterService();
                RegisterResult result = service.register(request);


                if(!result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }

                System.out.println(result.getMessage());

                // Get response body output stream
                OutputStream resBody = exchange.getResponseBody();
                String resData = gson.toJson(result);
                writeString(resData, resBody);
                resBody.close();
                success = true;

                if (!success) { // check for failure
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    exchange.getResponseBody().close();
                }


            }

            // request method not post
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST,0); // 400 status code
                exchange.getResponseBody().close(); //not gonna send back any data
            }



        } // End of try
        catch (IOException | DataAccessException e) { // Unhandled exception: DataAccess.DataAccessException
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }

    }



} // Class Closing


