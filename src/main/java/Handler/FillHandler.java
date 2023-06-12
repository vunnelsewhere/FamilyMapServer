package Handler;

import java.io.*;
import java.net.*;

import DataAccess.DataAccessException;
import Result.ClearResult;
import Service.ClearService;
import com.sun.net.httpserver.*;


import Result.FillResult;
import Request.FillRequest;
import Service.FillService;
import com.google.gson.*;

public class FillHandler implements HttpHandler { // HTTP Method: POST

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("In fill Handler");
        boolean success = false;

        try {

            // Determine the HTTP request type (POST).
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

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
