package example.spring.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.mockito.InOrder;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ViewWithHeadersTests {

    @Test
    public void shouldAddCookiesToResponse() throws Exception {
        View delegate = mock(View.class);

        Map<String, ?> model = Collections.emptyMap();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        ViewWithHeaders view = new ViewWithHeaders(delegate);
        view.setHeader("Location", "foo", "bar");

        view.render(model, request, response);

        List headers = response.getHeaders("Location");

        List expected = Arrays.asList("foo", "bar");
        assertThat(headers, equalTo(expected));

        verify(delegate).render(model, request, response);
    }

    @Test
    public void shouldSetHeadersBeforeInvokingDelegate() throws Exception {
        View delegate = mock(View.class);
        Map<String, ?> model = Collections.emptyMap();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ViewWithHeaders view = new ViewWithHeaders(delegate);
        view.setHeader("Location", "foo");

        view.render(model, request, response);

        InOrder order = inOrder(response, delegate);
        order.verify(response).setHeader("Location", "foo");
        order.verify(delegate).render(model, request, response);
    }
}
