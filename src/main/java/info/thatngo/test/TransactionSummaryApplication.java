package info.thatngo.test;

import java.io.IOException;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import info.thatngo.test.common.Transaction;
import info.thatngo.test.common.fixedwidth.FixedWidthProcessor;

@SpringBootApplication
public class TransactionSummaryApplication implements CommandLineRunner {
	
	private static Logger LOG = LoggerFactory
		      .getLogger(TransactionSummaryApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(TransactionSummaryApplication.class, args);
	}
	
	
	@Override
    public void run(String... args) throws IOException {
        LOG.info("EXECUTING : command line runner");
  
        for (int i = 0; i < args.length; ++i) {
            LOG.info("args[{}]: {}", i, args[i]);
        }
        
        FixedWidthProcessor<Transaction> processor = new FixedWidthProcessor<Transaction>(Transaction.class);
        
        Stream<Transaction> transactions = processor.read("/home/lmai/Downloads/input.txt");
        
        transactions.forEach(t -> LOG.info(t.getRecordType()));
    }
	
}
