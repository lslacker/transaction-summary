package info.thatngo.test;

import java.io.File;
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
        	return;
        }
        
        String input = args[0];
        String output = args[1];
        File out = new File(output);
        List<Summary> summaries = service.summarize(input);
        service.write(out, summaries, true);
    }
	
}
