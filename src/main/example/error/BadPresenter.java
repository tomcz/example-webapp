package example.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;

@Controller
public class BadPresenter {

    @RequestMapping(value = "/bad", method = RequestMethod.GET)
    public View present() {
        throw new UnsupportedOperationException();
    }
}
