package ibm.training;

public class BankTransferPayment extends Payment implements Verifiable {

	private String accountNumber;
	
	public BankTransferPayment(String accountNumber, double amount) {
		super(amount);
		this.accountNumber = accountNumber;
	}
	
	public String getAccountNumbet() {
		return accountNumber;
	}
	
	@Override
	public Boolean verify() {
		if(accountNumber.length() == 10) {
			System.out.println("Processing bank transfer...");
			return true;
		}
		return false;
	}
	
	@Override
	public void processPayment(){
		System.out.println("Processing credit card....");
	}
}
