package example.domain.web.tests;

import org.openqa.selenium.By;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class IndexPage implements Page {
    private PageDriver driver;

    public void verify(PageDriver driver) {
        assertThat(driver.getBodyClass(), equalTo("index"));
        this.driver = driver;
    }

    public void createNewForm() {
        driver.findElement(By.linkText("here")).click();
    }

    public void navigateToForm(String formId) {
        driver.findElement(By.linkText(formId)).click();
    }
}
