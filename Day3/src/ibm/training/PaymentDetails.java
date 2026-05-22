package ibm.training;

import java.time.LocalDateTime;

public record PaymentDetails(int transactionId, double amount, Payment paymentMethod, LocalDateTime timestamp ) {
}
