package example.spring.view;

import example.spring.PathBuilder;

import static example.spring.PathBuilder.pathTo;

public class RedirectBuilder {

    private final PathBuilder builder;

    private RedirectBuilder(PathBuilder builder) {
        this.builder = builder;
    }

    public static RedirectBuilder redirectTo(Class handler) {
        return new RedirectBuilder(pathTo(handler));
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
