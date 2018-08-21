package info.thatngo.test.common;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Transaction {
	
	@InputField(fromPos=1, toPos=3)
	private String recordType;
	
	@InputField(fromPos=4, toPos=7)
	private String clientType;
	
	@InputField(fromPos=8, toPos=11)
	private String clientNumber;
	
	@InputField(fromPos=12, toPos=15)
	private String accountNumber;
	
	@InputField(fromPos=16, toPos=19)
	private String subAccountNumber;
	
	@InputField(fromPos=28, toPos=31)
	private String exchangeCode;
	
	@InputField(fromPos=26, toPos=27)
	private String productGroupCode;
	
	@InputField(fromPos=32, toPos=37)
	private String symbol;
	
	@InputField(fromPos=38, toPos=45, format="YYYYMMDD")
	private Date expirationDate;
	
	@InputField(fromPos=53, toPos=62)
	private Double quantityLong;
	
	@InputField(fromPos=64, toPos=73)
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
	
	@Override
	public String toString() {
		return "Transaction [recordType=" + recordType + ", clientType=" + clientType + ", clientNumber=" + clientNumber
				+ ", accountNumber=" + accountNumber + ", subAccountNumber=" + subAccountNumber + ", exchangeCode="
				+ exchangeCode + ", productGroupCode=" + productGroupCode + ", symbol=" + symbol + ", expirationDate="
				+ expirationDate + ", quantityLong=" + quantityLong + ", quantityShort=" + quantityShort + "]";
	}
	
	public String getClientInformation() {
		return String.format("%s,%s,%s,%s", clientType, clientNumber, accountNumber, subAccountNumber);
	}
	
	public String getProductInformation() {
		return String.format("%s,%s,%s,%s", exchangeCode, productGroupCode, symbol, expirationDate);
	}
	
	public double getTotalTransactionAmount() {
		return quantityLong - quantityShort;
	}
	
	@Override
    public boolean equals(Object o) {

		if (o == this) return true;
        if (!(o instanceof Transaction)) {
            return false;
        }

        Transaction anotherTransaction = (Transaction) o;

        return new EqualsBuilder()
                .append(this.getClientInformation(), anotherTransaction.getClientInformation())
                .append(this.getProductInformation(), anotherTransaction.getProductInformation())
                .isEquals();
    }

    //Idea from effective Java : Item 9
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.getClientInformation().hashCode();
        result = 31 * result + this.getProductInformation().hashCode();
        return result;
    }
}
