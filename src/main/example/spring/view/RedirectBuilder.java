package example.spring.view;

import example.spring.PathBuilder;
import org.springframework.web.bind.annotation.RequestMethod;

public class RedirectBuilder {

    private final PathBuilder builder;

    public RedirectBuilder(Class handler) {
        builder = new PathBuilder(handler);
    }

    public RedirectBuilder withMethod(RequestMethod method) {
        builder.withMethod(method);
        return this;
    }

    public RedirectBuilder withMethod(String methodName) {
        builder.withMethod(methodName);
        return this;
    }

    public RedirectBuilder withVar(String name, Object value) {
        builder.withVar(name, value);
        return this;
    }

    public RedirectBuilder contextRelative(boolean contextRelative) {
        builder.contextRelative(contextRelative);
        return this;
    }

    public RedirectBuilder servletRelative(boolean servletRelative) {
        builder.servletRelative(servletRelative);
        return this;
    }

    public PathRedirectView build() {
        return new PathRedirectView(builder.build());
    }
}
