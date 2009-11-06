package example.spring.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.View;

import java.util.Collections;
import java.util.Map;

public class ViewWithStatusCodeTests {

    @Test
    public void shouldSetStatusCodeIntoResponse() throws Exception {
        View delegate = mock(View.class);

        Map<String, ?> model = Collections.emptyMap();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        ViewWithStatusCode view = new ViewWithStatusCode(404, delegate);
        view.render(model, request, response);

        assertThat(response.getStatus(), equalTo(404));

        verify(delegate).render(model, request, response);
    }
}
