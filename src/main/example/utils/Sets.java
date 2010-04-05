package example.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Sets {

    public static <T> Set<T> create() {
        return new HashSet<T>();
    }

    public static <T> Set<T> create(T... items) {
        Set<T> set = create();
        Collections.addAll(set, items);
        return set;
    }
}
