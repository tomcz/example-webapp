package example.spring;

import example.utils.Maps;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.Map;

public class PathBuilderTests {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreatePostLinkForGetHandler() throws Exception {
        PathBuilder.pathToPost(GetHandler.class, "documentId", "new");
    }

    @Test
    public void shouldCreateGetLinkForGetHandler() throws Exception {
        Path path = PathBuilder.pathToGet(GetHandler.class, "documentId", "new");
        assertThat(path.getUri(), is("/new/success.go"));
    }

    @Test
    public void shouldCreateRedirectToGetHandler() throws Exception {
        PathRedirectView view = PathBuilder.redirectTo(GetHandler.class, "documentId", "new");

        Map<String, ?> model = Collections.emptyMap();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        view.render(model, request, response);

        assertThat(response.getRedirectedUrl(), is("/new/success.go"));
    }

    @Test
    public void shouldCreatePostLinkToPostHandler() throws Exception {
        Path path = PathBuilder.pathToPost(PostHandler.class, "documentId", "old");
        assertThat(path.getUri(), is("/old/error.go"));
    }

    @Test
    public void shouldCreateLinkToNamedMethod() throws Exception {
        Map<String, String> params = Maps.create("documentId", "old");
        Path path = PathBuilder.pathTo(PostHandler.class, "handlePostRequest", params);
        assertThat(path.getUri(), is("/old/error.go"));
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
