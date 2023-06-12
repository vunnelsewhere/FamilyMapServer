package Handler;

import java.io.*;
import java.net.*;

import DataAccess.DataAccessException;
import Request.PersonRequest;
import Result.PersonResult;
import Service.PersonService;
import com.sun.net.httpserver.*;

import Result.EventResult;
import Request.EventRequest;
import Service.EventService;
import com.google.gson.*;

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException { // // HTTP Method: GET
        System.out.println("In All Event Handler");
        boolean success = false;

        try {
            // Determine the HTTP request type (POST).
            if (exchange.getRequestMethod().toLowerCase().equals("get")) { // expected a get request

                // Get the HTTP request headers
                Headers reqHeaders = exchange.getRequestHeaders();

                // Check to see if an "Authorization" header is present
                if (reqHeaders.containsKey("Authorization")) {

                    // Extract the auth token from the "Authorization" header
                    String authToken = reqHeaders.getFirst("Authorization"); // authorization header contains the authtoken

                    EventRequest request = new EventRequest(authToken);
                    EventResult result = EventService.getAllEvent(request);

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
