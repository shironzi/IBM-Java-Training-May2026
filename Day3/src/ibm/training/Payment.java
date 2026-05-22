package ibm.training;

public abstract class Payment {
	private double amount;
	
	public Payment(double amount) {
		this.amount = amount;
	}
	
	public void display() {
		System.out.println("Payment amount: " + amount);
	}
	
	public double getAmount() {
		return amount;
	}
	
	public abstract void processPayment();
}
