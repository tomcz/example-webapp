package example.spring.view;

import org.junit.Test;
import org.mockito.InOrder;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

public class ViewWithStatusCodeTests {

    @Test
    public void shouldSetStatusCodeIntoResponseBeforeInvokingDelegate() throws Exception {
        Map<String, ?> model = Collections.emptyMap();

        View delegate = mock(View.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        ViewWithStatusCode view = new ViewWithStatusCode(404, delegate);
        view.render(model, request, response);

        InOrder order = inOrder(response, delegate);
        order.verify(response).setStatus(404);
        order.verify(delegate).render(model, request, response);
    }
}
