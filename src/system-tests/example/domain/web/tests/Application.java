package example.domain.web.tests;

import example.jetty.WebServer;

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
            throw new RuntimeException(e);
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
        browser = new Browser(port);
        server = new WebServer(port);
        server.start();
    }

    private Browser get(String url) {
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
