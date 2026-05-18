package training.ibm.com;

import java.util.Scanner;

public class Pyramid {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter Size of Pyramid: ");
		while(!scanner.hasNextInt()) {
			
		}
		
		int size = scanner.nextInt();
		
		for(int col=1; col<=size; col++) {
			for(int row=1; row<=col; row++) {
				System.out.print(row + " ");
			}
			
			System.out.println();
		}
	}

}
