package example.spring.view;

import com.watchitlater.spring.StringTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class CustomTemplateView extends StringTemplateView {

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        template.register(new PathRenderer(request));
        super.render(model, request, response);
    }
}
