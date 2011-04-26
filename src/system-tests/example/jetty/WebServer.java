package example.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class WebServer {

    private Server server;

    public WebServer(int port) {
        server = new Server(port);
    }

    public WebServer start() throws Exception {
        WebAppContext context = new WebAppContext("src/webapp", "/example");
        context.setConfigurationClasses(removeTagLibConfiguration(context));

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
        new WebServer(8080).start();
    }

    private String[] removeTagLibConfiguration(WebAppContext context) {
        List<String> configurationClasses = new ArrayList<String>();
        Collections.addAll(configurationClasses, context.getConfigurationClasses());
        Iterator<String> itr = configurationClasses.iterator();
        while (itr.hasNext()) {
            if (itr.next().endsWith("TagLibConfiguration")) {
                itr.remove();
            }
        }
        return configurationClasses.toArray(new String[configurationClasses.size()]);
    }
}
