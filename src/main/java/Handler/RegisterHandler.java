package Handler;

import java.io.*;
import java.net.*;

import DataAccess.DataAccessException;
import com.sun.net.httpserver.*;

import Request.RegisterRequest;
import Result.RegisterResult;
import Service.RegisterService;
import com.google.gson.*;


public class RegisterHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        System.out.println("In Register Handler");
        boolean success = false;

        try {

            if (exchange.getRequestMethod().toLowerCase().equals("post")) { // HTTP Method: POST

                Headers reqHeaders = exchange.getRequestHeaders();
                // AuthToken Required: No

                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                System.out.println(reqData);

                // TODO: Claim a route based on the request data
                Gson gson = new Gson();
                RegisterRequest r = (RegisterRequest)gson.fromJson(reqData, RegisterRequest.class);

                RegisterService service = new RegisterService();
                RegisterResult result = service.register(r);

                if(result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }

                // Serialize
                OutputStream resBody = exchange.getResponseBody();
                gson = new Gson();
                String resData = gson.toJson(result);
                writeString(resData, resBody);
                resBody.close();

                success = true;



            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close(); // don't send response
            }


        }
        catch (DataAccessException e) { // Unhandled exception: DataAccess.DataAccessException
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


