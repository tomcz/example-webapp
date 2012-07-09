package example.domain.web.webdriver;

import org.junit.Test;

public class DomainWorkflowTests {

    @Test
    public void shouldStoreFormDetailsCorrectly() {
        Browser browser = Application.open("/example");

        browser.shows(IndexPage.class).createNewForm();
        browser.shows(FormPage.class).submitForm("homer", "option1", "10/03/2010");

        SuccessPage successPage = browser.shows(SuccessPage.class);
        successPage.showsValues("homer", "option1", "10/03/2010");

        String formId = successPage.getFormId();
        successPage.navigateToIndexPage();

        browser.shows(IndexPage.class).navigateToForm(formId);
        browser.shows(FormPage.class).showsValues("homer", "option1", "10/03/2010");
    }

    @Test
    public void shouldRedisplayFormWithErrorMessages() {
        Browser browser = Application.open("/example");
        browser.shows(IndexPage.class).createNewForm();
        browser.shows(FormPage.class).submitForm("homer", "error", "10/03/2010");
        browser.shows(FormPage.class).showsErrorForFieldTwo("Oops - <error> was provided");
    }
}
