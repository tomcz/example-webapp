package example.spring;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PathBuilderTests {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreatePostLinkForGetHandler() throws Exception {
        new PathBuilder(GetHandler.class).POST().withVar("documentId", "new").build();
    }

    @Test
    public void shouldCreateGetLinkForGetHandler() throws Exception {
        Path path = new PathBuilder(GetHandler.class).withVar("documentId", "new").build();
        assertThat(path.getUri(), is("/new/success.go"));
    }

    @Test
    public void shouldCreateRedirectToGetHandler() throws Exception {
        PathRedirectView view = new PathBuilder(GetHandler.class).withVar("documentId", "new").redirect();

        Map<String, ?> model = Collections.emptyMap();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        view.render(model, request, response);

        assertThat(response.getRedirectedUrl(), is("/new/success.go"));
    }

    @Test
    public void shouldCreatePostLinkToPostHandler() throws Exception {
        Path path = new PathBuilder(PostHandler.class).POST().withVar("documentId", "old").build();
        assertThat(path.getUri(), is("/old/error.go"));
    }

    @Test
    public void shouldCreateLinkToNamedMethod() throws Exception {
        Path path = new PathBuilder(PostHandler.class)
                .withMethod("handlePostRequest")
                .withVar("documentId", "old")
                .build();

        assertThat(path.getUri(), is("/old/error.go"));
    }

    @Test
    public void shouldCreateContextRelativePath() throws Exception {
        Path path = new PathBuilder(GetHandler.class)
                .withVar("documentId", "old")
                .servletRelative(false)
                .build();

        assertThat(path.isServletRelative(), is(false));
        assertThat(path.isContextRelative(), is(true));
    }

    @Test
    public void shouldCreateServletRelativePath() throws Exception {
        Path path = new PathBuilder(GetHandler.class)
                .withVar("documentId", "old")
                .contextRelative(false)
                .build();

        assertThat(path.isContextRelative(), is(false));
        assertThat(path.isServletRelative(), is(true));
    }

    @RequestMapping("/{documentId}/success.go")
    private class GetHandler {

        @RequestMapping(method = RequestMethod.GET)
        public String handleGetRequest() {
            throw new UnsupportedOperationException();
        }
    }

    private class PostHandler {

        @RequestMapping(value = "/{documentId}/error.go", method = RequestMethod.POST)
        public String handlePostRequest() {
            throw new UnsupportedOperationException();
        }
    }
}
