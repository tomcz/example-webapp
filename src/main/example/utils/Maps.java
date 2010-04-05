package example.utils;

import java.util.HashMap;
import java.util.Map;

public class Maps {

    public static <K, V> Map<K, V> create() {
        return new HashMap<K, V>();
    }

    public static <K, V> Map<K, V> create(K key, V value) {
        Map<K, V> map = create();
        map.put(key, value);
        return map;
    }
}
