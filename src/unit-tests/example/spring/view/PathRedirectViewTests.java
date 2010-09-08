package example.spring.view;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PathRedirectViewTests {

    @Test
    public void shouldCreateUrlWithContextAndServletPaths() throws Exception {
        PathRedirectView view = RedirectBuilder.redirectTo(GetHandler.class).build();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/context");
        request.setServletPath("/servlet");

        Map<String, ?> model = Collections.emptyMap();
        MockHttpServletResponse response = new MockHttpServletResponse();

        view.render(model, request, response);

        assertThat(response.getRedirectedUrl(), is("/context/servlet/path"));
    }

    @Test
    public void shouldCreateContextRelativeUrl() throws Exception {
        PathRedirectView view = RedirectBuilder.redirectTo(GetHandler.class).contextRelative(true).servletRelative(false).build();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/context");
        request.setServletPath("/servlet");

        Map<String, ?> model = Collections.emptyMap();
        MockHttpServletResponse response = new MockHttpServletResponse();

        view.render(model, request, response);

        assertThat(response.getRedirectedUrl(), is("/context/path"));
    }

    @Test
    public void shouldCreateServletRelativeUrl() throws Exception {
        PathRedirectView view = RedirectBuilder.redirectTo(GetHandler.class).contextRelative(false).servletRelative(true).build();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/context");
        request.setServletPath("/servlet");

        Map<String, ?> model = Collections.emptyMap();
        MockHttpServletResponse response = new MockHttpServletResponse();

        view.render(model, request, response);

        assertThat(response.getRedirectedUrl(), is("/servlet/path"));
    }

    private class GetHandler {

        @RequestMapping(value = "/path", method = RequestMethod.GET)
        public String handleGetRequest() {
            throw new UnsupportedOperationException();
        }
    }
}
