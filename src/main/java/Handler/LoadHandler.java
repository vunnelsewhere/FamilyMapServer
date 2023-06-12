package Handler;

import java.io.*;
import java.net.*;

import DataAccess.DataAccessException;
import com.sun.net.httpserver.*;


import Result.LoadResult;
import Request.LoadRequest;
import Service.LoadService;
import com.google.gson.*;

public class LoadHandler implements HttpHandler { // HTTP Method: POST

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("In load Handler");
        boolean success = false;

        try {

            // Determine the HTTP request type (POST).
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);

                Gson gson = new Gson();
                LoadRequest request = (LoadRequest)gson.fromJson(reqData, LoadRequest.class);

                LoadService service = new LoadService();
                LoadResult result = service.load(request);

                // Send HTTP Response to client
                if(result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }

                // Serialize
                OutputStream resBody = exchange.getResponseBody();
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

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
