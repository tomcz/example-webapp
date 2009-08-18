package example.spring.path;

public class Path {

    private final String uri;
    private final boolean contextRelative;

    public Path(String uri) {
        this(uri, true);
    }

    public Path(String uri, boolean contextRelative) {
        this.contextRelative = contextRelative;
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public boolean isContextRelative() {
        return contextRelative;
    }
}
