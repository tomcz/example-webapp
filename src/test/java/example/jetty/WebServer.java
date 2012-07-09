package example.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import java.util.List;

import static ch.lambdaj.collection.LambdaCollections.with;
import static org.hamcrest.Matchers.endsWith;

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
        WebAppContext context = new WebAppContext("src/main/webapp", "/example");
        server.addHandler(withoutTaglibs(context));
        server.start();
        return this;
    }

    private WebAppContext withoutTaglibs(WebAppContext context) {
        String[] configurationClasses = context.getConfigurationClasses();
        List<String> withoutTaglibs = with(configurationClasses).remove(endsWith("TagLibConfiguration"));
        context.setConfigurationClasses(withoutTaglibs.toArray(new String[withoutTaglibs.size()]));
        return context;
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
