package example.utils;

public interface Reducer<T, U> {
    U reduce(T item, U previous);
}
