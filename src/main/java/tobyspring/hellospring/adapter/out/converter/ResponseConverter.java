package tobyspring.hellospring.adapter.out.converter;

public interface ResponseConverter<T> {
    T convert(String input);
}
