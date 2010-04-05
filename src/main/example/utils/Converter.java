package example.utils;

public interface Converter<T, U> {
    U convert(T item);
}
