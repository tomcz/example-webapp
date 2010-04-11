package example.domain.web.tests;

import example.jetty.WebServer;
import org.apache.commons.lang.Validate;

import java.net.ServerSocket;

public class Application {

    private static Application instance;

    private WebServer server;
    private Browser browser;

    public static Browser open(String url) {
        return instance().get(url);
    }

    private static Application instance() {
        if (instance != null) {
            return instance;
        }
        try {
            int port = findFreePort();
            instance = new Application();
            registerShutdownHook();
            instance.start(port);
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

    private static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));
    }

    private void start(int port) throws Exception {
        server = new WebServer(port).start();
        browser = new Browser(port);
    }

    private Browser get(String url) {
        Validate.notNull(browser, "Application has not started succesfully. Please check earlier failed tests.");
        browser.get(url);
        return browser;
    }

    private void stop() {
        if (browser != null) {
            browser.stop();
        }
        if (server != null) {
            server.stop();
        }
    }

    private static class ShutdownHook implements Runnable {
        public void run() {
            instance.stop();
        }
    }
}
