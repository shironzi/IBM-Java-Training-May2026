package ibm.training;

import java.time.LocalDateTime;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		ArrayList<Payment> payments = new ArrayList<>();
	
		CreditCardPayment credit = new CreditCardPayment("1234567891011345", 100);
		PayPalPayment paypal = new PayPalPayment("Aaron.Josh.Baon@ibm.com", 250);
		BankTransferPayment bank = new BankTransferPayment("1234567890", 500);
		
		payments.add(credit);
		payments.add(paypal);
		payments.add(bank);
		
		OnlinePaymentType onlinePaymentType = new OnlinePaymentType();
		PaymentType offlinePaymentType = new OfflinePaymentType();
		
		PaymentGateway paymentGateway = new PaymentGateway();
		
		LocalDateTime now = LocalDateTime.now();
		
		ArrayList<PaymentDetails> details = new ArrayList<>();
		
		int transacId = 1;
		
		for(Payment payment: payments) {
			Verifiable verifiable = (Verifiable) payment;
			
			if(verifiable.verify()) {
				payment.display();
				paymentGateway.handlePayment(payment);
				
				PaymentDetails paymentdetails = new PaymentDetails(transacId, payment.getAmount(), payment, now);
				details.add(paymentdetails);
				transacId += 1;
			}else {
				System.out.println("Transaction Failed");
			}
		}
		
		System.out.println("-------------------------- Transactions ----------------------");
		
		for(PaymentDetails detail: details) {
			System.out.println("Transaction ID: " + detail.transactionId() + " ");
			System.out.println("Amount: " + detail.amount() + " ");
			System.out.println("Payment method: " + detail.paymentMethod().getClass().getSimpleName() + " ");
			System.out.println("Timestamp: " + detail.timestamp() + " ");
			System.out.println("----------------------------------------------------------------");
		}
	}
}
