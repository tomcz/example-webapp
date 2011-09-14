package example.spring;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static example.spring.PathBuilder.pathTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PathBuilderTests {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreatePostLinkForGetHandler() throws Exception {
        pathTo(GetHandler.class).POST().withVar("documentId", "new").build();
    }

    @Test
    public void shouldCreateGetLinkForGetHandler() throws Exception {
        String path = pathTo(GetHandler.class).withVar("documentId", "new").build();
        assertThat(path, is("/new/success.go"));
    }

    @Test
    public void shouldCreatePostLinkToPostHandler() throws Exception {
        String path = pathTo(PostHandler.class).POST().withVar("documentId", "old").build();
        assertThat(path, is("/old/error.go"));
    }

    @Test
    public void shouldCreateLinkToNamedMethod() throws Exception {
        String path = pathTo(PostHandler.class).withMethod("handlePostRequest").withVar("documentId", "old").build();
        assertThat(path, is("/old/error.go"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailCreateLinkToUnAnnotatedNamedMethod() throws Exception {
        pathTo(PostHandler.class).withMethod("test").withVar("documentId", "old").build();
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
