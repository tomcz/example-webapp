package example.spring.view;

import com.watchitlater.spring.Renderer;
import com.watchitlater.spring.WebAttributeRenderer;
import example.spring.Path;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PathRenderer extends WebAttributeRenderer implements Renderer {

    private final HttpServletRequest request;

    public PathRenderer(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
    }

    public Class getTypeToRender() {
        return Path.class;
    }

    public String toString(Object obj) {
        return super.toString(render(obj));
    }

    public String toString(Object obj, String formatName) {
        return super.toString(render(obj), formatName);
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
