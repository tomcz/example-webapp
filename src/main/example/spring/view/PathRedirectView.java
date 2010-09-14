package example.spring.view;

import example.spring.Path;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class PathRedirectView extends RedirectView {

    private final Path path;

    public PathRedirectView(Path path) {
        setExposeModelAttributes(false);
        this.path = path;
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        setUrl(path.isServletRelative() ? request.getServletPath() + path.getUri() : path.getUri());
        setContextRelative(path.isContextRelative());
        afterPropertiesSet();

        super.render(model, request, response);
    }
}
