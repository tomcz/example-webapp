package example.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Sets {

    public static <T> Set<T> create() {
        return new HashSet<T>();
    }

    public static <T> Set<T> create(T... items) {
        Set<T> set = create();
        Collections.addAll(set, items);
        return set;
    }

    public static <T> Set<T> createLinked() {
        return new LinkedHashSet<T>();
    }

    public static <T> Set<T> createLinked(T... items) {
        Set<T> set = createLinked();
        Collections.addAll(set, items);
        return set;
    }

    public static <T> SortedSet<T> createSorted() {
        return new TreeSet<T>();
    }

    public static <T> SortedSet<T> createSorted(T... items) {
        SortedSet<T> set = createSorted();
        Collections.addAll(set, items);
        return set;
    }

    public static <T> SortedSet<T> createSorted(Comparator<T> comparator) {
        return new TreeSet<T>(comparator);
    }

    public static <T> SortedSet<T> createSorted(Comparator<T> comparator, T... items) {
        SortedSet<T> set = createSorted(comparator);
        Collections.addAll(set, items);
        return set;
    }
}
