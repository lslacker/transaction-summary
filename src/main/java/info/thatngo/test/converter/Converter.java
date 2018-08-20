package info.thatngo.test.converter;

import java.util.Map;

public interface Converter<T> {
	public T convert(Map<String, String> context, String value);
}
