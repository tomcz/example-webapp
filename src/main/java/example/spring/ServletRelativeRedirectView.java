package example.spring;

import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ServletRelativeRedirectView extends RedirectView {

    public ServletRelativeRedirectView(String url) {
        super(url, true);
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        setUrl(request.getServletPath() + getUrl());
        super.render(model, request, response);
    }
}
