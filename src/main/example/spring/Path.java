package example.spring;

public class Path {

    private final String uri;
    private final boolean contextRelative;
    private final boolean servletRelative;

    public Path(String uri) {
        this(uri, true, true);
    }

    public Path(String uri, boolean contextRelative, boolean servletRelative) {
        this.contextRelative = contextRelative;
        this.servletRelative = servletRelative;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public boolean isContextRelative() {
        return contextRelative;
    }

    public boolean isServletRelative() {
        return servletRelative;
    }
}
