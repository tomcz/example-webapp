package example.spring.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

public class ErrorViewTests {

    @Test
    public void shouldSendErrorWithGivenStatusCode() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();

        ErrorView view = new ErrorView(404);
        view.render(null, null, response);

        assertThat(response.getStatus(), equalTo(404));
    }

    @Test
    public void shouldSendErrorWithGivenStatusCodeAndMessage() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();

        ErrorView view = new ErrorView(500, "oops");
        view.render(null, null, response);

        assertThat(response.getStatus(), equalTo(500));
        assertThat(response.getErrorMessage(), equalTo("oops"));
    }
}
