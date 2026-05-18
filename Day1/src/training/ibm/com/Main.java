package training.ibm.com;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println(blackjack(1,2));
		System.out.println(blackjack(21, 22));
		System.out.println(blackjack(2, 22));
		System.out.println(blackjack(2, 10));
	}
	
	public static int blackjack(int a, int b) {
		// TODO Auto-generated method stub
		a = a > 21 ? 0 : a;
		b = b > 21 ? 0 : b;
		return a > b ? a : b;
	}
}
