package example.spring.view;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.mockito.InOrder;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.View;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

public class ViewWithCookiesTests {

    @Test
    public void shouldAddCookiesToResponse() throws Exception {
        View delegate = mock(View.class);

        Map<String, ?> model = Collections.emptyMap();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        ViewWithCookies view = new ViewWithCookies(delegate);
        view.addCookie("key1", "test1");
        view.addCookie("key2", "test2");

        view.render(model, request, response);

        Cookie cookie1 = response.getCookie("key1");
        assertThat(cookie1, notNullValue());
        assertThat(cookie1.getValue(), is("test1"));

        Cookie cookie2 = response.getCookie("key2");
        assertThat(cookie2, notNullValue());
        assertThat(cookie2.getValue(), is("test2"));
    }

    @Test
    public void shouldSetCookiesBeforeInvokingDelegate() throws Exception {
        Map<String, ?> model = Collections.emptyMap();

        View delegate = mock(View.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        Cookie cookie = new Cookie("foo", "bar");

        ViewWithCookies view = new ViewWithCookies(delegate);
        view.addCookie(cookie);

        view.render(model, request, response);

        InOrder order = inOrder(response, delegate);
        order.verify(response).addCookie(cookie);
        order.verify(delegate).render(model, request, response);
    }
}
