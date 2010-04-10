package example.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebServer {

    private Server server;

    public WebServer(int port) {
        server = new Server(port);
    }

    public void start() throws Exception {
        server.addHandler(new WebAppContext("web", "/example"));
        server.start();
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new WebServer(8080).start();
    }
}
