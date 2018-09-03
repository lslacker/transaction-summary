package info.thatngo.test.common.fixedwidth;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import info.thatngo.test.common.InputField;
import info.thatngo.test.common.OutputField;
import info.thatngo.test.common.ThrowingConsumer;
import info.thatngo.test.converter.Converter;
import info.thatngo.test.converter.DateConverter;
import info.thatngo.test.converter.NumberConverter;


public class FixedWidthProcessor<T> {
	
    private static Logger LOGGER = LoggerFactory.getLogger(FixedWidthProcessor.class);
    private static final String NEW_LINE = System.lineSeparator();
    private static final Map<String, Converter<?>> CONVERTERLOOKUP = Maps.newHashMap();
	private Class<T> entityClass;
	private Map<Field, ColumnInfo> columnInfoMap;
	
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
		
		if (columnInfoMap != null) {
			return;
		}
		
		columnInfoMap = Maps.newLinkedHashMap();
		
		for (Field field : entityClass.getDeclaredFields()) {
			InputField inputField = field.getAnnotation(InputField.class);
			if (inputField != null) {
				int fromPos = inputField.fromPos();
				int toPos = inputField.toPos();
				String format = inputField.format();
				ColumnInfo columnInfo = new ColumnInfo(fromPos, toPos, format);
				columnInfoMap.put(field, columnInfo);
			}
			
			OutputField outputField = field.getAnnotation(OutputField.class);
			
			if (outputField != null) {
				String columnName = outputField.name();
				String format = outputField.format();
				ColumnInfo columnInfo = new ColumnInfo(columnName, format);
				columnInfoMap.put(field, columnInfo);
			}
		}
	}
	
	public Stream<T> read(String filename) throws IOException {
		getConfigFromEntity();
		Path path = Paths.get(filename);
		return Files.lines(path).map(l -> parseRow(l));
	}
	
	public void write(String output, Collection<T> rows, boolean writeHeader) throws IOException {
		getConfigFromEntity();
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));
		
		//header is column Name
		if (writeHeader) {
			//write header
			List<String> headerRow = this.columnInfoMap.entrySet().stream().map(c -> {
				return c.getValue().getColumnName();
			}).collect(Collectors.toList());
			writeHeader(writer, headerRow);
		}
		
		//start write row
		rows.stream().forEach(throwingConsumerWrapper(row -> writeRow(writer, row)));
		
		writer.close();
	}
	
	public void writeHeader(BufferedWriter writer, List<String> row) throws IOException {
		String line = row.stream().collect(Collectors.joining(","));
		writer.write(line);
		writer.write(NEW_LINE);
	}
	
	public void writeRow(BufferedWriter writer, T row) throws IOException {
		Stream<String> data = this.columnInfoMap.entrySet().stream().map(c -> {
			Field aField = c.getKey();
			Class<?> type = aField.getType();
			String formatter = "%s";
			try {
				aField.setAccessible(true);
				Object o = aField.get(row);
				if (StringUtils.equalsIgnoreCase(type.getSimpleName(), "Double")) {
					formatter = c.getValue().getFormat();
				}
				return "\"" + String.format(formatter, o)+ "\"";
			} catch (IllegalArgumentException | IllegalAccessException e) {
				
			}
			return null;
		});
		String line = data.collect(Collectors.joining(","));
		writer.write(line);
		writer.write(NEW_LINE);
	}
	
	private T parseRow(final String line) {
		
		Constructor<T> constructor;
		
		try {
			constructor = entityClass.getConstructor();
			final T instance = constructor.newInstance();
			columnInfoMap.entrySet().stream().forEach(x -> {
				Field field = x.getKey();
				ColumnInfo cInfo = x.getValue();
				int start = cInfo.getFromPos();
				int end = cInfo.getToPos();
				field.setAccessible(true);
				Class<?> type = field.getType();
				
				try {
					String value = StringUtils.substring(line, start - 1, end);
					Map<String, String> context = Maps.newHashMap();
					context.put("format", cInfo.getFormat());
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
	
	
	public class ColumnInfo {
		private final String columnName;
		private final int fromPos;
		private final int toPos;
		private final String format;
		
		public ColumnInfo(int fromPos, int toPos, String format) {
			this.columnName = "";
			this.fromPos = fromPos;
			this.toPos = toPos;
			this.format = format;
		}
		
		public ColumnInfo(String columnName, String format) {
			this.columnName = columnName;
			this.fromPos = -1;
			this.toPos = -1;
			this.format = format;
		}

		public String getColumnName() {
			return columnName;
		}

		public int getFromPos() {
			return fromPos;
		}

		public int getToPos() {
			return toPos;
		}

		public String getFormat() {
			return format;
		}
	}
	
	public static <T> Consumer<T> throwingConsumerWrapper(
			  ThrowingConsumer<T, Exception> throwingConsumer) {
			  
	    return fn -> {
	        try {
	            throwingConsumer.accept(fn);
	        } catch (Exception ex) {
	            throw new RuntimeException(ex);
	        }
	    };
	}
}



