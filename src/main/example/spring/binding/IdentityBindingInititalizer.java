package example.spring.binding;

import example.domain.Identity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

public class IdentityBindingInititalizer implements WebBindingInitializer {

    public void initBinder(WebDataBinder binder, WebRequest request) {
        HttpServletRequest servletRequest = (HttpServletRequest) request.resolveReference(WebRequest.REFERENCE_REQUEST);
        boolean createOnNew = RequestMethod.POST.equals(RequestMethod.valueOf(servletRequest.getMethod()));
        binder.registerCustomEditor(Identity.class, new IdentityPropertyEditor(createOnNew));
    }
}
