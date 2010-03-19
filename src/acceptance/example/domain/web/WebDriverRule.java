package example.domain.web;

import example.jetty.WebServer;
import org.junit.rules.ExternalResource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import java.net.ServerSocket;
import java.util.List;

public class WebDriverRule extends ExternalResource {

    private WebServer server;
    private WebDriver driver;
    private int serverPort;

    public WebDriver driver() {
        return driver;
    }

    public void get(String url) {
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

    @Override
    protected void before() throws Throwable {
        serverPort = findFreePort();
        server = startServer(serverPort);
        driver = new FirefoxDriver();
    }

    @Override
    protected void after() {
        quitDriver();
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

    private void quitDriver() {
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
