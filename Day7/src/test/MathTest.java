package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ibm.training.Math;

class MathTest {
	@Test
	void add_TwoPositiveFloats_ReturnsCorrectSum() {
		float num1 = 5.23f;
		float num2 = 323.2f;
		
		float res = Math.add(num1, num2);
		float expected = num1 + num2;
		
		assertEquals(expected, res);
	}
	
	@Test
	void subtract_PositiveFloats_ReturnsCorrectDifference() {
		
		float num1 = 323.2f;
		float num2 = 55.23f;
		
		float res = Math.subtract(num1, num2);
		float expected = num1 - num2;
		
		assertEquals(expected, res);
	}
	
	@Test
	void multiply_TwoFloats_ReturnsCorrectProduct() {
		
		float num1 = 5.2f;
		float num2 = 15.23f;
		
		float res = Math.multiply(num1, num2);
		float expected = num1 * num2;
		
		assertEquals(expected, res);
	}
	
	@Test
	void divide_ValidFloats_ReturnsCorrectQuotient() {
		
		float num1 = 15.00f;
		float num2 = 3.0f;
		
		float res = Math.divide(num1, num2);
		float expected = num1 / num2;
		
		assertEquals(expected, res);
	}
	
	@Test
	void divide_ByZero_ThrowsArithmeticException() {
		
		float num1 = 5.2f;
		float num2 = 0f;
		
		assertThrows(ArithmeticException.class, () -> Math.divide(num1, num2));
	}

}
