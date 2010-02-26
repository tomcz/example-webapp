package example.spring;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PathRedirectViewTests {

    @Test
    public void shouldCreateUrlWithContextAndServletPaths() throws Exception {
        PathRedirectView view = new PathRedirectView(new Path("/path", true, true));

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
        PathRedirectView view = new PathRedirectView(new Path("/path", true, false));

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
        PathRedirectView view = new PathRedirectView(new Path("/path", false, true));

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/context");
        request.setServletPath("/servlet");

        Map<String, ?> model = Collections.emptyMap();
        MockHttpServletResponse response = new MockHttpServletResponse();

        view.render(model, request, response);

        assertThat(response.getRedirectedUrl(), is("/servlet/path"));
    }
}
