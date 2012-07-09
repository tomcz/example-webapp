package example.domain.web.nodriver;

import org.junit.Test;

public class DomainWorkflowTests {

    private Browser browser = new Browser();

    @Test
    public void shouldStoreFormDetailsCorrectly() {
        IndexPage indexPage = browser.get("/forms", IndexPage.class);

        FormPage formPage = indexPage.createNewForm();
        formPage.setValues("homer", "option1", "10/03/2010");

        SuccessPage successPage = formPage.submitAndExpect(SuccessPage.class);
        successPage.showsValues("homer", "option1", "10/03/2010");
        String formId = successPage.getFormId();

        indexPage = successPage.navigateToIndexPage();
        formPage = indexPage.navigateToForm(formId);

        formPage.showsValues("homer", "option1", "10/03/2010");
    }

    @Test
    public void shouldRedisplayFormWithErrorMessages() {
        IndexPage indexPage = browser.get("/forms", IndexPage.class);

        FormPage formPage = indexPage.createNewForm();
        formPage.setValues("homer", "error", "10/03/2010");

        formPage = formPage.submitAndExpect(FormPage.class);
        formPage.showsErrorForFieldTwo("Oops - <error> was provided");
    }
}
