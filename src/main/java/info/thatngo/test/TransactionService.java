package info.thatngo.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import info.thatngo.test.common.Summary;

public interface TransactionService {
	List<Summary> summarize(String input) throws IOException;
	void write(File output, List<Summary> rows, boolean withHeader);
}
