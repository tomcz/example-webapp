package example.utils;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Lists {

    public static <T> List<T> create() {
        return new ArrayList<T>();
    }

    public static <T> List<T> createWithSize(int size) {
        return new ArrayList<T>(size);
    }

    public static <T> List<T> create(T... items) {
        List<T> list = createWithSize(items.length);
        Collections.addAll(list, items);
        return list;
    }

    public static <T> T first(List<T> list) {
        return first(list, null);
    }

    public static <T> T first(List<T> list, T defaultValue) {
        return isNotEmpty(list) ? list.get(0) : defaultValue;
    }

    public static boolean isEmpty(List<?> list) {
        return (list == null) || list.isEmpty();
    }

    public static boolean isNotEmpty(List<?> list) {
        return !isEmpty(list);
    }

    public static <T> void each(Collection<T> items, Closure<T> closure) {
        for (T item : items) {
            closure.execute(item);
        }
    }

    public static <T> boolean contains(Collection<T> items, Matcher<T> matcher) {
        for (T item : items) {
            if (matcher.matches(item)) {
                return true;
            }
        }
        return false;
    }

    public static <T> boolean containsOnly(Collection<T> items, Matcher<T> matcher) {
        return countMatches(items, matcher) == items.size();
    }

    public static <T> int countMatches(Collection<T> items, Matcher<T> matcher) {
        int count = 0;
        for (T item : items) {
            if (matcher.matches(item)) {
                count += 1;
            }
        }
        return count;
    }

    public static <T> T firstMatch(Collection<T> items, Matcher<T> matcher) {
        for (T item : items) {
            if (matcher.matches(item)) {
                return item;
            }
        }
        return null;
    }

    public static <T> List<T> select(Collection<T> items, Matcher<T> matcher) {
        List<T> result = create();
        for (T item : items) {
            if (matcher.matches(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public static <T> List<T> reject(Collection<T> items, Matcher<T> matcher) {
        List<T> result = create();
        for (T item : items) {
            if (!matcher.matches(item)) {
                result.add(item);
            }
        }
        return result;
    }

    public static <T, U> List<U> map(Collection<T> items, Converter<T, U> function) {
        List<U> result = createWithSize(items.size());
        for (T item : items) {
            result.add(function.convert(item));
        }
        return result;
    }

    public static <T, U> U reduce(Collection<T> items, U initial, Reducer<T, U> reducer) {
        U memo = initial;
        for (T item : items) {
            memo = reducer.reduce(item, memo);
        }
        return memo;
    }
}
