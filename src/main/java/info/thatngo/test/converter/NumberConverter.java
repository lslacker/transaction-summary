package info.thatngo.test.converter;

import java.util.Map;

import org.springframework.stereotype.Component;


@Component
public class NumberConverter implements Converter<Double> {


	@Override
	public Double convert(Map<String, String> context, String value) {
		return Double.parseDouble(value);

	}
}
