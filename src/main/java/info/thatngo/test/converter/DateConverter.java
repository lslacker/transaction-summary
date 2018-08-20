package info.thatngo.test.converter;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;


@Component
public class DateConverter implements Converter<Date> {
	
	@Override
	public Date convert(Map<String, String> context, String value) {
		String format = context.getOrDefault("format", null);
		if (format != null) {
			try {
				return DateUtils.parseDate(value, format);
			} catch (ParseException e) {
				
			}
		}
		return null;
	}
	
}
