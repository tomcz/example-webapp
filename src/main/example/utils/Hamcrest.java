package example.utils;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class Hamcrest {

    public static <T> Matcher<T> convert(final org.hamcrest.Matcher<T> matcher) {
        return new Matcher<T>() {
            public boolean matches(T item) {
                return matcher.matches(item);
            }
        };
    }

    public static <T> org.hamcrest.Matcher<T> convert(final Matcher<T> matcher) {
        return new TypeSafeMatcher<T>() {
            public boolean matchesSafely(T item) {
                return matcher.matches(item);
            }
            public void describeTo(Description description) {
                description.appendText(matcher.toString());
            }
        };
    }
}
