package Handler;

import com.sun.net.httpserver.*;

import java.io.*;
import java.net.*;
import java.nio.file.Files;

/*
 * Ignore everything but GET requests, Get the request URL from the exchange
 */
public class FileHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) { // Ignore everything but GET requests

                // Get the request URL from the exchange
                String urlPath = exchange.getRequestURI().toString();
                // If urlPath is null or "/", set urlPath = "/index.html"
                if (urlPath == null || urlPath.equals("/")) {
                    urlPath = "/index.html";
                }

                // Append urlPath to a relative path (no leading slash) to the directory containing the files
                String filePath = "web" + urlPath;

                // Create a file object and check if the file exists
                File file = new File(filePath);

                // OutputStream resBody = null; -> in the else statement it is still null
                OutputStream resBody = exchange.getResponseBody();
                // If the file exists, read the file and write it to the HttpExchange's output stream
                if (file.exists()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    // resBody = exchange.getResponseBody();
                    Files.copy(file.toPath(),resBody);
                }
                // If the file does not exist, return a 404 (not found) error
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                    filePath = "web/HTML/404.html";
                    file = new File(filePath);
                    Files.copy(file.toPath(), resBody); // send the 404 page to the response body

                }

                resBody.close();
                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close(); // don't send response
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }
}
