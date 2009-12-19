package example.spring.view;

import example.utils.Maps;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class StaticViewTests {

    @Test
    public void shouldNotDoAnythingWhenUsedAsNoop() throws Exception {
        Map<String, ?> model = Maps.create();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StaticView view = new StaticView();
        view.render(model, request, response);

        verifyZeroInteractions(response);
        verifyZeroInteractions(request);
    }

    @Test
    public void shouldSetContentTypeWhenOneHasBeenProvided() throws Exception {
        Map<String, ?> model = Maps.create();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        StaticView view = new StaticView();
        view.setContentType("text/plain");

        view.render(model, request, response);

        verify(response).setContentType("text/plain");
    }

    @Test
    public void shouldWriteResponseBodyTextWhenOneHasBeenProvided() throws Exception {
        Map<String, ?> model = Maps.create();
        HttpServletRequest request = mock(HttpServletRequest.class);
        MockHttpServletResponse response = new MockHttpServletResponse();

        StaticView view = new StaticView();
        view.setContentText("hello");

        view.render(model, request, response);

        assertThat(response.getContentLength(), is(5));
        assertThat(response.getContentAsString(), is("hello"));
    }

    @Test
    public void shouldWriteResponseBodyBytesWhenTheyHaveBeenProvided() throws Exception {
        Map<String, ?> model = Maps.create();
        HttpServletRequest request = mock(HttpServletRequest.class);
        MockHttpServletResponse response = new MockHttpServletResponse();

        StaticView view = new StaticView();
        view.setContentBytes("hello".getBytes());

        view.render(model, request, response);

        assertThat(response.getContentLength(), is(5));
        assertThat(new String(response.getContentAsByteArray()), is("hello"));
    }
}
