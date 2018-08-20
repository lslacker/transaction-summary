package info.thatngo.test.common.fixedwidth;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import info.thatngo.test.common.CsvField;
import info.thatngo.test.converter.Converter;
import info.thatngo.test.converter.DateConverter;
import info.thatngo.test.converter.NumberConverter;


public class FixedWidthProcessor<T> {
	
    private static Logger LOGGER = LoggerFactory.getLogger(FixedWidthProcessor.class);
    private static final Map<String, Converter<?>> CONVERTERLOOKUP = Maps.newHashMap();
	private Class<T> entityClass;
	private Map<Field, Pair<Integer, Integer>> columnPositionInfoMap;
	
	static {
		CONVERTERLOOKUP.put("Date", new DateConverter());
		CONVERTERLOOKUP.put("Double", new NumberConverter());
	}
	
	public FixedWidthProcessor() {
		
	}
	
	public FixedWidthProcessor(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	
	
	private void getConfigFromEntity() {
		
		if (columnPositionInfoMap != null) {
			return;
		}
		columnPositionInfoMap = Maps.newHashMap();
		
		for (Field field : entityClass.getDeclaredFields()) {
			CsvField csvField = field.getAnnotation(CsvField.class);
			if (csvField != null) {
				int fromPos = csvField.fromPos();
				int toPos = csvField.toPos();
				Pair<Integer, Integer> coord = Pair.of(fromPos, toPos);
				columnPositionInfoMap.put(field, coord);
			}
		}
	}
	
	public Stream<T> read(String filename) throws IOException {
		getConfigFromEntity();
		Path path = Paths.get(filename);
		return Files.lines(path).map(l -> parseRow(l));
	}
	
	private T parseRow(final String line) {
		
		Constructor<T> constructor;
		try {
			constructor = entityClass.getConstructor();
			final T instance = constructor.newInstance();
			columnPositionInfoMap.entrySet().stream().forEach(x -> {
				Field field = x.getKey();
				Pair<Integer, Integer> coord = x.getValue();
				int start = coord.getLeft();
				int end = coord.getRight();
				field.setAccessible(true);
				Class<?> type = field.getType();
				
				try {
					String value = StringUtils.substring(line, start - 1, end);
					Map<String, String> context = Maps.newHashMap();
					context.put("format", "YYYYMMDD");
					Converter<?> converter = CONVERTERLOOKUP.getOrDefault(type.getSimpleName(), null);
					if (converter != null) {
						field.set(instance, converter.convert(context, value));
					} else {
						field.set(instance, value);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					LOGGER.error(e.getMessage());
				}
			});
			return instance;
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOGGER.error(e.getMessage());
		}
		
		return null;
	}
	
}
