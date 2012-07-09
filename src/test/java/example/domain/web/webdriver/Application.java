package example.domain.web.webdriver;

import example.jetty.WebServer;
import org.apache.commons.lang.Validate;

import java.net.ServerSocket;

public class Application {

    private static Application instance;

    private WebServer server;
    private Browser browser;

    public static Browser open(String url) {
        return application().get(url);
    }

    private static Application application() {
        if (instance != null) {
            return instance;
        }
        try {
            int port = findFreePort();
            instance = new Application();
            instance.registerShutdownHook();
            instance.startup(port);
            return instance;

        } catch (Exception e) {
            throw new RuntimeException("Application startup failed", e);
        }
    }

    private static int findFreePort() throws Exception {
        ServerSocket socket = new ServerSocket(0);
        int port = socket.getLocalPort();
        socket.close();
        return port;
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                shutdown();
            }
        }));
    }

    private void startup(int port) throws Exception {
        server = new WebServer(port).start();
        browser = new Browser(port);
    }

    private Browser get(String url) {
        Validate.notNull(browser, "Application has not started succesfully. Please check earlier failed tests.");
        browser.get(url);
        return browser;
    }

    private void shutdown() {
        if (browser != null) {
            try {
                browser.stop();
            } catch (Exception e) {
                // ignore
            }
        }
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                // ignore
            }
        }
    }
}
