package be.groupe18.windowing.domain.utils;

/**
 * A simple class to store a tuple of data
 * @param <T> The type of the first stored data
 * @param <U> The type of the second stored data
 */

public class Tuple<T,U> {
    private final T v1;
    private final U v2;

    public Tuple(T v1, U v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public T getV1() {
        return v1;
    }

    public U getV2() {
        return v2;
    }
}