package Server;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import Handler.*;

public class Server {
    private static final int MAX_WAITING_CONNECTIONS = 12;
    private HttpServer server;

    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server");
        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS); // the other parameter (CS460)

        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating contexts");

        // Create and install the default HTTP handler
        server.createContext("/", new FileHandler());



        // Create and install the HTTP handlers......
       server.createContext("/user/register", new RegisterHandler());


        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/person/", new OnePersonHandler());
       server.createContext("/event", new EventHandler());
        // server.createContext("/event/" new OneEventHandler());




        System.out.println("Starting server");

        server.start();

        System.out.println("Server started");
    }

    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
