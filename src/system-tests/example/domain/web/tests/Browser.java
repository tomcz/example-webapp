package example.domain.web.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Browser {

    private WebDriver driver;
    private int serverPort;

    public Browser(int serverPort) {
        this.driver = new FirefoxDriver();
        this.serverPort = serverPort;
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

    public WebElement waitForElement(final By selector) {
        WebDriverWait wait = new WebDriverWait(driver, 5000);
        return wait.until(new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(selector);
            }
        });
    }

    public String getBodyClass() {
        return waitForElement(By.tagName("body")).getAttribute("class");
    }

    public <T extends Page> T shows(Class<T> pageClass) {
        T page = PageFactory.initElements(driver, pageClass);
        page.verify(this);
        return page;
    }

    public void stop() {
        try {
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
