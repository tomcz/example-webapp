package example.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class WebServer {

    private Server server;

    public WebServer(int port) {
        server = new Server(port);
    }

    public WebServer stopAtShutdown() {
        server.setStopAtShutdown(true);
        return this;
    }

    public WebServer start() throws Exception {
        WebAppContext context = new WebAppContext("src/webapp", "/example");
        server.addHandler(context);
        server.start();
        return this;
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new WebServer(8080).stopAtShutdown().start();
    }
}
