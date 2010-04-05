package example.domain.web;

import example.jetty.WebServer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import java.net.ServerSocket;
import java.util.List;

public class WebServerDriver {

    private WebServer server;
    private WebDriver driver;
    private int serverPort;

    public void getURL(String url) {
        driver.get("http://localhost:" + serverPort + url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public WebElement findElement(By selector) {
        return driver.findElement(selector);
    }

    public List<WebElement> findElements(By selector) {
        return driver.findElements(selector);
    }

    public <T> T initPage(Class<T> pageClass) {
        return PageFactory.initElements(driver, pageClass);
    }

    public void start() throws Exception {
        serverPort = findFreePort();
        server = startServer(serverPort);
        driver = new FirefoxDriver();
    }

    public void stop() {
        stopDriver();
        stopServer();
    }

    private static WebServer startServer(int serverPort) throws Exception {
        WebServer server = new WebServer();
        server.start(serverPort);
        return server;
    }

    private static int findFreePort() throws Exception {
        ServerSocket socket = new ServerSocket(0);
        int port = socket.getLocalPort();
        socket.close();
        return port;
    }

    private void stopDriver() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stopServer() {
        if (server != null) {
            try {
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
