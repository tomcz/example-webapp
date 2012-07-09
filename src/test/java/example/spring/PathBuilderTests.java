package example.spring;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;
import java.util.Map;

import static example.spring.PathBuilder.pathTo;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PathBuilderTests {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreatePostLinkForGetHandler() {
        pathTo(GetHandler.class).POST().withVar("documentId", "new").build();
    }

    @Test
    public void shouldCreateGetLinkForGetHandler() {
        String path = pathTo(GetHandler.class).withVar("documentId", "new").build();
        assertThat(path, equalTo("/new/success.go"));
    }

    @Test
    public void shouldCreatePostLinkToPostHandler() {
        String path = pathTo(PostHandler.class).POST().withVar("documentId", "old").build();
        assertThat(path, equalTo("/old/error.go"));
    }

    @Test
    public void shouldCreateLinkToNamedMethod() {
        String path = pathTo(PostHandler.class).withMethodName("handlePostRequest").withVar("documentId", "old").build();
        assertThat(path, equalTo("/old/error.go"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailCreateLinkToUnAnnotatedNamedMethod() {
        pathTo(PostHandler.class).withMethodName("test").withVar("documentId", "old").build();
    }

    @Test
    public void shouldCreateServletRelativeRedirect() throws Exception {
        RedirectView redirect = pathTo(GetHandler.class).withVar("documentId", "new").redirect();

        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setContextPath("/context");
        request.setServletPath("/servlet");

        Map<String, ?> model = Collections.emptyMap();
        redirect.render(model, request, response);

        assertThat(response.getRedirectedUrl(), equalTo("/context/servlet/new/success.go"));
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

        public String test() {
            throw new UnsupportedOperationException();
        }
    }
}
