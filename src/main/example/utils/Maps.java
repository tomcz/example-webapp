package example.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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

    public static <K, V> Map<K, V> create(Pair<K, V>... pairs) {
        Map<K, V> map = create();
        append(map, pairs);
        return map;
    }

    public static <K, V> Map<K, V> createLinked() {
        return new LinkedHashMap<K, V>();
    }

    public static <K, V> Map<K, V> createLinked(K key, V value) {
        Map<K, V> map = createLinked();
        map.put(key, value);
        return map;
    }

    public static <K, V> Map<K, V> createLinked(Pair<K, V>... pairs) {
        Map<K, V> map = createLinked();
        append(map, pairs);
        return map;
    }

    public static <K, V> K findKeyForValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static <K, V> void append(Map<K, V> map, Pair<K, V>... pairs) {
        for (Pair<K, V> pair : pairs) {
            map.put(pair.getKey(), pair.getValue());
        }
    }

    public static List<Pair<String, Integer>> asList(Map<String, Integer> map) {
        List<Pair<String, Integer>> list = Lists.create(map.size());
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            list.add(Pair.create(entry.getKey(), entry.getValue()));
        }
        return list;
    }
}
