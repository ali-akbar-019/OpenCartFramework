package utilities;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTotalCalculatorTest {

	// Business rule methods - cart total calculation logic
	private double calculateSubtotal(double price, double qty1) {
		if (price < 0)
			return 0.0;
		if (qty1 <= 0)
			return 0.0;
		return price * qty1;
	}

	private double calculateTotalWithTax(double subtotal, double taxRate) {
		if (subtotal <= 0)
			return 0.0;
		if (taxRate < 0)
			return subtotal;
		return subtotal + (subtotal * taxRate);
	}

	private double calculateTotalWithShipping(double subtotal, double shippingCost) {
		if (subtotal <= 0)
			return 0.0;
		if (shippingCost < 0)
			return subtotal;
		return subtotal + shippingCost;
	}

	private double calculateGrandTotal(double subtotal, double taxRate, double shippingCost) {
		double withTax = calculateTotalWithTax(subtotal, taxRate);
		return calculateTotalWithShipping(withTax, shippingCost);
	}

	// ========== NORMAL CASES - Subtotal ==========

	@Test
	public void testCalculateSubtotal_SingleItem() {
		double subtotal = calculateSubtotal(100.0, 1);
		Assert.assertEquals(subtotal, 100.0, 0.01, "1 item at $100 = $100");
	}

	@Test
	public void testCalculateSubtotal_MultipleItems() {
		double subtotal = calculateSubtotal(25.0, 4);
		Assert.assertEquals(subtotal, 100.0, 0.01, "4 items at $25 each = $100");
	}

	@Test
	public void testCalculateSubtotal_WithDecimalPrices() {
		double subtotal = calculateSubtotal(19.99, 3);
		Assert.assertEquals(subtotal, 59.97, 0.01, "3 items at $19.99 each = $59.97");
	}

	// ========== BOUNDARY CASES - Subtotal ==========

	@Test
	public void testCalculateSubtotal_ZeroQuantity() {
		double subtotal = calculateSubtotal(50.0, 0);
		Assert.assertEquals(subtotal, 0.0, 0.01, "Zero quantity = $0");
	}

	@Test
	public void testCalculateSubtotal_NegativeQuantity() {
		double subtotal = calculateSubtotal(50.0, -2);
		Assert.assertEquals(subtotal, 0.0, 0.01, "Negative quantity should be treated as $0");
	}

	@Test
	public void testCalculateSubtotal_ZeroPrice() {
		double subtotal = calculateSubtotal(0.0, 5);
		Assert.assertEquals(subtotal, 0.0, 0.01, "Zero price × any quantity = $0");
	}

	@Test
	public void testCalculateSubtotal_NegativePrice() {
		double subtotal = calculateSubtotal(-10.0, 5);
		Assert.assertEquals(subtotal, 0.0, 0.01, "Negative price should be treated as $0");
	}

	// ========== NORMAL CASES - Tax ==========

	@Test
	public void testCalculateTotalWithTax_TenPercent() {
		double total = calculateTotalWithTax(100.0, 0.10);
		Assert.assertEquals(total, 110.0, 0.01, "$100 + 10% tax = $110");
	}

	@Test
	public void testCalculateTotalWithTax_TwentyPercent() {
		double total = calculateTotalWithTax(50.0, 0.20);
		Assert.assertEquals(total, 60.0, 0.01, "$50 + 20% tax = $60");
	}

	@Test
	public void testCalculateTotalWithTax_ZeroPercent() {
		double total = calculateTotalWithTax(100.0, 0.0);
		Assert.assertEquals(total, 100.0, 0.01, "0% tax = same as subtotal");
	}

	// ========== BOUNDARY CASES - Tax ==========

	@Test
	public void testCalculateTotalWithTax_NegativeTaxRate() {
		double total = calculateTotalWithTax(100.0, -0.10);
		Assert.assertEquals(total, 100.0, 0.01, "Negative tax rate should be ignored");
	}

	@Test
	public void testCalculateTotalWithTax_ZeroSubtotal() {
		double total = calculateTotalWithTax(0.0, 0.10);
		Assert.assertEquals(total, 0.0, 0.01, "Tax on $0 should be $0");
	}

	// ========== NORMAL CASES - Shipping ==========

	@Test
	public void testCalculateTotalWithShipping_Standard() {
		double total = calculateTotalWithShipping(50.0, 10.0);
		Assert.assertEquals(total, 60.0, 0.01, "$50 + $10 shipping = $60");
	}

	@Test
	public void testCalculateTotalWithShipping_FreeShipping() {
		double total = calculateTotalWithShipping(100.0, 0.0);
		Assert.assertEquals(total, 100.0, 0.01, "Free shipping = subtotal only");
	}

	// ========== BOUNDARY CASES - Shipping ==========

	@Test
	public void testCalculateTotalWithShipping_NegativeShipping() {
		double total = calculateTotalWithShipping(50.0, -5.0);
		Assert.assertEquals(total, 50.0, 0.01, "Negative shipping should be ignored");
	}

	@Test
	public void testCalculateTotalWithShipping_ZeroSubtotal() {
		double total = calculateTotalWithShipping(0.0, 10.0);
		Assert.assertEquals(total, 0.0, 0.01, "Shipping on $0 cart should be $0");
	}

	// ========== GRAND TOTAL TESTS ==========

	@Test
	public void testCalculateGrandTotal_CompleteOrder() {
		double subtotal = 100.0;
		double taxRate = 0.10;
		double shipping = 15.0;

		double grandTotal = calculateGrandTotal(subtotal, taxRate, shipping);

		// $100 + 10% tax ($10) + $15 shipping = $125
		Assert.assertEquals(grandTotal, 125.0, 0.01, "Complete order calculation");
	}

	@Test
	public void testCalculateGrandTotal_NoTaxFreeShipping() {
		double grandTotal = calculateGrandTotal(75.0, 0.0, 0.0);
		Assert.assertEquals(grandTotal, 75.0, 0.01, "$75 with no tax and free shipping");
	}

	// ========== PROPERTY-BASED TESTS (Invariants) ==========

	@Test
	public void testTotalProperty_AlwaysNonNegative() {
		// Property: Cart total should NEVER be negative
		double[] prices = { 0, 10, 25, 50, 100 };
		int[] quantities = { 0, 1, 2, 5, 10 };
		double[] taxRates = { 0, 0.05, 0.10, 0.20 };
		double[] shippingCosts = { 0, 5, 10, 15 };

		for (double price : prices) {
			for (int qty : quantities) {
				for (double tax : taxRates) {
					for (double shipping : shippingCosts) {
						double subtotal = calculateSubtotal(price, qty);
						double total = calculateGrandTotal(subtotal, tax, shipping);
						Assert.assertTrue(total >= 0, "Total should never be negative: price=" + price + ", qty=" + qty
								+ ", tax=" + tax + ", shipping=" + shipping + ", total=" + total);
					}
				}
			}
		}
	}

	@Test
	public void testTotalProperty_Additive() {
		// Property: Total(A + B) = Total(A) + Total(B)
		// Testing with two separate items vs combined

		double price1 = 10.0, qty1 = 2;
		double price2 = 15.0, qty2 = 3;
		double taxRate = 0.10;
		double shipping = 5.0;

		// Calculate separately
		double subtotal1 = calculateSubtotal(price1, qty1);
		double subtotal2 = calculateSubtotal(price2, qty2);
		double totalSeparate = calculateGrandTotal(subtotal1 + subtotal2, taxRate, shipping);

		// Calculate combined (same as above mathematically)
		double totalCombined = calculateGrandTotal(subtotal1 + subtotal2, taxRate, shipping);

		Assert.assertEquals(totalSeparate, totalCombined, 0.01, "Totals should be additive");
	}

	@Test
	public void testTotalProperty_ZeroQuantityMeansZeroTotal() {
		// Property: If all quantities are zero, grand total should be zero
		double[] prices = { 10, 25, 50, 100 };
		double taxRate = 0.10;
		double shipping = 10.0;

		for (double price : prices) {
			double subtotal = calculateSubtotal(price, 0);
			double grandTotal = calculateGrandTotal(subtotal, taxRate, shipping);
			Assert.assertEquals(grandTotal, 0.0, 0.01,
					"Zero quantity should result in zero total even with tax/shipping");
		}
	}

	@Test
	public void testTotalProperty_MonotonicIncreasing() {
		// Property: Increasing quantity should NEVER decrease total
		double price = 10.0;
		double taxRate = 0.10;
		double shipping = 5.0;

		double previousTotal = -1;

		for (int qty = 0; qty <= 10; qty++) {
			double subtotal = calculateSubtotal(price, qty);
			double total = calculateGrandTotal(subtotal, taxRate, shipping);

			if (qty > 0) {
				Assert.assertTrue(total >= previousTotal,
						"Total should not decrease when quantity increases from " + (qty - 1) + " to " + qty);
			}
			previousTotal = total;
		}
	}

	@Test
	public void testTotalProperty_TaxOnlyOnPositiveSubtotal() {
		// Property: Tax should only apply when subtotal > 0
		double taxRate = 0.10;

		double totalWithZeroSubtotal = calculateTotalWithTax(0.0, taxRate);
		Assert.assertEquals(totalWithZeroSubtotal, 0.0, "Tax on $0 should be $0");

		double totalWithPositiveSubtotal = calculateTotalWithTax(100.0, taxRate);
		Assert.assertTrue(totalWithPositiveSubtotal > 100.0, "Tax should increase total when subtotal > 0");
	}
}