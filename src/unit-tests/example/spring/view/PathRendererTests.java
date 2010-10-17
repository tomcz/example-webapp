package example.spring.view;

import example.spring.Path;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PathRendererTests {

    @Test
    public void shouldPrependContextPathAndServletPath() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/context");
        request.setServletPath("/servlet");

        PathRenderer renderer = new PathRenderer(request, response);
        String text = renderer.toString(new Path("/path", true, true), "none");

        assertThat(text, is("/context/servlet/path"));
    }

    @Test
    public void shouldPrependContextPath() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/context");
        request.setServletPath("/servlet");

        PathRenderer renderer = new PathRenderer(request, response);
        String text = renderer.toString(new Path("/path", true, false), "none");

        assertThat(text, is("/context/path"));
    }

    @Test
    public void shouldPrependServletPath() {
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/context");
        request.setServletPath("/servlet");

        PathRenderer renderer = new PathRenderer(request, response);
        String text = renderer.toString(new Path("/path", false, true), "none");

        assertThat(text, is("/servlet/path"));
    }
}
