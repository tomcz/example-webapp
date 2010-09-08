package example.spring.view;

import com.watchitlater.spring.StringTemplateView;
import com.watchitlater.spring.StringTemplateViewResolver;

public class CustomTemplateViewResolver extends StringTemplateViewResolver {

    @Override
    protected StringTemplateView createView() {
        return new CustomTemplateView();
    }
}
