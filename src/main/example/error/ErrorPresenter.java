package example.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorPresenter {

    @RequestMapping(value = "/error/{errorRef}", method = RequestMethod.GET)
    public ModelAndView present(@PathVariable String errorRef) {
        return new ModelAndView("error/error", "errorRef", errorRef);
    }
}
