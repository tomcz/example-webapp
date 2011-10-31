package example.domain.web.binding;

import example.domain.Identity;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomBindingInititalizer implements WebBindingInitializer {

    public void initBinder(WebDataBinder binder, WebRequest webRequest) {
        binder.registerCustomEditor(Identity.class, new IdentityPropertyEditor(shouldCreateOnNew(webRequest)));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    private boolean shouldCreateOnNew(WebRequest webRequest) {
        HttpServletRequest request = (HttpServletRequest) webRequest.resolveReference(WebRequest.REFERENCE_REQUEST);
        return RequestMethod.POST.equals(RequestMethod.valueOf(request.getMethod()));
    }
}
