package info.thatngo.test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import info.thatngo.test.common.Summary;
import info.thatngo.test.common.Transaction;
import info.thatngo.test.common.fixedwidth.FixedWidthProcessor;

@Service
public class TransactionServiceImpl implements TransactionService {
	
    private static Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Override
	public List<Summary> summarize(String input) throws IOException {
		FixedWidthProcessor<Transaction> processor = new FixedWidthProcessor<Transaction>(Transaction.class);
        
        Stream<Transaction> transactions = processor.read(input);
        
        Map<Transaction, Double> map = 
        		transactions.collect(Collectors.groupingBy(Function.identity(),
        							Collectors.summingDouble(Transaction::getTotalTransactionAmount)));
        
        List<Summary> rows = map.entrySet().stream().map(x -> {
        	Transaction t = x.getKey();
        	Double netTotal = x.getValue();
        	return new Summary(t.getClientInformation(), t.getProductInformation(), netTotal);
        }).collect(Collectors.toList());
        
        return rows;
	}

	@Override
	public void write(String output, List<Summary> rows, boolean withHeader)	 {
		FixedWidthProcessor<Summary> processor = new FixedWidthProcessor<Summary>(Summary.class);
		try {
			processor.write(output, rows, withHeader);
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

}
