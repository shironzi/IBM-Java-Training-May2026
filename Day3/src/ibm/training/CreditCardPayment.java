package ibm.training;

class CreditCardPayment extends Payment implements Verifiable {
	
	private String cardNumber;

	public CreditCardPayment(String cardNumber, double amount) {
		super(amount);
		this.cardNumber = cardNumber;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}

	@Override
	public Boolean verify() {
		
		if(cardNumber.length() == 16) {
			System.out.println("Processing credit card payment...");
			return true;
		}
		return false;
	}
	
	@Override
	public void processPayment(){
		System.out.println("Processing credit card....");
	}
	
}
