package example.spring.view;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;

public class StaticViewTests {

    @Test
    public void shouldNotDoAnythingWhenUsedAsNoop() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);

        StaticView view = new StaticView();
        view.render(null, null, response);

        verifyZeroInteractions(response);
    }

    @Test
    public void shouldSetContentTypeWhenOneHasBeenProvided() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();

        StaticView view = new StaticView("text/plain", "hello");
        view.render(null, null, response);

        assertThat(response.getContentType(), is("text/plain"));
    }

    @Test
    public void shouldWriteResponseBodyTextWhenOneHasBeenProvided() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();

        StaticView view = new StaticView("text/plain", "hello");
        view.render(null, null, response);

        assertThat(response.getContentLength(), is(5));
        assertThat(response.getContentAsString(), is("hello"));
    }

    @Test
    public void shouldWriteResponseBodyBytesWhenTheyHaveBeenProvided() throws Exception {
        MockHttpServletResponse response = new MockHttpServletResponse();

        StaticView view = new StaticView("text/plain", "hello".getBytes());
        view.render(null, null, response);

        assertThat(response.getContentLength(), is(5));
        assertThat(new String(response.getContentAsByteArray()), is("hello"));
    }
}
