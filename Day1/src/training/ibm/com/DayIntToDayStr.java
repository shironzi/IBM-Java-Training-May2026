package training.ibm.com;

import java.util.Scanner;

public class DayIntToDayStr {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter Size for Zigzag Pattern: ");
		int day = scanner.nextInt();
		
		scanner.close();
		
		 String dayStr = switch(day) {
			case 1 -> "Monday";
			case 2 -> "Tuesday";
			case 3 -> "Wednesday";
			case 4 -> "Thursday";
			case 5 -> "Firday";
			case 6 -> "Saturday";
			case 7 -> "Sunday";
			default -> "Invalid day";
		};
		
		System.out.println(dayStr);
	}

}
