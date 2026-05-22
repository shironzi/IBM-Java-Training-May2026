package ibm.training;

public non-sealed class PaymentGateway extends Gateway {
	public void handlePayment(Payment payment) {
		payment.processPayment();
		System.out.println("Payment has been processed...");
	}
}
