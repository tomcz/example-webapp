package example.error;

import example.spring.template.TemplateView;
import example.spring.template.TemplateViewFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

@Controller
public class ErrorPresenter {

    private final TemplateViewFactory factory;

    @Autowired
    public ErrorPresenter(TemplateViewFactory factory) {
        this.factory = factory;
    }

    @RequestMapping(value = "/error/{errorRef}", method = RequestMethod.GET)
    public View present(@PathVariable String errorRef) {
        TemplateView view = factory.create("error", "error");
        view.set("errorRef", errorRef);
        return view;
    }
}
