package info.thatngo.test.common;

import java.util.Date;

public class Transaction {
	
	@CsvField(fromPos=1, toPos=3)
	private String recordType;
	
	@CsvField(fromPos=4, toPos=7)
	private String clientType;
	
	@CsvField(fromPos=8, toPos=11)
	private String clientNumber;
	
	@CsvField(fromPos=12, toPos=15)
	private String accountNumber;
	
	@CsvField(fromPos=16, toPos=19)
	private String subAccountNumber;
	
	@CsvField(fromPos=28, toPos=31)
	private String exchangeCode;
	
	@CsvField(fromPos=26, toPos=27)
	private String productGroupCode;
	
	@CsvField(fromPos=32, toPos=37)
	private String symbol;
	
	@CsvField(fromPos=38, toPos=45, format="YYYYMMDD")
	private Date expirationDate;
	
	
	private Double quantityLong;
	private Double quantityShort;
	
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getSubAccountNumber() {
		return subAccountNumber;
	}
	public void setSubAccountNumber(String subAccountNumber) {
		this.subAccountNumber = subAccountNumber;
	}
	public String getExchangeCode() {
		return exchangeCode;
	}
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}
	public String getProductGroupCode() {
		return productGroupCode;
	}
	public void setProductGroupCode(String productGroupCode) {
		this.productGroupCode = productGroupCode;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Double getQuantityLong() {
		return quantityLong;
	}
	public void setQuantityLong(Double quantityLong) {
		this.quantityLong = quantityLong;
	}
	public Double getQuantityShort() {
		return quantityShort;
	}
	public void setQuantityShort(Double quantityShort) {
		this.quantityShort = quantityShort;
	}
}
