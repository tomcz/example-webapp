package example.spring.view;

import com.watchitlater.spring.StringTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class CustomTemplateView extends StringTemplateView {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        template.register(new PathRenderer(request));
        super.render(model, request, response);
    }
}
