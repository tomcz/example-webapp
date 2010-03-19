package example.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebServer {

    private Server server;

    public void start(int port) throws Exception {
        server = new Server(port);
        server.addHandler(new WebAppContext("web", "/"));
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public static void main(String[] args) throws Exception {
        new WebServer().start(8080);
    }
}
