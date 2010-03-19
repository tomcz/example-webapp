package example.domain.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class FormPage {

    private WebElement one;
    private WebElement two;
    private WebElement date;
    private WebElement submit;

    public static FormPage init(WebDriver driver) {
        return PageFactory.initElements(driver, FormPage.class);
    }

    public void submitForm(String one, String two, String date) {
        this.one.sendKeys(one);
        this.two.sendKeys(two);
        this.date.sendKeys(date);
        this.submit.click();
    }

    public String getOne() {
        return one.getValue();
    }

    public String getTwo() {
        return two.getValue();
    }

    public String getDate() {
        return date.getValue();
    }
}
