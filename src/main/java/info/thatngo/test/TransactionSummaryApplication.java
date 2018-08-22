package info.thatngo.test;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import info.thatngo.test.common.Summary;

@SpringBootApplication
public class TransactionSummaryApplication implements CommandLineRunner {
	
	private static Logger LOG = LoggerFactory
		      .getLogger(TransactionSummaryApplication.class);
	
	private static String USAGE = "2 args required: <input> <output>";
	
	private final TransactionService service;
	
	@Autowired
	TransactionSummaryApplication(TransactionService service) {
		this.service = service;
	}
	public static void main(String[] args) {
		SpringApplication.run(TransactionSummaryApplication.class, args);
	}
	
	@Override
    public void run(String... args) throws IOException {
        LOG.info("EXECUTING : command line runner");
  
        if (args.length < 2) {
        	LOG.info(USAGE);
        	System.exit(1);
        }
        
        String input = args[0];
        String output = args[1];
        
        List<Summary> summaries = service.summarize(input);
        LOG.info(summaries.toString());
    }
	
//	private void console(final String input, final String output) throws IOException {
//		FixedWidthProcessor<Transaction> processor = new FixedWidthProcessor<Transaction>(Transaction.class);
//        
//        Stream<Transaction> transactions = processor.read(input);
//        
//        Map<Transaction, Double> map = 
//        		transactions.collect(Collectors.groupingBy(Function.identity(),
//        							Collectors.summingDouble(Transaction::getTotalTransactionAmount)));
//        
//        List<String> rows = Lists.newArrayList("Client Information,Product Information,Total Transaction Amount");
//        
//        rows.addAll(map.entrySet().stream().map(x -> {
//        	Transaction t = x.getKey();
//        	Double netTotal = x.getValue();
//        	return String.format("\"%s\",\"%s\",\"%.2f\"", t.getClientInformation(), t.getProductInformation(), netTotal);
//        }).collect(Collectors.toList()));
//        
//        write(output, rows);
//	}
//	
//	private void write(String outputFilename, List<String> rows) {
//		Path path = Paths.get(outputFilename);
//        try {
//			Files.write(path, rows, StandardCharsets.UTF_8);
//		} catch (IOException e) {
//			LOG.error(e.getMessage());
//		}
//	}
	
}
