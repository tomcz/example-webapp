package example.domain.web.tests;

import org.junit.Test;

public class DomainWorkflowTests {

    @Test
    public void shouldStoreFormDetailsCorrectly() {
        PageDriver driver = Application.open("/example");

        driver.expect(IndexPage.class).createNewForm();
        driver.expect(FormPage.class).submitForm("homer", "simpson", "10/03/2010");

        SuccessPage successPage = driver.expect(SuccessPage.class);
        successPage.showsValues("homer", "simpson", "10/03/2010");
        String formId = successPage.getFormId();
        successPage.navigateToIndexPage();

        driver.expect(IndexPage.class).navigateToForm(formId);
        driver.expect(FormPage.class).showsValues("homer", "simpson", "10/03/2010");
    }

    @Test
    public void shouldRedisplayFormWithErrorMessages() {
        PageDriver driver = Application.open("/example");
        driver.expect(IndexPage.class).createNewForm();
        driver.expect(FormPage.class).submitForm("homer", "error", "10/03/2010");
        driver.expect(FormPage.class).showsFieldTwoError("Oops - <error> was provided");
    }

    @Test
    public void shouldDisplayErrorPageWithErrorReferenceWhenRequestingBadPresenter() {
        PageDriver driver = Application.open("/example/page/bad");
        driver.expect(ErrorPage.class).showsErrorId();
    }
}
