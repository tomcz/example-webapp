package example.spring;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PathRedirectView implements View {

    private final Path path;
    private final RedirectView delegate;

    public PathRedirectView(Path path) {
        this.delegate = new RedirectView();
        this.delegate.setContextRelative(path.isContextRelative());
        this.delegate.setExposeModelAttributes(false);
        this.delegate.setHttp10Compatible(true);
        this.path = path;
    }

    public void setHttp10Compatible(boolean http10Compatible) {
        delegate.setHttp10Compatible(http10Compatible);
    }

    public void setExposeModelAttributes(boolean exposeModelAttributes) {
        delegate.setExposeModelAttributes(exposeModelAttributes);
    }

    public String getContentType() {
        return delegate.getContentType();
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String uri = path.getUri();
        if (path.isServletRelative() && uri.startsWith("/")) {
            uri = request.getServletPath() + uri;
        }
        delegate.setUrl(uri);
        delegate.render(model, request, response);
    }
}
