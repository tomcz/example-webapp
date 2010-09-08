package example.spring.view;

import com.watchitlater.spring.Renderer;
import com.watchitlater.spring.WebFormat;
import example.spring.Path;

import javax.servlet.http.HttpServletRequest;

public class PathRenderer implements Renderer {

    private final HttpServletRequest request;

    public PathRenderer(HttpServletRequest request) {
        this.request = request;
    }

    public Class getTypeToRender() {
        return Path.class;
    }

    public String toString(Object obj) {
        return WebFormat.html.format(render(obj));
    }

    public String toString(Object obj, String formatName) {
        return WebFormat.fromName(formatName).format(render(obj));
    }

    private String render(Object obj) {
        Path path = (Path) obj;
        String uri = path.getUri();
        if (uri.startsWith("/")) {
            if (path.isServletRelative()) {
                uri = request.getServletPath() + uri;
            }
            if (path.isContextRelative()) {
                uri = request.getContextPath() + uri;
            }
        }
        return uri;
    }
}
