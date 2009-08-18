package example.spring.binding;

import example.spring.path.PathBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.ServletWebRequest;

import java.lang.reflect.Method;

public class PathBuilderArgumentResolverTests {

    @Test
    public void shouldCreatePathFinderThatDoesNotPrependServletPath() throws Exception {
        Method testMethod = getClass().getMethod("testMethod", String.class, PathBuilder.class);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/test");

        PathBuilderArgumentResolver resolver = new PathBuilderArgumentResolver();
        resolver.setUseServletPath(false);

        Object arg = resolver.resolveArgument(new MethodParameter(testMethod, 1), new ServletWebRequest(request));
        PathBuilder builder = (PathBuilder) arg;

        assertThat(builder.httpGet(GetHandler.class).getUri(), is("/success.go"));
    }

    @Test
    public void shouldCreatePathFinderThatPrependsServletPath() throws Exception {
        Method testMethod = getClass().getMethod("testMethod", String.class, PathBuilder.class);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/test");

        PathBuilderArgumentResolver resolver = new PathBuilderArgumentResolver();
        resolver.setUseServletPath(true);

        Object arg = resolver.resolveArgument(new MethodParameter(testMethod, 1), new ServletWebRequest(request));
        PathBuilder builder = (PathBuilder) arg;

        assertThat(builder.httpGet(GetHandler.class).getUri(), is("/test/success.go"));
    }

    @Test
    public void shouldNotResolveOtherArgumentTypes() throws Exception {
        Method testMethod = getClass().getMethod("testMethod", String.class, PathBuilder.class);

        MockHttpServletRequest request = new MockHttpServletRequest();

        PathBuilderArgumentResolver resolver = new PathBuilderArgumentResolver();
        Object arg = resolver.resolveArgument(new MethodParameter(testMethod, 0), new ServletWebRequest(request));

        assertThat(arg, is(WebArgumentResolver.UNRESOLVED));
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void testMethod(String param1, PathBuilder param2) {
    }

    @RequestMapping("/success.go")
    private class GetHandler {

        @RequestMapping(method = RequestMethod.GET)
        public String handleGetRequest() {
            throw new UnsupportedOperationException();
        }
    }
}
