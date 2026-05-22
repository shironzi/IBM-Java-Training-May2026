package ibm.training;

public class PayPalPayment extends Payment implements Verifiable{
	private String email;
	
	public PayPalPayment(String email, double amount) {
		super(amount);
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}

	@Override
	public Boolean verify() {
		if(email.contains("@")) {
			System.out.println("Processing PayPal payment...");
			return true;
		}
		return false;
	}
	
	@Override
	public void processPayment(){
		System.out.println("Processing credit card....");
	}
}
