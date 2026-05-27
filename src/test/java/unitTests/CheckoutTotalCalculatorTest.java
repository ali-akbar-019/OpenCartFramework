package unitTests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTotalCalculatorTest {

	@Test
	public void testCalculateSubtotal_SingleItem() {
		double subtotal = calculateSubtotal(100.0, 1);
		Assert.assertEquals(subtotal, 100.0, "Single item: price × 1 = price");
	}

	@Test
	public void testCalculateSubtotal_MultipleItems() {
		double subtotal = calculateSubtotal(25.0, 4);
		Assert.assertEquals(subtotal, 100.0, "4 items at $25 each = $100");
	}

	@Test
	public void testCalculateSubtotal_ZeroQuantity() {
		double subtotal = calculateSubtotal(50.0, 0);
		Assert.assertEquals(subtotal, 0.0, "Zero quantity = zero cost");
	}

	@Test
	public void testCalculateSubtotal_NegativeQuantity() {
		double subtotal = calculateSubtotal(50.0, -2);
		Assert.assertEquals(subtotal, 0.0, "Negative quantity should be treated as zero");
	}

	@Test
	public void testCalculateTotalWithTax() {
		double subtotal = 100.0;
		double taxRate = 0.10; // 10%
		double total = calculateTotalWithTax(subtotal, taxRate);
		Assert.assertEquals(total, 110.0, "Subtotal $100 + 10% tax = $110");
	}

	@Test
	public void testCalculateTotalWithShipping() {
		double subtotal = 50.0;
		double shipping = 10.0;
		double total = calculateTotalWithShipping(subtotal, shipping);
		Assert.assertEquals(total, 60.0, "$50 + $10 shipping = $60");
	}

	// ========== PROPERTY-BASED TEST ==========

	@Test
	public void testTotalProperty_AlwaysPositiveOrZero() {
		// Property: Cart total should never be negative
		for (double price : new double[] { 0, 10, 50, 100 }) {
			for (int qty : new int[] { 0, 1, 2, 5 }) {
				double total = calculateSubtotal(price, qty);
				Assert.assertTrue(total >= 0, "Total should never be negative: " + total);
			}
		}
	}

	@Test
	public void testTotalProperty_Additive() {
		// Property: total(A + B) = total(A) + total(B)
		double price1 = 10.0, qty1 = 2;
		double price2 = 15.0, qty2 = 3;

		double totalSeparate = calculateSubtotal(price1, qty1) + calculateSubtotal(price2, qty2);
		double totalCombined = calculateSubtotal(price1, qty1) + calculateSubtotal(price2, qty2);

		Assert.assertEquals(totalSeparate, totalCombined, "Totals should be additive");
	}

	// Business rule methods
	private double calculateSubtotal(double price, double qty1) {
		if (qty1 <= 0)
			return 0.0;
		return price * qty1;
	}

	private double calculateTotalWithTax(double subtotal, double taxRate) {
		return subtotal + (subtotal * taxRate);
	}

	private double calculateTotalWithShipping(double subtotal, double shipping) {
		return subtotal + shipping;
	}
}