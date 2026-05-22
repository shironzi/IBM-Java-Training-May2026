package training.ibm.com;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		System.out.println(blackjack(1,2));
		System.out.println(blackjack(21, 22));
		System.out.println(blackjack(22, 22));
		System.out.println(blackjack(2, 10));
		
		System.out.println(dayToStrSwitch());
//		System.out.println(dayToStrPattern());
		
		pyramid();
	}
	
	public static int blackjack(int a, int b) {
		a = a > 21 ? 0 : a;
		b = b > 21 ? 0 : b;
		return a > b ? a : b;
	}
	
	public static String dayToStrSwitch() {
		Scanner scanner = new Scanner(System.in);
				
		System.out.print("Enter Day Number: ");
		int day = scanner.nextInt();
				
		switch(day) {
			case 1:
				return "Monday";
			case 2:
				return "Tuesday";
			case 3:
				return "Wednesday";
			case 4:
				return "Thursday";
			case 5:
				return "Friday";
			case 6:
				return"Saturday";
			case 7:
				return "Sunday";
			default:
				return "Invalid day";
		}
	}
	
	public static String dayToStrPattern() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter Day Number: ");
		int day = scanner.nextInt();
		
		scanner.close();
				
		return switch(day) {
			case 1 -> "Monday";
			case 2 -> "Tuesday";
			case 3 -> "Wednesday";
			case 4 -> "Thursday";
			case 5 -> "Friday";
			case 6 -> "Saturday";
			case 7 -> "Sunday";
			default -> "Invalid day";
		};
	}
	
	public static void pyramid() {
		Scanner scanner = new Scanner(System.in);
		
		int size;
		
		do {
			System.out.print("Enter Size of Pyramid: ");
			size = scanner.nextInt();
		} while(size < 1 || size > 20);
		
		for(int col=1; col<=size; col++) {
			for(int row=1; row<=col; row++) {
				System.out.print(row + " ");
			}
			
			System.out.println();
		}
		scanner.close();
	}
}
