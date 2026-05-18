package training.ibm.com;

import java.util.Scanner;

public class ZigzagPattern {

	public static void main(String[] args) {
		System.out.print("Enter Size for Zigzag Pattern: ");
		Scanner scanner = new Scanner(System.in);
		
		int size = scanner.nextInt();
		
		for(int col = 1; col <= size; col++) {
			
			if(col % 2 == 1) {
				for(int i=((col - 1 )*size) + 1; i <= col * size; i++) {
					System.out.print
					(i + " ");
				}
			}else {
				for(int i=col * size; i > (col - 1 )*size; i--) {
					System.out.print(i + " ");
				}
			}
			
			System.out.println();
		}
	}

}
