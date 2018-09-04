package info.thatngo.test

import spock.lang.Specification
import info.thatngo.test.common.Summary
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.charset.Charset;


class TransactionServiceSpec extends Specification {
	def "can get a list of summary from input file"() {
		given:
			def input = "src/test/resources/input.txt"
			def service = new TransactionServiceImpl()
		when:
			def summaries = service.summarize(input)
		
		then:
			summaries.size() == 1	
	}
	
	def "can write a list of summary to output file"() {
		given:
			def summary = new Summary("Col1", "Col2", 0.2d);
			def summaries = [summary]
			def withHeader = true
			Path outputPath = Files.createTempFile("sample-file", ".csv")
			File output = outputPath.toFile();
			def service = new TransactionServiceImpl()
			
		when:
			service.write(output, summaries, true)
			List<String> lines = Files.readAllLines(output.toPath(), Charset.defaultCharset());
			
		then:
			lines.size() == 2
	}
}
