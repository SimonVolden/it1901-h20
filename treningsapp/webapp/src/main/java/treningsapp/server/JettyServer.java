package treningsapp.server;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class JettyServer {

    public static void main(String[] args) {
        Server server = new Server(8999);

        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(PingHandler.class, "/ping");

        server.setHandler(handler);

        server.start();
        server.join();
    }

    
}