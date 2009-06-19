package example.spring.path;

import example.utils.Maps;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

public class RequestMappingPathBuilderTests {

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToCreatePostLinkForGetHandler() throws Exception {
        new RequestMappingPathBuilder().httpPost(GetHandler.class, "documentId", "new");
    }

    @Test
    public void shouldCreateGetLinkForGetHandler() throws Exception {
        String link = new RequestMappingPathBuilder().httpGet(GetHandler.class, "documentId", "new");
        assertThat(link, is("/new/success.go"));
    }

    @Test
    public void shouldPrependServletPathToLink() throws Exception {
        String link = new RequestMappingPathBuilder("/test").httpGet(GetHandler.class, "documentId", "new");
        assertThat(link, is("/test/new/success.go"));
    }

    @Test
    public void shouldCreateRedirectToGetHandler() throws Exception {
        RedirectView view = new RequestMappingPathBuilder().redirectTo(GetHandler.class, "documentId", "new");
        assertThat(view.getUrl(), is("/new/success.go"));
    }

    @Test
    public void shouldCreatePostLinkToPostHandler() throws Exception {
        String link = new RequestMappingPathBuilder().httpPost(PostHandler.class, "documentId", "old");
        assertThat(link, is("/old/error.go"));
    }

    @Test
    public void shouldCreateLinkToNamedMethod() throws Exception {
        Map<String, String> params = Maps.create("documentId", "old");
        String link = new RequestMappingPathBuilder().build(PostHandler.class, "handlePostRequest", params);
        assertThat(link, is("/old/error.go"));
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
