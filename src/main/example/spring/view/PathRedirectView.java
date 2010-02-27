package example.spring.view;

import example.spring.Path;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PathRedirectView implements View {

    private final Path path;
    private final RedirectView redirect;

    public PathRedirectView(Path path) {
        this.redirect = new RedirectView();
        this.path = path;
    }

    public void setHttp10Compatible(boolean http10Compatible) {
        redirect.setHttp10Compatible(http10Compatible);
    }

    public void setExposeModelAttributes(boolean exposeModelAttributes) {
        redirect.setExposeModelAttributes(exposeModelAttributes);
    }

    public String getContentType() {
        return redirect.getContentType();
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        String uri = path.getUri();
        if (path.isServletRelative() && uri.startsWith("/")) {
            uri = request.getServletPath() + uri;
        }
        redirect.setUrl(uri);
        redirect.setContextRelative(path.isContextRelative());
        redirect.render(model, request, response);
    }
}
