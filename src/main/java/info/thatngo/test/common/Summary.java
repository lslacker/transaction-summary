package info.thatngo.test.common;

public class Summary {
	
	@OutputField(name="Client Information")
	private String clientInformation;
	
	@OutputField(name="Product Information")
	private String productInformation;
	
	@OutputField(name="Total Transaction Amount", format="%.2f")
	private Double totalTransactonAmount;
	
	public Summary(String clientInformation, String productInformation, Double totalTransactionAmount) {
		this.clientInformation = clientInformation;
		this.productInformation = productInformation;
		this.totalTransactonAmount = totalTransactionAmount;
	}
	
	public String getClientInformation() {
		return clientInformation;
	}
	
	public void setClientInformation(String clientInformation) {
		this.clientInformation = clientInformation;
	}
	
	public String getProductInformation() {
		return productInformation;
	}
	public void setProductInformation(String productInformation) {
		this.productInformation = productInformation;
	}
	public Double getTotalTransactonAmount() {
		return totalTransactonAmount;
	}
	public void setTotalTransactonAmount(Double totalTransactonAmount) {
		this.totalTransactonAmount = totalTransactonAmount;
	}
	
	
}
