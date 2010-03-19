package example.domain.web;

import org.openqa.selenium.WebElement;

public class FormPage {

    private WebElement one;
    private WebElement two;
    private WebElement date;
    private WebElement submit;

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
