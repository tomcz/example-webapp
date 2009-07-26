package example.utils;

public class HamcrestMatcher<T> implements Matcher<T> {

    private final org.hamcrest.Matcher<T> matcher;

    public HamcrestMatcher(org.hamcrest.Matcher<T> matcher) {
        this.matcher = matcher;
    }

    public boolean matches(T item) {
        return matcher.matches(item);
    }
}
