package example.domain.web;

import example.jetty.WebServer;
import org.apache.commons.lang.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.net.ServerSocket;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class DomainWorkflowTests {

    private static WebServer server;
    private static WebDriver driver;
    private static int serverPort;

    @BeforeClass
    public static void setup() throws Exception {
        serverPort = findFreePort();
        server = new WebServer();
        server.start(serverPort);
        driver = new FirefoxDriver();
    }

    private static int findFreePort() throws Exception {
        ServerSocket socket = new ServerSocket(0);
        int port = socket.getLocalPort();
        socket.close();
        return port;
    }

    @AfterClass
    public static void teardown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
        if (server != null) {
            server.stop();
        }
    }

    @Test
    public void shouldStoreFormDetailsCorrectly() {
        driver.get("http://localhost:" + serverPort);
        assertThat(bodyClass(), equalTo("index"));

        driver.findElement(By.linkText("here")).click();
        assertThat(bodyClass(), equalTo("form"));

        FormPage form = FormPage.init(driver);
        form.submitForm("homer", "simpson", "10/03/2010");

        assertThat(bodyClass(), equalTo("success"));
        assertThat(elementText("one"), equalTo("homer"));
        assertThat(elementText("two"), equalTo("simpson"));
        assertThat(elementText("date"), equalTo("10/03/2010"));

        String successId = StringUtils.substringAfterLast(driver.getCurrentUrl(), "/");

        driver.findElement(By.xpath("//a[@id='index']")).click();
        assertThat(bodyClass(), equalTo("index"));

        driver.findElement(By.linkText(successId)).click();
        assertThat(bodyClass(), equalTo("form"));

        form = FormPage.init(driver);
        assertThat(form.getOne(), equalTo("homer"));
        assertThat(form.getTwo(), equalTo("simpson"));
        assertThat(form.getDate(), equalTo("10/03/2010"));
    }

    private String bodyClass() {
        return driver.findElement(By.tagName("body")).getAttribute("class");
    }

    private String elementText(String id) {
        return driver.findElement(By.id(id)).getText();
    }
}
