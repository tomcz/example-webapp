package example.utils;

import org.apache.commons.collections.Predicate;
import org.hamcrest.Matcher;

public class PredicateMatcher implements Predicate {

    private final Matcher matcher;

    public PredicateMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    public boolean evaluate(Object object) {
        return matcher.matches(object);
    }

    public static PredicateMatcher with(Matcher matcher) {
        return new PredicateMatcher(matcher);
    }
}
