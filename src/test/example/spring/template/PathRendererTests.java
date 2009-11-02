package example.spring.template;

import example.spring.Path;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

public class PathRendererTests {

    @Test
    public void shouldPrependContextPathAndServletPathByDefault() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/context");
        request.setServletPath("/servlet");

        PathRenderer renderer = new PathRenderer(request);
        String text = renderer.toString(new Path("/path"), "none");

        assertThat(text, is("/context/servlet/path"));
    }
}
