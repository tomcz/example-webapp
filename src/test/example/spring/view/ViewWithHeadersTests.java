package example.spring.view;

import org.junit.Test;
import org.mockito.InOrder;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

public class ViewWithHeadersTests {

    @Test
    public void shouldAddHeadersToResponse() throws Exception {
        Map<String, ?> model = Collections.emptyMap();

        View delegate = mock(View.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ViewWithHeaders view = new ViewWithHeaders(delegate);
        view.setHeader("Location", "foo", "bar");

        view.render(model, request, response);

        verify(response).setHeader("Location", "foo");
        verify(response).addHeader("Location", "bar");
    }

    @Test
    public void shouldSetHeadersBeforeInvokingDelegate() throws Exception {
        Map<String, ?> model = Collections.emptyMap();

        View delegate = mock(View.class);
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
