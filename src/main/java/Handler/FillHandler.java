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
import Result.FillResult;
import Request.FillRequest;
import Service.FillService;


public class FillHandler extends Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException { // HTTP Method: POST

        System.out.println("In fill Handler");

        try { // Beginning of try
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                // get URl
                String [] URL = exchange.getRequestURI().toString().split("/");
                String username = URL[2]; // index 2, 3rd element

                // generation: default 4
                int numGeneration = 4;
                if(URL.length > 3) {
                    numGeneration = Integer.parseInt(URL[3]);
                }

                FillRequest request = new FillRequest(username,numGeneration);
                FillService service = new FillService();
                FillResult result = service.fill(request);



                // Send HTTP Response to client
                if(result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }

                // Serialize
                OutputStream resBody = exchange.getResponseBody();
                Gson gson = new Gson();
                String resData = gson.toJson(result);
                writeString(resData, resBody);
                resBody.close();

                success = true;

            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close(); // don't send response
            }
        } catch (DataAccessException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }


}
