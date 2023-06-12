package Handler;

import java.io.*;
import java.net.*;

import DataAccess.DataAccessException;
import com.sun.net.httpserver.*;


import Result.ClearResult;
import Service.ClearService;
import com.google.gson.*;

public class ClearHandler implements HttpHandler { // HTTP Method: POST

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("In clear Handler");
        boolean success = false;

        try {

            // Determine the HTTP request type (POST).
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {


                ClearService service = new ClearService();
                ClearResult result = service.clear();

                // Send HTTP Response to client
                if (result.isSuccess()) {
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

