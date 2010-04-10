package example.domain.web.tests;

import org.openqa.selenium.By;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class IndexPage implements Page {

    private Browser browser;

    public void verify(Browser browser) {
        assertThat(browser.getBodyClass(), equalTo("index"));
        this.browser = browser;
    }

    public void createNewForm() {
        browser.findElement(By.linkText("here")).click();
    }

    public void navigateToForm(String formId) {
        browser.findElement(By.linkText(formId)).click();
    }
}
