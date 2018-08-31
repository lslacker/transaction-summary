package info.thatngo.test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import info.thatngo.test.common.Summary;

public interface TransactionService {
	List<Summary> summarize(String input) throws IOException;
	void write(String output, List<Summary> rows, boolean withHeader);
}
