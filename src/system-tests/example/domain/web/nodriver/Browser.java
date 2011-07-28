package example.domain.web.nodriver;

import example.spring.tests.SpringDispatcherServlet;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Browser {

    private String currentURI;

    public String currentURI() {
        return currentURI;
    }

    public <T> T get(String requestURI, Class<T> pageClass) {
        return send(new MockHttpServletRequest("GET", requestURI), pageClass);
    }

    public <T> T send(MockHttpServletRequest request, Class<T> pageClass) {
        try {
            MockHttpServletResponse response = sendRequest(request, 0);
            return createPage(pageClass, response);

        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    private MockHttpServletResponse sendRequest(MockHttpServletRequest request, int requestCount) throws Exception {
        if (requestCount > 5) {
            throw new IllegalStateException("Too many redirects to " + request.getRequestURI());
        }
        currentURI = request.getRequestURI();
        MockHttpServletResponse response = SpringDispatcherServlet.create().process(request);
        String redirect = response.getRedirectedUrl();
        if (redirect != null) {
            response = sendRequest(new MockHttpServletRequest("GET", redirect), requestCount + 1);
        }
        return response;
    }

    private <T> T createPage(Class<T> pageClass, MockHttpServletResponse response) throws Exception {
        HtmlPage htmlPage = new HtmlPage(response, this);
        Constructor<T> constructor = pageClass.getConstructor(htmlPage.getClass());
        return constructor.newInstance(htmlPage);
    }

    private void handleException(Exception ex) {
        if (ex instanceof InvocationTargetException) {
            handleInvocationException((InvocationTargetException) ex);

        } else if (ex instanceof RuntimeException) {
            throw (RuntimeException) ex;

        } else {
            throw new RuntimeException(ex);
        }
    }

    private void handleInvocationException(InvocationTargetException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
        }
        if (cause instanceof Error) {
            throw (Error) cause;
        }
        throw new RuntimeException(cause);
    }
}
